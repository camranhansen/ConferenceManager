package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.events.EventController;
import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.input.validators.MessageContentValidator;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.users.UserController;
import csc.zerofoureightnine.conferencemanager.users.permission.Category;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.HashMap;

public class SubControllerDecider {
    private final HashMap<Category, SubController> subcontrollers;

    public SubControllerDecider(HashMap<Category, SubController> subcontrollers) {
        this.subcontrollers = subcontrollers;
    }

    public void DecideSubControllerAndRunMethod(String username, Permission permissionSelected, HashMap<InputStrategy, String> inputHistory){
        //This is... the smallest method call ever?
        subcontrollers.get(permissionSelected.getCategory()).performSelectedAction(username, permissionSelected, inputHistory);

    }
}
