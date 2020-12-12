package csc.zerofoureightnine.conferencemanager.users.session;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.List;

/**
 * Interface denoting a session observer. I.e. - a class that needs information denoting whenever someone logs in or not.
 */
public interface SessionObserver {
    /**
     * Change the authentication state.
     *
     * @param username    the newly logged or out user, represented by their username.
     * @param permissions a list of the user's {@link Permission}.
     * @param loggedIn    true if someone is logged in, false if otherwise.
     */
    void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn);
}
