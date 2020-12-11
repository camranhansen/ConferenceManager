package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventPresenter {

    private EventManager eventManager;
    private HashMap<String, String> inputMap;
    public EventPresenter(EventManager eventManager, HashMap<String, String> inputMap) {
        this.eventManager = eventManager;
        this.inputMap = inputMap;
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

    public String renderAllEvents(){
//        inputMap.get("username")
        List<String> eventIds = eventManager.getAllEventIds();
        return renderEventByIDList(eventIds).toString();
    }

    public String renderAvailableEventsToUser(){
        List<String> eventIds = eventManager.getAvailableEvents(inputMap.get("username"));
        return renderEventByIDList(eventIds).toString();
    }



//    private StringBuilder renderEvents
    // VIEW_ALL_EVENTS

    //VIEW_HOSTING_EVENTS
    public void viewSpeakerList(){
        System.out.println("List of csc.zerofoureightnine.conferencemanager.events that you are hosting");
    }


    public String foundConflict(){
        return("You already joined a event at the give time slot or the event you wish to enroll currently is full.");
    }

    public String wrongInput(){
        return ("Invalid Input");
    }


    public String enterId(){
        return("Please enter the event id");
    }

    public void enrollOrDrop(){
        System.out.println("Do you want to 1. enroll an event or 2. drop an event? Reply 1 or 2");
    }

    public void enterChange(){
        System.out.println("What information would you like to change 1.Time 2.Speaker name 3.Event name 4.Room 5.Capacity");
    }

    //EVENT_OTHER_ENROLL
    public String enterUsername(){
        return("Please enter the username");
    }

    //EVENT_EDIT, EVENT_CREATE
    public String enterHour(){
        return("Assuming that events last for one hour, please enter the hour that the event will start on");
    }

    public String enterDay(){
        return("Please enter a valid day of December, from 1-31");
    }

    public String enterSpeakerName(){
        return("Please enter any number of speaker names, seperated by a comma." + System.lineSeparator() + "In the format speakrname1, speakername2..." + System.lineSeparator() + "If you would like to create a party, please put a space bar, end then enter.");
    }

    public String enterEventName(){
        return("Please enter an event name");
    }

    public String enterRoom(){
        return("Please enter a room name");
    }

    public String enterCapacity(){
        return("Please enter a number designating the capacity");
    }

    public String eventCreateConfirmation(){
        return "Event created!";
    }



}
