package csc.zerofoureightnine.conferencemanager.main;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestManager;

import java.util.UUID;

/**
 * Used to instantiate, access, and store the model (usecases) components of this program.
 */
public class MasterModel {

    private MessageManager messageManager;
    private PermissionManager permissionManager;
    private SpecialRequestManager specialRequestManager;
    private UserManager userManager;
    private EventManager eventManager;

    /**
     * Create the Master Model
     *
     * @param userMap           {@link PersistentMap} map of users/permissions.
     * @param eventMap          {@link PersistentMap} map of events.
     * @param messageMap        {@link PersistentMap} messages.
     * @param specialRequestMap {@link PersistentMap} special requests.
     */
    public MasterModel(PersistentMap<String, UserData> userMap, PersistentMap<String, EventData> eventMap,
                       PersistentMap<UUID, MessageData> messageMap, PersistentMap<UUID, SpecialRequestData> specialRequestMap) {

        createUseCases(userMap, eventMap, messageMap, specialRequestMap);

    }

    /**
     * Create the use cases.
     *
     * @param userMap           {@link PersistentMap} map of users/permissions.
     * @param eventMap          {@link PersistentMap} map of events.
     * @param messageMap        {@link PersistentMap} messages.
     * @param specialRequestMap {@link PersistentMap} special requests.
     */
    private void createUseCases(PersistentMap<String, UserData> userMap, PersistentMap<String, EventData> eventMap, PersistentMap<UUID, MessageData> messageMap, PersistentMap<UUID, SpecialRequestData> specialRequestMap) {
        this.messageManager = new MessageManager(messageMap);
        this.permissionManager = new PermissionManager(userMap);
        this.userManager = new UserManager(userMap);
        this.eventManager = new EventManager(eventMap);
        this.userManager = new UserManager(userMap);
        this.specialRequestManager = new SpecialRequestManager(specialRequestMap);
    }

    /**
     * Get the Message manager
     *
     * @return {@link MessageManager}
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Get the Permission manager
     *
     * @return {@link PermissionManager}
     */
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    /**
     * Get the Special Request Manager
     *
     * @return {@link SpecialRequestManager}
     */
    public SpecialRequestManager getSpecialRequestManager() {
        return specialRequestManager;
    }

    /**
     * Get the User Manager
     *
     * @return {@link UserManager}
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Get the Event Manager
     *
     * @return {@link EventManager}
     */
    public EventManager getEventManager() {
        return eventManager;
    }
}
