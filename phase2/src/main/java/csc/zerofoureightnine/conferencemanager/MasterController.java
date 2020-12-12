package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.datacollection.DataController;
import csc.zerofoureightnine.conferencemanager.datacollection.DataPresenter;
import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeDataHolder;
import csc.zerofoureightnine.conferencemanager.datacollection.StoredDataGetter;
import csc.zerofoureightnine.conferencemanager.events.EventController;
import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.events.EventPresenter;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.users.UserController;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.UserPresenter;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;
import csc.zerofoureightnine.conferencemanager.users.session.SessionPresenter;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestController;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestManager;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestPresenter;

import java.util.UUID;

/**
 * Master controller. Stores and instantiates controller
 */
public class MasterController {
    private MessageManager messageManager;
    private PermissionManager permissionManager;
    private SpecialRequestManager specialRequestManager;
    private UserManager userManager;
    private EventManager eventManager;
    private RuntimeDataHolder runtimeDataHolder;
    private StoredDataGetter storedDataGetter;
    private DataPresenter dataPresenter;
    private DataController dataController;
    private EventPresenter eventPresenter;
    private UserPresenter userPresenter;
    private SessionPresenter sessionPresenter;
    private MessagePresenter messagePresenter;
    private SpecialRequestPresenter specialRequestPresenter;
    private SessionController sessionController;
    private UserController userController;
    private MessageController messageController;
    private EventController eventController;
    private SpecialRequestController specialRequestController;

    /**
     * Create a master controller
     *
     * @param userMap           {@link PersistentMap} map of users/permissions.
     * @param eventMap          {@link PersistentMap} map of events.
     * @param messageMap        {@link PersistentMap} messages.
     * @param specialRequestMap {@link PersistentMap} special requests.
     */
    public MasterController(PersistentMap<String, UserData> userMap, PersistentMap<String, EventData> eventMap,
                            PersistentMap<UUID, MessageData> messageMap, PersistentMap<UUID, SpecialRequestData> specialRequestMap) {

        createUseCases(userMap, eventMap, messageMap, specialRequestMap);
        createDataCollectors();
        createControllers();
        createPresenters();
    }

    private void createControllers() {
        this.dataController = new DataController();
        this.sessionController = new SessionController(userManager, permissionManager);
        this.messageController = new MessageController(messageManager, userManager, eventManager, permissionManager);
        this.eventController = new EventController(eventManager, userManager, permissionManager);
        this.specialRequestController = new SpecialRequestController(specialRequestManager);
        this.userController = new UserController(userManager, permissionManager);
    }

    private void createPresenters() {
        this.eventPresenter = new EventPresenter(eventManager);
        this.sessionPresenter = new SessionPresenter();
        this.messagePresenter = new MessagePresenter(messageManager, messageController.getInputMap());
        this.specialRequestPresenter = new SpecialRequestPresenter(specialRequestManager, specialRequestController.getInputMap());
        this.userPresenter = new UserPresenter();
        this.dataPresenter = new DataPresenter(runtimeDataHolder, storedDataGetter);
    }

    private void createDataCollectors() {
        this.runtimeDataHolder = new RuntimeDataHolder();
        this.storedDataGetter = new StoredDataGetter(messageManager, eventManager, specialRequestManager, userManager);
    }

    private void createUseCases(PersistentMap<String, UserData> userMap, PersistentMap<String, EventData> eventMap, PersistentMap<UUID, MessageData> messageMap, PersistentMap<UUID, SpecialRequestData> specialRequestMap) {
        this.messageManager = new MessageManager(messageMap);
        this.permissionManager = new PermissionManager(userMap);
        this.userManager = new UserManager(userMap);
        this.eventManager = new EventManager(eventMap);
        this.userManager = new UserManager(userMap);
        this.specialRequestManager = new SpecialRequestManager(specialRequestMap);
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public EventPresenter getEventPresenter() {
        return eventPresenter;
    }

    public SessionPresenter getSessionPresenter() {
        return sessionPresenter;
    }

    public MessagePresenter getMessagePresenter() {
        return messagePresenter;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public MessageController getMessageController() {
        return messageController;
    }

    public EventController getEventController() {
        return eventController;
    }

    public RuntimeDataHolder getRuntimeDataHolder() {
        return runtimeDataHolder;
    }

    public SpecialRequestPresenter getSpecialRequestPresenter() {
        return specialRequestPresenter;
    }

    public SpecialRequestController getSpecialRequestController() {
        return specialRequestController;
    }

    public UserController getUserController() {
        return userController;
    }

    public UserPresenter getUserPresenter() {
        return userPresenter;
    }

    public StoredDataGetter getStoredDataGetter() {
        return storedDataGetter;
    }

    public DataPresenter getDataPresenter() {
        return dataPresenter;
    }

    public DataController getDataController() {
        return dataController;
    }
}
