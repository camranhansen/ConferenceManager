package Menus;

import Users.Permission;

public interface SubController {

    void performSelectedAction(String username, Permission permissionSelected);

    void exitEarly();
}



