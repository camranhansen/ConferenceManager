package Menus;

import Users.Permission;

import java.util.HashMap;
import java.util.List;

public class MenuController {



    public MenuController(String username, List<Permission> permissions,
                          HashMap<String, SubController> subcontrollers) {
    }

    //    //TODO: Move into MenuController. Presenter should not need to have any logic.
//    public void presentOptions(List<Permission> permissions){
//        if (permissions.size() < 8){
//            presentAllOptions(permissions);
//        }
//        else{
//            presentCategoryOptions(permissions);
//        }
//    }
}
