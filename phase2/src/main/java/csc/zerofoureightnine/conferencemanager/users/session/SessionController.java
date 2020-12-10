package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class SessionController { // UI
    private Set<SessionObserver> observers = new HashSet<>();
    private Map<String, String> inputMap = new HashMap<>();
    private SessionPresenter presenter;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private String loggedInUser;

    public SessionController(SessionPresenter presenter, UserManager userManager, PermissionManager permissionManager) {
        this.presenter = presenter;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        addObserver(presenter);
    }

    public MenuNode loginUser(String username, String input, List<MenuNode> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        if (userManager.userExists(attemptingUser) && userManager.getPassword(attemptingUser).equals(attemptingPassword)) {
            onAuthenticationStateChange(attemptingUser, true);
        } else {
            onAuthenticationStateChange(attemptingUser, false);
        }
        return selectableOptions.get(0);
    }

    public MenuNode logOutUser(String username, String input, List<MenuNode> selectableOptions){
        onAuthenticationStateChange(username, false);
        return selectableOptions.get(0);
    }

    public boolean checkUserNotExist(String input, List<MenuNode> opts) {
        return !userManager.userExists(input);
    }

    public MenuNode createUser(String username, String input, List<MenuNode> selectableOptions) {
        String attemptingUser = inputMap.get("user");
        String attemptingPassword = inputMap.get("password");
        userManager.createUser(attemptingUser, attemptingPassword, Template.ATTENDEE.getPermissions());
        return selectableOptions.get(0);
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

    public SessionPresenter getPresenter() {
        return presenter;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

}
