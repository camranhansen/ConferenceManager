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

    /**
     * Executes the current node. This involves first checking which options are accessible to the user. {@link Presentable#getPresentation(List)}
     * from the presenter associated with this node is called and printed if the result isn't null.  {@link Presentable#getPrompt()} is called, and the
     * user is required to enter input.
     * @param username A string representing the username of the user currently interacting with this node. May be null if no user has logged in.
     * @param scanner A scanner to use to take in the user input.
     * @param userPermissions The permissions the user has. May be null if no user has logged in.
     * @param mainMenu The {@link MenuNode} respresenting the main menu for the user to jump there.
     * @return The {@link MenuNode} to move to next. This must be a child, parent, or the main menu.
     * @throws IllegalStateException if the resulting node to jump to is null, not a child, parent or main menu.
     */
    public MenuNode executeNode(String username, Scanner scanner, List<Permission> userPermissions, MenuNode mainMenu) {
        List<MenuNode> available = availableOptions(mainMenu, userPermissions);
        List<Presentable> presentables = new ArrayList<>();
        available.forEach(m -> presentables.add(m == null ? null : m.presentable));
        String presentation = presentable.getPresentation(presentables);
        if (presentation != null) System.out.println(presentation);
        System.out.println(presentable.getPrompt() + ": ");
        
        String input = scanner.nextLine();
        while (validatable != null && !validatable.validateInput(input, available)) {
            String retryMsg = presentable.getRetryMessage();
            if (retryMsg != null && !retryMsg.isEmpty()) {
                System.out.println(retryMsg + ": ");
            }
            input = scanner.nextLine();
        }
        MenuNode next = action.complete(username, input, available);
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

        /**
         * Adds the {@link MenuNode}s as children to this new node. Their parent
         * will automatically be set to this node.
         * @param children The children to add to this node.
         */
        public void addOptions(MenuNode... children) {
            options.addAll(Arrays.asList(children));
        }

        /**
         * Adds the {@link Collection} of {@link MenuNode}s as children to
         * this new node. Their parent will automatically be set to this node.
         * @param children The children to add to this node.
         */
        public void addOptions(Collection<MenuNode> children) {
            options.addAll(children);
        }

        /**
         * Sets both the {@link Action} and the {@link Validatable}. 
         * If the validatable is null, no check will be performed on the input.
         * If action is null, {@link OptionSelector} will be used as the action.
         * @param action
         * @param validatable
         * @throws IllegalArgumentException when action is null and valitable is not null
         *                                  as validatable will be replaced with {@link OptionSelector}. 
         */
        public void setActionAndValidator(Action action, Validatable validatable) {
            if (action == null && validatable != null) throw new IllegalArgumentException("If action is null, validatable must be null.");
            this.action = action;
            this.validatable = validatable;
        }

        /**
         * Sets the {@link Presentable} to be used for the resulting {@link MenuNode}.
         * Either this or {@link MenuNodeBuilder#setBasicPresentation(String)} needs to be called
         * before building with a value that is not null.
         * @param presentable The presentable 
         */
        public void setPresentable(Presentable presentable) {
            this.presentable = presentable;
        }

        /**
         * Creates a {@link OptionPresenter} with the given listing. Can only be used if
         * {@link MenuNodeBuilder#setActionAndValidator(Action, Validatable)} has {@code action} and
         * {@code validatable} set to null.
         * 
         * @param identifier The identifier is what is shown when this item is listed as an option
         *                   for traversal. Cannot be empty or null.
         * @throws IllegalStateException if {@link MenuNodeBuilder#setActionAndValidator(Action, Validatable)}
         *                               has {@code action} and {@code validatable} set not to null.
         * 
         */
        public void setBasicPresentation(String identifier) {
            if (action == null || validatable == null) throw new IllegalStateException("Action and validatable need to be null to use this.");
            if (identifier == null || identifier.isEmpty()) throw new IllegalArgumentException("Identifier cannot be null or empty.");
            this.presentable = new OptionPresenter(identifier);
        }

        /**
         * Associates a permission with the node. By default is null, where anyone can access the node.
         * @param permission the permission to associate with built {@link MenuNode}.
         */
        public void setPermission(Permission permission) {
            this.permission = permission;
        }

        /**
         * Builds the {@link MenuNode}. See individual operations for more details on implication of
         * what their values do.
         * 
         * @throws IllegalStateException if {@link MenuNodeBuilder#setPresentable(Presentable)} or 
         * {@link MenuNodeBuilder#setBasicPresentation(String, String, String)} has not been called.
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
