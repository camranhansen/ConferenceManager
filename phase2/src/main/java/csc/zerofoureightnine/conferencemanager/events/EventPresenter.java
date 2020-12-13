package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.List;

public class EventPresenter {

    private EventManager eventManager;
    public EventPresenter(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Displays all the specified events.
     * @param eventIds A list of event IDs.
     * @return A string representation of all the specified events.
     */
    private StringBuilder renderEventByIDList(List<String> eventIds) {
        StringBuilder renderableEvents = new StringBuilder();
        for (String eventID: eventIds){
            renderableEvents.append("************************************************").append(System.lineSeparator());
            eventManager.getEventData(eventID).forEach((category, dataPoint) ->
                    renderableEvents.append(category).append(": ").append(dataPoint).append(".").append(System.lineSeparator()));
        }
        renderableEvents.append("Press enter once finished!");
        return renderableEvents;
    }

    /**
     * Displays all the events.
     * @param username The username of the user.
     * @return A string representation of all the events.
     */
    public String renderAllEvents(String username){
//        inputMap.get("username")
        List<String> eventIds = eventManager.getAllEventIds();
        return renderEventByIDList(eventIds).toString();
    }

    /**
     * Displays all the events for which a user can register in.
     * @param username The username of the user.
     * @return A string representation of events for which the user can register in.
     */
    public String renderAvailableEventsToUser(String username){
        List<String> eventIds = eventManager.getAvailableEvents(username);
        return renderEventByIDList(eventIds).toString();
    }

    /**
     * Displays all the events for which a user is registered for.
     * @param username The username of the user.
     * @return A string representation of events for which the user is registered for.
     */
    public String renderAttendingEventsToUser(String username){
        List<String> eventIds = eventManager.getUserEvents(username);
        return renderEventByIDList(eventIds).toString();
    }

    /**
     * Displays all the events for which a user is hosting.
     * @param username The username of the user.
     * @return A string representation of events for which the user is hosting.
     */
    public String renderHostingEventsToUser(String username){
        List<String> eventIds = eventManager.getHostingEvents(username);
        return renderEventByIDList(eventIds).toString();
    }

    /**
     * Indicates that the user cannot register in an event.
     * @param username The username of the user.
     * @return A string explaining the error.
     */
    public String foundConflict(String username){
        return("You already joined a event at the give time slot or the event you wish to enroll currently is full.");
    }

    /**
     * Indicates that an invalid input has been received.
     * @return A string explaining the error.
     */
    public String wrongInput(){
        return ("Invalid Input");
    }

    /**
     * Indicates that the event ID for the event to be interacted with should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterId(String username){
        return("Please enter the event ID");
    }

    //EVENT_OTHER_ENROLL
    /**
     * Indicates that the username of the user to perform the actions for should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterUsername(String username){
        return("Please enter the username");
    }

    //EVENT_EDIT, EVENT_CREATE
    /**
     * Indicates that the hour which the event will start at should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterHour(String username){
        return("Assuming that events last for one hour, please enter the hour that the event will start on");
    }

    /**
     * Indicates that a valid date in December should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterDay(String username){
        return("Please enter a valid day of December, from 1-31");
    }

    /**
     * Indicates that the names of the speakers for the event should be entered.
     * Also provides instuctions for creating a party (non-speaker event).
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterSpeakerName(String username) {
        return("Please enter any number of speaker names, seperated by a comma." +
                System.lineSeparator() + "In the format speakrname1, speakername2..." + System.lineSeparator() +
                "If you would like to create a party, please just put enter, end then enter.");
    }

    /**
     * Indicates that the name of the event should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterEventName(String username) {
        return("Please enter an event name");
    }

    /**
     * Indicates that the name of the room for the event should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterRoom(String username){
        return("Please enter a room name");
    }

    /**
     * Indicates that the amount denoting the capacity of the event should be entered.
     * @param username The username of the user.
     * @return A string indicating that input should be received.
     */
    public String enterCapacity(String username) {
        return("Please enter a number designating the capacity");
    }

    /**
     * Indicates that the event has been created successfully.
     * @param username The username of the user.
     * @param p An identifier for the presenter.
     * @return A string indicating that the event has been created successfully.
     */
    public String eventCreateConfirmation(String username, TopicPresentable p) {
        return "Event created!";
    }



}
