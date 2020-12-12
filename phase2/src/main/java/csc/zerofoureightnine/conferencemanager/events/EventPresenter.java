package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventPresenter {

    private EventManager eventManager;
    public EventPresenter(EventManager eventManager) {
        this.eventManager = eventManager;
    }

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

    public String renderAllEvents(String username){
//        inputMap.get("username")
        List<String> eventIds = eventManager.getAllEventIds();
        return renderEventByIDList(eventIds).toString();
    }

    public String renderAvailableEventsToUser(String username){
        List<String> eventIds = eventManager.getAvailableEvents(username);
        return renderEventByIDList(eventIds).toString();
    }

    public String renderAttendingEventsToUser(String username){
        List<String> eventIds = eventManager.getUserEvents(username);
        return renderEventByIDList(eventIds).toString();
    }

    public String renderHostingEventsToUser(String username){
        List<String> eventIds = eventManager.getHostingEvents(username);
        return renderEventByIDList(eventIds).toString();
    }


//    private StringBuilder renderEvents
    // VIEW_ALL_EVENTS

    //VIEW_HOSTING_EVENTS
    public void viewSpeakerList(String username){
        System.out.println("List of csc.zerofoureightnine.conferencemanager.events that you are hosting");
    }


    public String foundConflict(String username){
        return("You already joined a event at the give time slot or the event you wish to enroll currently is full.");
    }

    public String wrongInput(){
        return ("Invalid Input");
    }


    public String enterId(String username){
        return("Please enter the event id");
    }

    public void enrollOrDrop(String username) {
        System.out.println("Do you want to 1. enroll an event or 2. drop an event? Reply 1 or 2");
    }

    public void enterChange(String username) {
        System.out.println("What information would you like to change 1.Time 2.Speaker name 3.Event name 4.Room 5.Capacity");
    }

    //EVENT_OTHER_ENROLL
    public String enterUsername(String username){
        return("Please enter the username");
    }

    //EVENT_EDIT, EVENT_CREATE
    public String enterHour(String username){
        return("Assuming that events last for one hour, please enter the hour that the event will start on");
    }

    public String enterDay(String username){
        return("Please enter a valid day of December, from 1-31");
    }

    public String enterSpeakerName(String username) {
        return("Please enter any number of speaker names, seperated by a comma." + System.lineSeparator() + "In the format speakrname1, speakername2..." + System.lineSeparator() + "If you would like to create a party, please put a space bar, end then enter.");
    }

    public String enterEventName(String username) {
        return("Please enter an event name");
    }

    public String enterRoom(String username){
        return("Please enter a room name");
    }

    public String enterCapacity(String username) {
        return("Please enter a number designating the capacity");
    }

    public String eventCreateConfirmation(String username, TopicPresentable p) {
        return "Event created!";
    }



}
