package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public interface SubController {
    /**
     * Performs a selected action corresponding to a permission in the specific subcontroller that implements this interface
     * @param username The username of the user that is invoking the permission selected
     * @param permissionSelected the permission that corresponds to an action, that will be performed in the subcontroller.
     */
    void performSelectedAction(String username, Permission permissionSelected);

}



