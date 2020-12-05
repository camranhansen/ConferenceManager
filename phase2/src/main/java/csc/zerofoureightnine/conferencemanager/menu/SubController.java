package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.HashMap;

public interface SubController {
    /**
     * Performs a selected action corresponding to a permission in the specific subcontroller that implements this interface
     * @param username The username of the user that is invoking the permission selected
     * @param permissionSelected the permission that corresponds to an action, that will be performed in the subcontroller.
     * @param inputHistory the hashmap representing the input history of the user, with {@link InputStrategy} key and {@link String} value
     */
    void performSelectedAction(String username, Permission permissionSelected, HashMap<InputStrategy, String> inputHistory);

}



