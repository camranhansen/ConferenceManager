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
     * @param userManager {@link UserManager}
     * @param permissionManager {@link PermissionManager}
     */
    public SessionController(UserManager userManager, PermissionManager permissionManager) {
        this.userManager = userManager;
        this.permissionManager = permissionManager;

        if (!userManager.userExists("admin")) {
            userManager.createUser("admin", "password", Template.ADMIN.getPermissions());
        }
    }

    /**
     * Login the current user.
     * @param username username of the user
     * @param input user input
     * @param selectableOptions the options available to user
     * @return int 0 after the user is logged in.
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
     * Logout the current user.
     * @param username username of the user
     * @param input user input
     * @param selectableOptions the options available to user
     * @return int 0 after the user is logged out.
     */
    public int logOutUser(String username, String input, List<TopicPresentable> selectableOptions){
        onAuthenticationStateChange(username, false);
        return 0;
    }

    /**
     * Check whether the given username exists or not.
     * @param input user input, username being checked
     * @param options the options available to user
     * @return true if the username exists, otherwise return false.
     */
    public boolean checkUserNotExist(String input, List<TopicPresentable> options) {
        return !userManager.userExists(input);
    }

    /**
     * Create an user account.
     *
     * @param username          this is null, because no user is logged in currently - since this is in sessionController
     * @param input             user input
     * @param selectableOptions the options available to user
     * @return int 1 after the new user account is created
     */
    public int createUser(String username, String input, List<TopicPresentable> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        userManager.createUser(attemptingUser, attemptingPassword, Template.ATTENDEE.getPermissions());
        return 1;
    }

    /**
     * Change the user's password.
     * @param username username of the user
     * @param input user input, new password
     * @param opts the options available to user
     * @return int 1 after the password has changed
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

    /**
     * Update user's login status.
     * @param username username of the user
     * @param loggedIn status of the user
     */
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
