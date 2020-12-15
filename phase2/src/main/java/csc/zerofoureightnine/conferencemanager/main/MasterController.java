package csc.zerofoureightnine.conferencemanager.main;

import csc.zerofoureightnine.conferencemanager.datacollection.DataActions;
import csc.zerofoureightnine.conferencemanager.datacollection.DataPresenter;
import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeDataHolder;
import csc.zerofoureightnine.conferencemanager.datacollection.StoredDataGetter;
import csc.zerofoureightnine.conferencemanager.events.EventActionHolder;
import csc.zerofoureightnine.conferencemanager.events.EventInputValidator;
import csc.zerofoureightnine.conferencemanager.events.EventPresenter;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.messaging.MessageActions;
import csc.zerofoureightnine.conferencemanager.messaging.MessageInputValidator;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.users.UserActions;
import csc.zerofoureightnine.conferencemanager.users.UserInputValidator;
import csc.zerofoureightnine.conferencemanager.users.UserPresenter;
import csc.zerofoureightnine.conferencemanager.users.session.SessionController;
import csc.zerofoureightnine.conferencemanager.users.session.SessionPresenter;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestActions;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestInputValidator;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestPresenter;

import java.util.UUID;

/**
 * Master controller.
 */
public class MasterController {


    private MasterModel model;
    private RuntimeDataHolder runtimeDataHolder;
    private StoredDataGetter storedDataGetter;
    private DataPresenter dataPresenter;
    private DataActions dataActions;
    private EventPresenter eventPresenter;
    private UserPresenter userPresenter;
    private SessionPresenter sessionPresenter;
    private MessagePresenter messagePresenter;
    private SpecialRequestPresenter specialRequestPresenter;

    private UserInputValidator userInputValidator;
    private SpecialRequestInputValidator specialRequestInputValidator;
    private EventInputValidator eventInputValidator;
    private MessageInputValidator messageInputValidator;


    private SessionController sessionController;
    private UserActions userActions;
    private MessageActions messageActions;
    private EventActionHolder eventActionHolder;
    private SpecialRequestActions specialRequestActions;

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
        this.model = new MasterModel(userMap, eventMap, messageMap, specialRequestMap);
        createDataCollectors();
        createActionHolders();
        createInputValidators();
        createPresenters();
    }

    /**
     * Create the action holders for the Section Menus. See {@link csc.zerofoureightnine.conferencemanager.interaction.control.Action}
     */
    private void createActionHolders() {
        this.dataActions = new DataActions();
        this.sessionController = new SessionController(model.getUserManager(), model.getPermissionManager());
        this.messageActions = new MessageActions(model.getMessageManager(), model.getUserManager(), model.getEventManager(), model.getPermissionManager());
        this.eventActionHolder = new EventActionHolder(model.getEventManager(), model.getUserManager(), model.getPermissionManager());
        this.specialRequestActions = new SpecialRequestActions(model.getSpecialRequestManager());
        this.userActions = new UserActions(model.getUserManager(), model.getPermissionManager());
    }

    /**
     * Create the input validators for the Section Menus. See {@link csc.zerofoureightnine.conferencemanager.interaction.control.Validatable}
     */
    private void createInputValidators() {
        this.userInputValidator = new UserInputValidator(model.getUserManager());
        this.specialRequestInputValidator = new SpecialRequestInputValidator(model.getSpecialRequestManager());
        this.eventInputValidator = new EventInputValidator(model.getEventManager(), model.getUserManager(), model.getPermissionManager(), eventActionHolder.getInputMap());
        this.messageInputValidator = new MessageInputValidator(model.getMessageManager(), messageActions.getInputMap(), model.getUserManager(), model.getPermissionManager(), model.getEventManager(), messageActions.getSelectedMessageIDs());

    }

    /**
     * Create the presenters for the Section Menus.
     */
    private void createPresenters() {
        this.eventPresenter = new EventPresenter(model.getEventManager());
        this.sessionPresenter = new SessionPresenter();
        this.messagePresenter = new MessagePresenter(model.getMessageManager(), messageActions.getInputMap());
        this.specialRequestPresenter = new SpecialRequestPresenter(model.getSpecialRequestManager(), specialRequestActions.getInputMap());
        this.userPresenter = new UserPresenter();
        this.dataPresenter = new DataPresenter(runtimeDataHolder, storedDataGetter);
    }

    /**
     * Create the data collectors, {@link RuntimeDataHolder}, and {@link StoredDataGetter}
     */
    private void createDataCollectors() {
        this.runtimeDataHolder = new RuntimeDataHolder();
        this.storedDataGetter = new StoredDataGetter(model.getMessageManager(), model.getEventManager(), model.getSpecialRequestManager(), model.getUserManager());
    }



    /*
    NOTE:
    WE HAVE NOT DONE JAVADOC FOR THE METHODS BELOW,
    BECAUSE THEY ARE GETTERS AND SETTERS,
    AND INHERENTLY EXCEPTIONALLY SELF-DESCRIPTIVE.
     */

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

    public MessageActions getMessageActions() {
        return messageActions;
    }

    public EventActionHolder getEventActionHolder() {
        return eventActionHolder;
    }

    public RuntimeDataHolder getRuntimeDataHolder() {
        return runtimeDataHolder;
    }

    public SpecialRequestPresenter getSpecialRequestPresenter() {
        return specialRequestPresenter;
    }

    public SpecialRequestActions getSpecialRequestActions() {
        return specialRequestActions;
    }

    public SpecialRequestInputValidator getSpecialRequestInputValidator() {
        return specialRequestInputValidator;
    }

    public UserInputValidator getUserInputValidator() {
        return userInputValidator;
    }

    public EventInputValidator getEventInputValidator() {
        return eventInputValidator;
    }

    public UserActions getUserActions() {
        return userActions;
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

    public DataActions getDataController() {
        return dataActions;
    }

    public MessageInputValidator getMessageInputValidator() {
        return messageInputValidator;
    }


}
