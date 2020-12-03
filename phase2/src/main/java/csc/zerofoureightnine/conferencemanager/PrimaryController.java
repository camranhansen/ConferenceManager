package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.events.EventController;
import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.gateway.csv.EventCSVGateway;
import csc.zerofoureightnine.conferencemanager.gateway.csv.MessageCSVGateway;
import csc.zerofoureightnine.conferencemanager.gateway.csv.UserCSVGateway;
import csc.zerofoureightnine.conferencemanager.menu.MenuController;
import csc.zerofoureightnine.conferencemanager.menu.SubController;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.*;
import csc.zerofoureightnine.conferencemanager.users.login.LoginController;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PrimaryController {
    private EventCSVGateway eventGateway;
    private UserCSVGateway userGateway;
    private MessageCSVGateway messageCSVGateway;
    private EventController eventController;
    private UserController userController;
    private MessageController messageController;
    private LoginController loginController;
    private MenuController menuController;
    private UserManager userManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    // private InputPrompter inputPrompter; //TODO: Maybe this + add InputPrompter parameter to individual controllers so it doesn't need to keep getting instantiated?

    public PrimaryController() {
        eventGateway = new EventCSVGateway();
        userGateway = new UserCSVGateway();
        messageManager = new MessageManager();
        eventManager = new EventManager();
        messageCSVGateway = new MessageCSVGateway(messageManager);
        userManager = new UserManager(new HashMap<>());
        userController = new UserController(userManager);
        eventController = new EventController(eventManager, userManager);
        messageController = new MessageController(messageManager, userManager, eventManager);
        loginController = new LoginController(userManager);
        // InputPrompter inputPrompter = new InputPrompter();
    }

    public void loadData(){ //TODO: MessageGateway read() method
        try {
            this.eventGateway.readEventsFromGateway(eventManager);
            this.userGateway.readUsersFromGateway(userManager);
            this.messageCSVGateway.readAllUsers();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read from files");
        }
    }

    public void saveData(){ //TODO: MessageGateway save() method
        //TODO: Notify system? Anyways, implement
        try {
            this.eventGateway.saveEvents(eventManager);
            this.userGateway.saveAllUsers(userManager);
            this.messageCSVGateway.saveAllUsers();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save to files");
        }

    }

    public void run() {
        loadData();
        String username = loginController.loginUser();
        HashMap<String, SubController> subcontrollers = new HashMap<>();
        subcontrollers.put("EVENT", eventController);
        subcontrollers.put("MESSAGE", messageController);
        subcontrollers.put("USER", userController);
        List<Permission> permissions = userManager.getPermissions(username);
        menuController = new MenuController(username, permissions, subcontrollers);
        menuController.makeMenu();
        saveData();
    }

    public void runWithoutGateways(){
        userManager.createUser("Camran","123", Template.ADMIN.getPermissions());
        userManager.createUser("Steve","asdf",Template.ADMIN.getPermissions());

        String username = loginController.loginUser();
        HashMap<String, SubController> subcontrollers = new HashMap<>();
        subcontrollers.put("EVENT", eventController);
        subcontrollers.put("MESSAGE", messageController);
        subcontrollers.put("USER", userController);
        List<Permission> permissions = userManager.getPermissions(username);
        menuController = new MenuController(username, permissions, subcontrollers);
        menuController.makeMenu();
        //Put exiting functions here

    }

}
