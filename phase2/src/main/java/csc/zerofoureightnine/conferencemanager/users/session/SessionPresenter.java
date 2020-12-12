package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionPresenter implements SessionObserver {
    private String loggedInUser;

    public String authenticationAttemptedMessage(String username, TopicPresentable n) {
        if (loggedInUser != null) return "Welcome " + loggedInUser + "!";
        return "Login incorrect. Returning to " + n.getIdentifier() + ".";
    }

    public String requestPassword() {
        return "Password";
    }

    public String requestUsername() {
        return "Username";
    }

    public String passwordChanged(String username, TopicPresentable n) {
        return "Password changed! Returning to " + n.getIdentifier() + ".";
    }

    public String accountCreated(String username, TopicPresentable n) {
        return "Account successfully created! You may now go login.";
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

    public String loggedOut(String username, TopicPresentable n){
        return "Logged out successfully!";
    }

}
