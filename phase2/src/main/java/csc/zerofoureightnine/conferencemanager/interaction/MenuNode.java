package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.control.Validatable;
import csc.zerofoureightnine.conferencemanager.interaction.general.OptionPresenter;
import csc.zerofoureightnine.conferencemanager.interaction.general.OptionSelector;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Completable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Listable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Nameable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Promptable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Reattemptable;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class MenuNode { // UI
    private Permission permission; // may be null.
    private EnumMap<Permission, MenuNode> permissionOptions = new EnumMap<>(Permission.class); // !null, may be empty.
    private Set<MenuNode> children = new HashSet<>(); // !null but may be empty.
    private MenuNode parent; // may be null, no parent then, no going back up the menu.
    private Validatable validatable; // may be null, in which case any input is accepted.
    private Nameable nameable; // !null
    private Action action; // !null
    private Completable completable; // !null
    private Promptable promptable;
    private Listable listable;
    private Reattemptable reattemptable;
    private boolean disabled = false;

    private MenuNode(Nameable nameable, Action action, Completable completable) {
        this.nameable = nameable;
        this.action = action;
        this.completable = completable;
    }

    /**
     * Executes the current node. This involves first checking which options are
     * accessible to the user. {@link Presentable#getOptionsPresentation(List)} from
     * the presenter associated with this node is called and printed if the result
     * isn't null. {@link Presentable#getPrompt()} is called, and the user is
     * required to enter input.
     * 
     * @param username        A string representing the username of the user
     *                        currently interacting with this node. May be null if
     *                        no user has logged in.
     * @param scanner         A scanner to use to take in the user input.
     * @param userPermissions The permissions the user has. May be null if no user
     *                        has logged in.
     * @param mainMenu        The {@link MenuNode} respresenting the main menu for
     *                        the user to jump there.
     * @return The {@link MenuNode} to move to next. This must be a child, parent,
     *         or the main menu.
     * @throws IllegalStateException if the resulting node to jump to is null, not a
     *                               child, parent or main menu.
     */
    public MenuNode executeNode(String username, Scanner scanner, List<Permission> userPermissions, MenuNode mainMenu) {
        List<MenuNode> available = availableOptions(mainMenu, userPermissions);
        List<Nameable> nameables = new ArrayList<>();
        available.forEach(m -> nameables.add(m == null ? null : m.nameable));
        System.out.println(this.nameable.getIdentifier() + ":");
        if (listable != null)
            System.out.println(listable.getOptionsPresentation(nameables));
        String input = null;
        if (promptable != null) {
            System.out.print(promptable.getPrompt() + ": ");
            input = scanner.nextLine();
            while (validatable != null && !validatable.validateInput(input, available)) {
                if (reattemptable != null) {
                    System.out.print(reattemptable.getRetryMessage() + ": ");
                    input = scanner.nextLine();
                } else {
                    return parent != null ? parent : mainMenu;
                }
            }
        }
        MenuNode next = action.complete(username, input, available);
        if (next == null)
            throw new IllegalStateException("Cannot move to null node.");
        if (!(children.contains(next) || mainMenu == next || permissionOptions.containsValue(next)))
            throw new IllegalStateException("Cannot move to node that is not an option child of this node.");
        System.out.println(completable.getCompleteMessage(next));
        return next;
    }

    private List<MenuNode> availableOptions(MenuNode mainMenu, List<Permission> userPermissions) {
        ArrayList<MenuNode> available = new ArrayList<>(); // returning list of menu nodes because there exists nodes
                                                           // that don't have permissions associated (ex: main menu,
                                                           // parent);
        available.add(mainMenu);
        available.add(parent);
        for (MenuNode menuNode : children) {
            if (!menuNode.disabled) {
                available.add(menuNode);
            }
        }
        if (userPermissions != null) {
            for (Permission permission : userPermissions) {
                if (permissionOptions.containsKey(permission))
                    available.add(permissionOptions.get(permission));
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
        private Set<MenuNode> children = new HashSet<>();
        private final Action action; // !null
        private final Nameable displayName; // !null
        private final Completable completable; // !null
        private Validatable validatable;
        private Permission permission;
        private Promptable promptable;
        private Listable listable;
        private Reattemptable reattemptable;

        /**
         * Instantiates the {@link MenuNodeBuilder} with minimal requirements.
         * 
         * @param displayName A {@link Nameable} presenter that generates a display name
         *                    for this menu.
         * @param action      An {@link Action} that is executed upon after input is
         *                    validated, if input is being validated.
         * @param completable A {@link Completable} presenter generating information
         *                    regarding the completion of execution of this menu.
         */
        public MenuNodeBuilder(Nameable displayName, Action action, Completable completable) {
            this.displayName = displayName;
            this.action = action;
            this.completable = completable;
        }

        /**
         * Instantiates the {@link MenuNodeBuilder} with minimal requirements.
         * 
         * @param displayName A {@link Nameable} presenter that generates a display name
         *                    for this menu.
         * @param action      An {@link Action} that is executed upon after input is
         *                    validated, if input is being validated.
         * @param completable A {@link Completable} presenter generating information
         *                    regarding the completion of execution of this menu.
         */
        public MenuNodeBuilder(String displayName, Action action, Completable completable) {
            this.displayName = () -> displayName;
            this.action = action;
            this.completable = completable;
        }

        /**
         * Instantiates the {@link MenuNodeBuilder} in preparation for a decision making
         * {@link MenuNode}. That is, if {@link MenuNodeBuilder#build()} is called
         * without changing anything, this menu will serve to allow the user to choose
         * from a list of children {@link MenuNode}s by inputting an integer
         * corresponding to their selection.
         * 
         * @param displayName a constant display name to be used for this node.
         */
        public MenuNodeBuilder(String displayName) {
            OptionPresenter optionPresenter = new OptionPresenter(displayName);
            OptionSelector optionSelector = new OptionSelector();
            this.displayName = optionPresenter;
            this.promptable = optionPresenter;
            this.reattemptable = optionPresenter;
            this.listable = optionPresenter;
            this.completable = optionPresenter;
            this.action = optionSelector;
            this.validatable = optionSelector;
        }

        /**
         * Instantiates the {@link MenuNodeBuilder} in preparation for a decision making
         * {@link MenuNode}. That is, if {@link MenuNodeBuilder#build()} is called
         * without changing anything, this menu will serve to allow the user to choose
         * from a list of children {@link MenuNode}s by inputting an integer
         * corresponding to their selection.
         * 
         * @param displayName a constant display name to be used for this node.
         */
        public MenuNodeBuilder(Nameable nameable) {
            OptionPresenter optionPresenter = new OptionPresenter("");
            OptionSelector optionSelector = new OptionSelector();
            this.displayName = nameable;
            this.promptable = optionPresenter;
            this.reattemptable = optionPresenter;
            this.listable = optionPresenter;
            this.completable = optionPresenter;
            this.action = optionSelector;
            this.validatable = optionSelector;
        }

        /**
         * Sets the {@link Validatable} to be used to validate user input. If this is
         * null, no validation of user input will occur before
         * {@link Action#complete(String, String, List)}.
         * 
         * @param validatable The {@link Validatable} used to check user input.
         */
        public void setValidatable(Validatable validatable) {
            this.validatable = validatable;
        }

        /**
         * Sets the {@link Permission} associated this {@link MenuNode}. If this is
         * null, this menu node will be displayed no matter what permissions the
         * currently logged in user has. Furthermore, these are the nodes displayed to
         * guest users. If this is set, only users with this permission will be able to
         * access this {@link MenuNode}.
         * 
         * @param permission The {@link Permission} to associated this node with.
         */
        public void setPermission(Permission permission) {
            this.permission = permission;
        }

        /**
         * Sets the {@link Promptable} presenter. If this is null, when the user is
         * interacting with this node, they will not be prompted for input nor will they
         * have the option to input. The {@link MenuNode} will move to calling it's
         * {@link Action#complete(String, String, List)}.
         * 
         * @param promptable the promptable presenter used to generate a request for the
         *                   user to input information.
         */
        public void setPromptable(Promptable promptable) {
            this.promptable = promptable;
        }

        /**
         * Sets the {@link Listable} presenter. If this is null, when the user is
         * interacting with this {@link MenuNode}, no list of options message is
         * displayed.
         * 
         * @param listable
         */
        public void setListable(Listable listable) {
            this.listable = listable;
        }

        /**
         * Sets the {@link Reattemptable} presenter. If this is null, when the user
         * fails to enter valid input (determined by the {@link Validatable}) the user
         * will be moved to the parent {@link MenuNode} or the main menu of there is no
         * parent.
         * 
         * @param reattemptable the reattemptable presenter to use to organize displayed
         *                      reattempt content.
         */
        public void setReattemptable(Reattemptable reattemptable) {
            this.reattemptable = reattemptable;
        }

        /**
         * Adds the {@link Collection} of {@link MenuNode}s as children to this new
         * node. Their parent will automatically be set to this node.
         * 
         * @param children The children to add to this node.
         */
        public void addChildren(Collection<MenuNode> children) {
            this.children.addAll(children);
        }

        /**
         * Adds the {@link MenuNode}s as children to this new node. Their parent will
         * automatically be set to this node.
         * 
         * @param children The children to add to this node.
         */
        public void addChildren(MenuNode... children) {
            this.children.addAll(Arrays.asList(children));
        }

        public MenuNode build() {
            MenuNode res = new MenuNode(this.displayName, this.action, this.completable);
            res.validatable = this.validatable;
            res.permission = this.permission;
            res.promptable = this.promptable;
            res.listable = this.listable;
            res.reattemptable = this.reattemptable;

            for (MenuNode menuNode : children) {
                menuNode.parent = res;
                if (menuNode.permission != null)
                    res.permissionOptions.put(menuNode.permission, menuNode);
            }
            res.children = this.children;

            return res;
        }
    }
}
