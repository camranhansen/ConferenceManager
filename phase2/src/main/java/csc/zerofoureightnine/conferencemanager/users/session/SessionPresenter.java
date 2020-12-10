package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionPresenter implements SessionObserver {
    private String loggedInUser;

    public String authenticationAttemptedMessage(MenuNode n) {
        if (loggedInUser != null) return "Welcome " + loggedInUser + "!";
        return "Login incorrect please try again.";
    }

    public String requestPassword() {
        return "Password";
    }

    public String requestUsername() {
        return "Username";
    }

    public String accountCreated(MenuNode n) {
        return "Account successfully created!";
    }

    public String userExistsError() {
        return "Username already exists, try again";
    }

    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        if (loggedIn) {
            loggedInUser = username;
        } else {
            loggedInUser = null;
        }
    }

    public String loggedOut(MenuNode n){
        return "Logged out successfully!!!!!!!!!";
    }

}
