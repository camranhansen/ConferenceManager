package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.Category;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.HashMap;
import java.util.Map;

public class SubControllerDecider {
    private final Map<Category, SubController> subcontrollers;

    public SubControllerDecider(Map<Category, SubController> subcontrollers) {
        this.subcontrollers = subcontrollers;
    }

    public void decideSubControllerAndRunMethod(String username, Permission permissionSelected, Map<InputStrategy, String> inputHistory){
        //This is... the smallest method call ever?
        subcontrollers.get(permissionSelected.getCategory()).performSelectedAction(username, permissionSelected, inputHistory);

    }
}
