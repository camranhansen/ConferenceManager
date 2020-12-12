package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.events.EventController;
import csc.zerofoureightnine.conferencemanager.events.EventPresenter;
import csc.zerofoureightnine.conferencemanager.events.EventUI;
import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;
import csc.zerofoureightnine.conferencemanager.interaction.MenuBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.messaging.MessageUI;
import csc.zerofoureightnine.conferencemanager.users.UserUI;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;
import csc.zerofoureightnine.conferencemanager.users.session.SessionPresenter;
import csc.zerofoureightnine.conferencemanager.users.session.SessionUI;

public class MainUI {
    private MenuNode.MenuNodeBuilder root;
    private MasterController masterController;
    private MenuBuilder menuBuilder;

    public MainUI(MasterController masterController) {
        this.masterController = masterController;
        this.root = new MenuNode.MenuNodeBuilder("Main menu");
        this.menuBuilder = new MenuBuilder(root);
    }

    public void run() {
        addSectionUIs();
        ConsoleUserInterface userInterface = new ConsoleUserInterface(
                menuBuilder.build(masterController.getRuntimeDataHolder()));
        SessionController sessionController = masterController.getSessionController();
        sessionController.addObserver(userInterface);
        userInterface.interact();
    }

    private void addSectionUIs() {
        EventController eventController = masterController.getEventController();
        MessageController messageController = masterController.getMessageController();
        SessionController sessionController = masterController.getSessionController();
        MessagePresenter messagePresenter = masterController.getMessagePresenter();
        EventPresenter eventPresenter = masterController.getEventPresenter();
        SessionPresenter sessionPresenter = masterController.getSessionPresenter();
        menuBuilder.addSectionUI(new MessageUI(messageController, messagePresenter),
                new SessionUI(sessionController, sessionPresenter), new EventUI(eventController, eventPresenter),
                new UserUI(masterController.getUserController(), masterController.getUserPresenter(),
                        masterController.getSpecialRequestController(), masterController.getSpecialRequestPresenter()));
    }
}