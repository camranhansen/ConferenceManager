package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;
import csc.zerofoureightnine.conferencemanager.interaction.MenuBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.GeneralMenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.messaging.MessageUI;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;
import csc.zerofoureightnine.conferencemanager.users.session.SessionPresenter;
import csc.zerofoureightnine.conferencemanager.users.session.SessionUI;

public class EntryPoint {
    public static void main(String[] args) {
        SQLConfiguration configuration = new SQLConfiguration("db/data");
        MenuNodeBuilder root = new MenuNodeBuilder("Main menu");
        MenuBuilder menuBuilder = new MenuBuilder(root);

        PersistentMap<String, UserData> userMap = new SQLMap<>(configuration, UserData.class);
        UserManager userManager = new UserManager(userMap);
        PermissionManager permissionManager = new PermissionManager(userMap);
        MessageManager messageManager = new MessageManager(new SQLMap<>(configuration, MessageData.class));
        SessionController sessionController = new SessionController(new SessionPresenter(), userManager, permissionManager);
        MessageController messageController = new MessageController(new MessagePresenter(), messageManager, userManager);
        
        menuBuilder.addSectionUI(new MessageUI(messageController), new SessionUI(sessionController));
        ConsoleUserInterface userInterface = new ConsoleUserInterface(menuBuilder.build());
        sessionController.addObserver(userInterface);
        userInterface.interact();
    }

}
