package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionPresenter implements SessionObserver {
    private String loggedInUser;

    /**
     * Returns the prompt after the user attempts to log in.
     * @param username username of the user
     * @param n {@link TopicPresentable}
     * @return a {@link String} prompting whether the user has logged in or not
     */
    public String authenticationAttemptedMessage(String username, TopicPresentable n) {
        if (loggedInUser != null) return "Welcome " + loggedInUser + "!";
        return "Login incorrect. Returning to " + n.getIdentifier() + ".";
    }

    /**
     * Returns the prompt for users to enter their password.
     * @return the {@link String} "Password"
     */
    public String requestPassword() {
        return "Password";
    }

    /**
     * Returns the prompt for users to enter their username.
     * @return the {@link String} "Username"
     */
    public String requestUsername() {
        return "Username";
    }

    /**
     * Returns the prompt after users have successfully changed their password.
     * @param username username of the user
     * @param n {@link TopicPresentable}
     * @return a {@link String} prompting that the password has changed.
     */
    public String passwordChanged(String username, TopicPresentable n) {
        return "Password changed! Returning to " + n.getIdentifier() + ".";
    }

    /**
     * Returns the prompt after users have successfully created an account.
     * @param username username of the user
     * @param n {@link TopicPresentable}
     * @return the {@link String} "Account successfully created! You may now go login."
     */
    public String accountCreated(String username, TopicPresentable n) {
        return "Account successfully created! You may now go login.";
    }

    /**
     * Returns the prompt when username already exists when creating an account.
     * @return the {@link String} "Username already exists, try again"
     */
    public String userExistsError() {
        return "Username already exists, try again";
    }

    /**
     * Update user's login status.
     * @param username username of the user
     * @param permissions list of {@link Permission}
     * @param loggedIn login status of the user
     */
    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        if (loggedIn) {
            loggedInUser = username;
        } else {
            loggedInUser = null;
        }
    }

    /**
     * Returns the prompt after users have logged out successfully.
     * @param username username of the user
     * @param n {@link TopicPresentable}
     * @return the {@link String} "Logged out successfully!"
     */
    public String loggedOut(String username, TopicPresentable n){
        return "Logged out successfully!";
    }

}
