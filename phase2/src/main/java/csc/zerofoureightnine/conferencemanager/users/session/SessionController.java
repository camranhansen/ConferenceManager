package csc.zerofoureightnine.conferencemanager.users.session;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.*;

public class SessionController {
    private Set<SessionObserver> observers = new HashSet<>();
    private Map<String, String> inputMap = new HashMap<>();
    private UserManager userManager;
    private PermissionManager permissionManager;
    private String loggedInUser;

    /**
     * Initiates SessionController
     * @param userManager associating {@link UserManager}
     * @param permissionManager associating {@link PermissionManager}
     */
    public SessionController(UserManager userManager, PermissionManager permissionManager) {
        this.userManager = userManager;
        this.permissionManager = permissionManager;

        if (!userManager.userExists("admin")) {
            userManager.createUser("admin", "password", Template.ADMIN.getPermissions());
        }
    }

    /**
     * Login the current user,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int loginUser(String username, String input, List<TopicPresentable> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        if (userManager.userExists(attemptingUser) && userManager.getPassword(attemptingUser).equals(attemptingPassword)) {
            onAuthenticationStateChange(attemptingUser, true);
        } else {
            onAuthenticationStateChange(attemptingUser, false);
        }
        return 1;
    }

    /**
     * Logout the current user,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int logOutUser(String username, String input, List<TopicPresentable> selectableOptions){
        onAuthenticationStateChange(username, false);
        return 0;
    }

    /**
     * Check whether the given username exists or not.
     * @param input user input, username being checked
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if the username exists, otherwise return false.
     */
    public boolean checkUserNotExist(String input, List<TopicPresentable> options) {
        return !userManager.userExists(input);
    }

    /**
     * Create an user account,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 1.
     */
    public int createUser(String username, String input, List<TopicPresentable> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        userManager.createUser(attemptingUser, attemptingPassword, Template.ATTENDEE.getPermissions());
        return 1;
    }

    /**
     * Change the user's password.
     * according to previous user input,
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param opts the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 1.
     */
    public int changePassword(String username, String input, List<TopicPresentable> opts) {
        userManager.setPassword(username, input);
        return 1;
    }

    /**
     * Add new {@code observer} to the set of observers.
     * @param observer {@link SessionObserver}
     */
    public void addObserver(SessionObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Remove {@code observer} from the set of observers.
     * @param observer {@link SessionObserver}
     */
    public void removeObserver(SessionObserver observer) {
        this.observers.remove(observer);
    }


    private void onAuthenticationStateChange(String username, boolean loggedIn) {
        for (SessionObserver sessionObserver : observers) {
            sessionObserver.authenticationStateChanged(username, permissionManager.getPermissions(username), loggedIn);
        }
        if (loggedIn) {
            this.loggedInUser = username;
        } else {
            this.loggedInUser = null;
        }
    }

    /**
     * Returns the inputMap of this SessionController
     * @return a {@link Map} that maps strings to their associating user inputs.
     */
    public Map<String, String> getInputMap() {
        return inputMap;
    }

    /**
     * Returns the status of the user.
     * @return true if the user is logged in, false otherwise.
     */
    public String getLoggedInUser() {
        return loggedInUser;
    }

}
