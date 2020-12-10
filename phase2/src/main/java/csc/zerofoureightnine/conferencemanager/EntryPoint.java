package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.menus.MenuBuilder;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;

public class EntryPoint {
    public static void main(String[] args) {
        SQLConfiguration configuration = new SQLConfiguration("db/data");
        MenuNodeBuilder root = new MenuNodeBuilder();
        root.setBasicPresentation("Main Menu");
        MenuBuilder menuBuilder = (new MenuBuilder(root));
        SessionController session = new SessionController(new SQLMap<>(configuration, UserData.class));
        menuBuilder.addSectionControllers(session);
        ConsoleUserInterface userInterface = new ConsoleUserInterface(menuBuilder.build());
        userInterface.interact();
    }

}
