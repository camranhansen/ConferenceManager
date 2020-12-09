package csc.zerofoureightnine.conferencemanager.users.session;

public interface SessionObserver {
    void authenticationAttempted(String username, boolean success);
    void loggedOut(String username);
}
