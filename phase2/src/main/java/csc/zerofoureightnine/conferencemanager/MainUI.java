package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.datacollection.DataUI;
import csc.zerofoureightnine.conferencemanager.events.EventActionHolder;
import csc.zerofoureightnine.conferencemanager.events.EventPresenter;
import csc.zerofoureightnine.conferencemanager.events.EventUI;
import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;
import csc.zerofoureightnine.conferencemanager.interaction.MenuBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.utils.ExitAction;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.messaging.MessageUI;
import csc.zerofoureightnine.conferencemanager.users.UserUI;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;
import csc.zerofoureightnine.conferencemanager.users.session.SessionPresenter;
import csc.zerofoureightnine.conferencemanager.users.session.SessionUI;

/**
 * The UI layer for the "main" page
 */
public class MainUI {
    private ExitAction exitAction;
    ConsoleUserInterface userInterface;
    private MenuNodeBuilder root;
    private MasterController masterController;
    private MenuBuilder menuBuilder;

    /**
     * Constructor for the main page. Special because exit action is constructed here.
     *
     * @param masterController
     */
    public MainUI(MasterController masterController) {
        this.exitAction = new ExitAction();
        this.masterController = masterController;
        this.root = new MenuNode.MenuNodeBuilder("Main menu");
        this.menuBuilder = new MenuBuilder(root);
    }

    /**
     * Run the program.
     */
    public void run() {
        addSectionUIs();
        MenuNodeBuilder exit = new MenuNodeBuilder("Exit", exitAction);
        MenuNode menu = menuBuilder.build(masterController.getRuntimeDataHolder());
        exit.addChildren(menu);
        exit.setCompletable((u, n) -> "Goodbye!");
        exit.build();
        userInterface = new ConsoleUserInterface(menu);
        menu.setTracker(masterController.getRuntimeDataHolder());
        exitAction.addObserver(userInterface);
        masterController.getSessionController().addObserver(userInterface);
        masterController.getSessionController().addObserver(masterController.getRuntimeDataHolder());
        masterController.getSessionController().addObserver(masterController.getUserInputValidator());
        userInterface.interact();
    }

    /**
     * Instantiate and add section UIs to the MainUI.
     */
    private void addSectionUIs() {
        EventActionHolder eventActionHolder = masterController.getEventActionHolder();
        MessageController messageController = masterController.getMessageController();
        SessionController sessionController = masterController.getSessionController();
        MessagePresenter messagePresenter = masterController.getMessagePresenter();
        EventPresenter eventPresenter = masterController.getEventPresenter();
        SessionPresenter sessionPresenter = masterController.getSessionPresenter();
        menuBuilder.addSectionUI(new MessageUI(messageController, messagePresenter),
                new SessionUI(sessionController, sessionPresenter), new EventUI(eventActionHolder, eventPresenter, masterController.getEventInputValidator()),
                new UserUI(masterController.getUserActions(), masterController.getUserPresenter(),
                        masterController.getSpecialRequestActions(), masterController.getSpecialRequestPresenter(), masterController.getUserInputValidator(), masterController.getSpecialRequestInputValidator()),
                new DataUI(masterController.getDataPresenter(), masterController.getDataController()));
    }
}