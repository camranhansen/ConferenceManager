package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;
import java.util.Map;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public interface Action {

    /**
     * Completes an action.
     * @param username the currently logged in users username. May be null if nobody is logged in.
     * @param input the user input after being validated.
     * @param selectableOptions The options the user has available to them at this point. 0 will always be main menu, 1 will always be parent.
     *                          Additionally, parent may be null if the node does not have a parent.
     * @param selectablePermissions
     * @return
     */
    public MenuNode complete(String username, String input, List<MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions);
}
