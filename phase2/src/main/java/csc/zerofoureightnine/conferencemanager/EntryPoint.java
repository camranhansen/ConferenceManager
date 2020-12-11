package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;
import csc.zerofoureightnine.conferencemanager.interaction.MenuBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
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
        PermissionManager permissionManager = new PermissionManager(userMap);
        MessageManager messageManager = new MessageManager(new SQLMap<>(configuration, MessageData.class));
        UserManager userManager = new UserManager(userMap);

        SessionPresenter sessionPresenter = new SessionPresenter();
        MessagePresenter messagePresenter = new MessagePresenter();

        SessionController sessionController = new SessionController(userManager, permissionManager);
        MessageController messageController = new MessageController(messageManager, userManager);
        
        menuBuilder.addSectionUI(new MessageUI(messageController, messagePresenter), new SessionUI(sessionController, sessionPresenter));
        ConsoleUserInterface userInterface = new ConsoleUserInterface(menuBuilder.build());
        sessionController.addObserver(userInterface);
        userInterface.interact();
    }

}
