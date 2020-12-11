package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class SessionController { // UI
    private Set<SessionObserver> observers = new HashSet<>();
    private Map<String, String> inputMap = new HashMap<>();
    private UserManager userManager;
    private PermissionManager permissionManager;
    private String loggedInUser;

    public SessionController(UserManager userManager, PermissionManager permissionManager) {
        this.userManager = userManager;
        this.permissionManager = permissionManager;
    }

    public int loginUser(String username, String input, List<TopicPresentable> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        if (userManager.userExists(attemptingUser) && userManager.getPassword(attemptingUser).equals(attemptingPassword)) {
            onAuthenticationStateChange(attemptingUser, true);
        } else {
            onAuthenticationStateChange(attemptingUser, false);
        }
        return 0;
    }

    public int logOutUser(String username, String input, List<TopicPresentable> selectableOptions){
        onAuthenticationStateChange(username, false);
        return 0;
    }

    public boolean checkUserNotExist(String input, List<TopicPresentable> options) {
        return !userManager.userExists(input);
    }

    public int createUser(String username, String input, List<TopicPresentable> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        userManager.createUser(attemptingUser, attemptingPassword, Template.ATTENDEE.getPermissions());
        return 0;
    }


    public void addObserver(SessionObserver observer) {
        this.observers.add(observer);
    }

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

    public Map<String, String> getInputMap() {
        return inputMap;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

}
