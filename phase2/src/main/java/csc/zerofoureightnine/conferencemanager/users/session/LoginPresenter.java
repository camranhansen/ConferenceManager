package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.Presentable;

public class LoginPresenter implements Presentable, SessionObserver {
    boolean collect;
    String username;

    public LoginPresenter(boolean collect) {
        this.collect = collect;
    }

    @Override
    public String getIdentifier() {
        return "Login";
    }

    @Override
    public String getPrompt() {
        return "Please enter your " + (collect ? "username" : "password");
    }

    @Override
    public String getRetryMessage() {
        return null;
    }

    @Override
    public String getPresentation(List<Presentable> options) {
        return null;
    }

    @Override
    public String getCompleteMessage() {
        if (collect) return null;
        if (username != null) return "Welcome " + username;
        return "Login failed. Please try again.";
    }

    @Override
    public void authenticationAttempted(String username, boolean success) {
        if (success) {
            this.username = username;
        } else {
            this.username = null;
        }
    }

    @Override
    public void loggedOut(String username) {
        this.username = null;
    }
    
}
