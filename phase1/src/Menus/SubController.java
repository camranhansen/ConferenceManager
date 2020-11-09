package Menus;

import Users.Permission;

public interface SubController {

    //TODO: Implement in Messaging, Events and Registration(when required) controllers.
    //You are "invoking" the permission
    void performSelectedAction(String username, Permission permissionSelected);
}



