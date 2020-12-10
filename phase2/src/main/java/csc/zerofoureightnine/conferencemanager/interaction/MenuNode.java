package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.interaction.general.OptionPresenter;
import csc.zerofoureightnine.conferencemanager.interaction.general.OptionSelector;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class MenuNode { //UI
    private Permission permission; // may be null.
    private EnumMap<Permission, MenuNode> permissionOptions; //!null, may be empty.
    private Set<MenuNode> options; //!null but may be empty.
    private MenuNode parent; //may be null, no parent then, no going back up the menu.
    private Action action; //!null
    private Validatable validatable; //may be null, in which case any input is accepted.
    private Presentable presentable; //!null
    private boolean disabled = false;
    
    private MenuNode(Set<MenuNode> children, Action action, Permission permission, Validatable validatable, Presentable presentable) {
        this.permissionOptions = new EnumMap<>(Permission.class);
        this.options = new HashSet<>();
        for (MenuNode menuNode : children) {
            if (menuNode.permission != null) {
                this.permissionOptions.put(menuNode.permission, menuNode);
            }
            this.options.add(menuNode);
            menuNode.parent = this;
        }
        this.action = action;
        this.permission = permission;
        this.validatable = validatable;
        this.presentable = presentable;
    }

    public MenuNode executeNode(String username, Scanner scanner, List<Permission> userPermissions, MenuNode mainMenu) {
        List<MenuNode> available = availableOptions(mainMenu, userPermissions);
        List<Presentable> presentables = new ArrayList<>();
        available.forEach(m -> presentables.add(m == null ? null : m.presentable));
        String presentation = presentable.getPresentation(presentables);
        if (presentation != null) System.out.println(presentation);
        System.out.println(presentable.getPrompt() + ": ");
        Map<Permission, MenuNode> selectablePermissions = new HashMap<>();
        for (MenuNode menuNode : available) {
            if (menuNode != null && menuNode.permission != null) selectablePermissions.put(menuNode.permission, menuNode);
        }
        
        String input = scanner.nextLine();
        while (validatable != null && !validatable.validateInput(input, available)) {
            String retryMsg = presentable.getRetryMessage();
            if (retryMsg != null && !retryMsg.isEmpty()) {
                System.out.println(retryMsg + ": ");
            }
            input = scanner.nextLine();
        }
        MenuNode next = action.complete(username, input, available, selectablePermissions);
        if (next == null) throw new IllegalStateException("Cannot move to null node.");
        if (!(options.contains(next) || mainMenu == next || permissionOptions.containsValue(next))) throw new IllegalStateException("Cannot move to node that is not an option child of this node.");
        if (presentable.getCompleteMessage() != null && !presentable.getCompleteMessage().isEmpty()) System.out.println(presentable.getCompleteMessage());
        return next;
    }

    private List<MenuNode> availableOptions(MenuNode mainMenu, List<Permission> userPermissions) {
        ArrayList<MenuNode> available = new ArrayList<>(); // returning list of menu nodes because there exists nodes that don't have permissions associated (ex: main menu, parent);
        available.add(mainMenu);
        available.add(parent);
        for (MenuNode menuNode : options) {
            if (!menuNode.disabled) {
                available.add(menuNode);
            }
        }
        if (userPermissions != null) {
            for (Permission permission : userPermissions) {
                if (permissionOptions.containsKey(permission)) available.add(permissionOptions.get(permission));
            }
        }
        return available;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public static class MenuNodeBuilder {
        private Set<MenuNode> options = new HashSet<>();
        private Action action;
        private Validatable validatable;
        private OptionSelector optionSelector = new OptionSelector();
        private Presentable presentable;
        private Permission permission;

        public void addOptions(MenuNode... children) {
            options.addAll(Arrays.asList(children));
        }

        public void addOptions(Collection<MenuNode> children) {
            options.addAll(children);
        }

        /**
         * 
         * @param action
         * @param validatable
         */
        public void setActionAndValidator(Action action, Validatable validatable) {
            if (action == null && validatable != null) throw new IllegalArgumentException("If action is null, validatable must be null.");
            this.action = action;
            this.validatable = validatable;
        }

        public void setPresentable(Presentable presentable) {
            this.presentable = presentable;
        }

        public void setBasicPresentation(String listing) {
            this.presentable = new OptionPresenter(listing);
        }

        public void setPermission(Permission permission) {
            this.permission = permission;
        }

        /**
         * Builds the {@link MenuNode}.
         * 
         * @throws IllegalStateException if {@link MenuNodeBuilder#setPresentable(Presentable)} or {@link MenuNodeBuilder#setBasicPresentation(String, String, String)} has not been called.
         * @throws IllegalStateException {@link Presentable#getIdentifier()} is empty or null.
         * @return the built {@link MenuNode}.
         */
        public MenuNode build() {
            if (presentable == null) throw new IllegalStateException("Presentable cannot be null.");
            if (presentable.getIdentifier() == null || presentable.getIdentifier().isEmpty()) throw new IllegalStateException("Presentable cannot have a null or empty listing.");
            if (action == null) {
                action = optionSelector;
                validatable = optionSelector;
            }
            return new MenuNode(options, action, permission, validatable, presentable);
        }
    }
}
