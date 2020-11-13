import Events.EventController;
import Events.EventManager;
import Gateway.EventGateway;
import Gateway.MessageGateway;
import Gateway.UserGateway;
import Menus.MenuController;
import Menus.SubController;
import Messaging.MessageController;
import Messaging.MessageManager;
import Users.*;

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
        eventController = new EventController(eventManager);
        userManager = new UserManager(new HashMap<>());
        userController = new UserController(userManager);
        messageController = new MessageController(messageManager, userManager, eventManager);
        loginController = new LoginController();
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
        String username = loginController.loginUser(userManager);
        HashMap<String, SubController> subcontrollers = new HashMap<>();
        subcontrollers.put("EVENT", eventController);
        subcontrollers.put("MESSAGE", messageController);
        subcontrollers.put("USER", userController);
        List<Permission> permissions = userManager.getPermissions(username);
        menuController = new MenuController(username, permissions, subcontrollers);
        menuController.selectSubcontroller();
    }

}
