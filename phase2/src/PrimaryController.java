import events.EventController;
import events.EventManager;
import gateway.EventGateway;
import gateway.MessageGateway;
import gateway.UserGateway;
import menu.MenuController;
import menu.SubController;
import messaging.MessageController;
import messaging.MessageManager;
import users.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PrimaryController {
    private EventGateway eventGateway;
    private UserGateway userGateway;
    private MessageGateway messageGateway;
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
        eventGateway = new EventGateway();
        userGateway = new UserGateway();
        messageManager = new MessageManager();
        eventManager = new EventManager();
        messageGateway = new MessageGateway(messageManager);
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
            this.messageGateway.readAllUsers();
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
            this.messageGateway.saveAllUsers();
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
        userManager.createUser("Camran","123",Template.ADMIN.getPermissions());
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
