package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public interface SessionObserver {
    void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn);
}
