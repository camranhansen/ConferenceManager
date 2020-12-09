package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.Map;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public interface Action {

    /**
     * @param username the username of the user performing this action.
     * @param input the raw input from the user.
     * @param parent the parent node.
     * @return The next node to move to.
     */
    public MenuNode complete(String username, String input, Map<Integer, MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions);
}
