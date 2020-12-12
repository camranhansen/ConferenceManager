package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeDataHolder;
import csc.zerofoureightnine.conferencemanager.events.EventController;
import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.events.EventPresenter;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;
import csc.zerofoureightnine.conferencemanager.users.session.SessionPresenter;

import java.util.HashMap;

public class MasterController {
    private MessageManager messageManager;
    private PermissionManager permissionManager;
    private UserManager userManager;
    private EventManager eventManager;
    private EventPresenter eventPresenter;
    private SessionPresenter sessionPresenter;
    private MessagePresenter messagePresenter;
    private SessionController sessionController;
    private MessageController messageController;
    private EventController eventController;
    private RuntimeDataHolder runtimeDataHolder;

    public MasterController(PersistentMap<String, UserData> userMap,
                            PersistentMap<String, EventData> eventMap, PersistentMap<String, MessageData> messageMap){
        this.messageManager = new MessageManager(messageMap);
        this.permissionManager = new PermissionManager(userMap);
        this.userManager = new UserManager(userMap);
        this.eventManager = new EventManager(eventMap);
        HashMap<String, String> inputMap = new HashMap<>();
        this.eventPresenter = new EventPresenter(eventManager, inputMap);
        this.sessionPresenter = new SessionPresenter();
        this.messagePresenter = new MessagePresenter(messageManager, inputMap);
        this.sessionController = new SessionController(userManager, permissionManager);
        this.messageController = new MessageController(messageManager, userManager, eventManager,
                permissionManager);
        this.eventController = new EventController(eventManager,userManager,permissionManager, inputMap);
        this.runtimeDataHolder = new RuntimeDataHolder();
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

    public RuntimeDataHolder getRuntimeDataHolder(){return runtimeDataHolder;}
}
