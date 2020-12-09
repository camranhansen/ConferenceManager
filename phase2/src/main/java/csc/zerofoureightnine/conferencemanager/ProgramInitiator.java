package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;

public class ProgramInitiator {
    private MenuNode mainMenu;
    private SessionController sessionController;
    private SQLConfiguration sqlConfiguration;
    private SQLMap<String, UserData> userSqlMap;

    public ProgramInitiator() {
        this.sqlConfiguration = new SQLConfiguration("db/data");
        userSqlMap = new SQLMap<>(sqlConfiguration, UserData.class);
        this.sessionController = new SessionController(userSqlMap);

        MenuNodeBuilder mainBuilder = new MenuNodeBuilder();
        mainBuilder.addOptions(sessionController.getEntryMenuNodes());
        mainBuilder.setBasicPresentation("Main menu");
        mainMenu = mainBuilder.build();
    }

    public MenuNode getMainMenu() {
        return mainMenu;
    }

}
