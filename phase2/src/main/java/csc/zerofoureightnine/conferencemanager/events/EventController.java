package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.menu.SubController;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.time.Instant;
import java.util.*;


public class EventController implements SubController {
    private EventManager eventManager;
    private EventPresenter eventPresenter;
    private UserManager userManager;

    /**
     * Creates a new EventController with event manager eventManager and user
     * manager userManager.
     *
     * @param userManager  Manager of the
     *                     csc.zerofoureightnine.conferencemanager.users.
     * @param eventManager Manager of the
     *                     csc.zerofoureightnine.conferencemanager.events.
     */

    public EventController(EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.userManager = userManager;
    }

    /**
     * Executes the method associated with the selected permission for the given user with the assumed relevant inputHistory
     *
     * @param username           Username of the current user.
     * @param permissionSelected Permission the user would like to pursue.
     * @param inputHistory The input history
     */
    @Override
    public void performSelectedAction(String username, Permission permissionSelected, Map<InputStrategy, String> inputHistory) {
        switch(permissionSelected) {
            case EVENT_SELF_ENROLL:
                enrollUser(username, inputHistory);
                break;
            case EVENT_SELF_DROP:
                drop(inputHistory.get(InputStrategy.VALID_EVENT_ID),username);
                break;
            case EVENT_OTHER_ENROLL:
                enrollOtherUser(inputHistory);
                break;
            case EVENT_CREATE:
                createEvent(inputHistory);
                break;
            case EVENT_DELETE:
                deleteEvent(inputHistory);
                break;
            case EVENT_EDIT:
                //TODO later... but only if editing an event is part of the specifications.
                break;
            case VIEW_HOSTING_EVENTS:
                viewHostingEvent(username);
                break;
            case VIEW_ATTENDING_EVENTS:
                viewMyEvents(username);
                break;
            case VIEW_AVAILABLE_EVENTS:
                viewAvailableEvents(username);
                break;
            case VIEW_ALL_EVENTS:
                viewAllEvents();
                break;

        }
            
    }

    /**
     * Registers the inputted user into the event specified in InputHistory.
     * @param inputHistory the input history to be parsed.
     */
    private void enrollOtherUser(Map<InputStrategy, String> inputHistory){
        eventManager.enrollUser(inputHistory.get(InputStrategy.VALID_EVENT_ID), inputHistory.get(InputStrategy.VALID_USERNAME));
    }

    /**
     * Registers the current user in the event specified in inputHistory
     *
     * @param inputHistory the input history to be parsed.
     * @param username Username of the current user.
     */
    private void enrollUser(String username, Map<InputStrategy, String> inputHistory){
        eventManager.enrollUser(inputHistory.get(InputStrategy.VALID_EVENT_ID), username);
    }

    /**
     * View events that the user who is logged in is a speaker at
     * @param username the username identifying the user who is logged in.
     */
    public void viewHostingEvent(String username) {
        List<String> hostingEvents= eventManager.getHostingEvents(username);
        renderEventIDList(hostingEvents);
    }

    /**
     * Withdraws the user with String username from the event identified by String
     * eventID.
     *
     * @param eventID  ID of the selected event.
     * @param username Username of the current user.
     */

    public void drop(String eventID, String username) {
        this.eventManager.dropUser(eventID, username);
    }

    /**
     * Returns the list of csc.zerofoureightnine.conferencemanager.events which the
     * user with String username is registered for.
     *
     * @param username Username of the current user.
     */

    public void viewMyEvents(String username) {
        List<String> eventIDList = eventManager.getUserEvents(username);
        renderEventIDList(eventIDList);
    }

    /**
     * Private helper method that gets event data from the eventManager, and then
     * calls the given EventPresenter to render it.
     * @param eventIDList the list of event IDs to render.
     */
    private void renderEventIDList(List<String> eventIDList) {
        List<Map<String, String>> eventData = new ArrayList<>();
        for (String EventID : eventIDList) {
            eventData.add(eventManager.getFormattedEvent(EventID));
        }
        eventPresenter.renderEvents(eventData);
    }

    /**
     * Render a list of events available to the given username.
     * @param username Username of the current user.
     */
    public void viewAvailableEvents(String username) {
        renderEventIDList(this.eventManager.getAvailableEvents(username));
    }

    /**
     * Render all possible events
     */
    public void viewAllEvents() {
        renderEventIDList(eventManager.getAllEventIds());
    }

    /**
     * Removes the event identified by String eventID from data storage
     * @param inputHistory the input history to be parsed
     */
    public void deleteEvent(Map<InputStrategy, String> inputHistory) {
        this.eventManager.deleteEvent(inputHistory.get(InputStrategy.VALID_EVENT_ID));
    }
    /**
     * Creates a new event by parsing inputHistory for String speakerName, Instant time, String
     * eventName, String room, and int capacity. Stores this newly created event.
     *
     * @param inputHistory the input history to be parsed.
     */
    public void createEvent(Map<InputStrategy, String> inputHistory) {
        // TODO implement changes once allows for multiple users in event creation

        this.eventManager.createEvent(
                Arrays.asList(inputHistory.get(InputStrategy.EVENT_SPEAKER_SINGLE).split(", ")),
                parseTimeFromInputHistory(inputHistory),
                inputHistory.get(InputStrategy.MESSAGE_CONTENT),//TODO create VALID_EVENT_NAME enum in
                //InputStrategy.
                inputHistory.get(InputStrategy.EVENT_ROOM),
                Integer.parseInt(inputHistory.get(InputStrategy.EVENT_CAPACITY)));

    }

    /**
     * Presents the options to modify either the speaker, the event name, the room,
     * the capacity, or the time for the event identified by String eventID.
     * Performs selected action.
     *
     * @param eventID ID of the selected event.
     */

    public void editEvent(String eventID) {
        //TODO implement this method.
    }


    /**
     * Construct an Instant representing the time during December that the user has inputted
     * @param inputHistory the input history to parse
     * @return the Instant time constructed
     */
    private Instant parseTimeFromInputHistory(Map<InputStrategy, String> inputHistory){
        return eventManager.parseTime(
                inputHistory.get(InputStrategy.EVENT_DAY),
                inputHistory.get(InputStrategy.EVENT_HOUR));
    }


}