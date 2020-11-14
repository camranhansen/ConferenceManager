package Events;

import Menus.InputPrompter;
import Menus.Option;
import Menus.SubController;
import Users.Permission;
import Users.Template;
import Users.UserManager;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class EventController implements SubController {
    private EventManager eventManager;
    private EventPresenter eventPresenter;
    private InputPrompter inputPrompter;
    private UserManager userManager;

    public EventController(EventManager eventManager){
        this.eventManager = eventManager;
    }

    public EventController(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.inputPrompter = new InputPrompter();
        this.userManager = userManager;
    }

    public void performSelectedAction(String username, Permission permissionSelected){
        String eventIDStr = inputPrompter.getResponse("Enter the ID of the event you wish to interact with");

        if (permissionSelected== Permission.EVENT_SELF_ENROLL){
            enrollSelf(username);
        }
        else if(permissionSelected == Permission.EVENT_OTHER_ENROLL){
            String name = inputPrompter.getResponse("Enter the username of the user you would like to enroll or withdraw");
            while(!userManager.uNameExists(name)){
                name = getCorrectName();
            }
            enrollSelf(name);
        }
        else if(permissionSelected == Permission.EVENT_CREATE){
            Instant time = getTimeInput();
            String speakerName = inputPrompter.getResponse("Enter the new speaker's name");
            while (!userManager.uNameExists(speakerName) &&
                    !userManager.getPermissions(speakerName).contains(Template.SPEAKER)) {
                speakerName = inputPrompter.getResponse("The username you have entered is not a speaker." + System.lineSeparator() + "Please enter a new username");
                while(eventManager.checkConflictSpeaker(time, speakerName)) {
                    speakerName = inputPrompter.getResponse("The speaker is not available at that time." + System.lineSeparator() + "Please enter a new username");
                }
            }
            String eventName = getEventNameInput();
            String room = getRoomInput();
            while(eventManager.checkRoom(time, room)){
                room = inputPrompter.getResponse("The room is not available at that time."+System.lineSeparator()+"Please enter a new room");
            }
            int capacity = getCapacityInput();
            createEvent(speakerName, time, eventName, room, capacity);
        }
        else if(permissionSelected == Permission.EVENT_DELETE){
            String eventId = getId();
            while(!eventManager.eventExists(eventId)){
                eventId = getCorrectId();
            }
            deleteEvent(eventId);
        }
        else if(permissionSelected == Permission.EVENT_EDIT){
            eventIDStr = getId();
            while(!eventManager.eventExists(eventIDStr)){
                eventIDStr = getCorrectId();
            }
            editEvent(eventIDStr);
        }
        else if(permissionSelected == Permission.VIEW_ALL_EVENTS){
            viewEvents(username);
        }

    }

    //enroll methods
    public void enrollSelf(String username) {

        Option option1 = new Option("Enroll In Event") {
            @Override
            public void run() {
                String eventId = getId();
                while(!eventManager.eventExists(eventId)){
                    eventId = getCorrectId();
                    while(eventManager.checkUserInEvent(eventManager.getEventTime(eventId), username) ||
                    eventManager.isEventFull(eventId)){
                        eventPresenter.foundConflict();
                        eventId = getId();
                    }
                }
                enroll(eventId, username);
            }
        };

        Option option2 = new Option("Withdraw From Event") {
            @Override
            public void run() {
                String eventId = getId();
                while(!eventManager.eventExists(eventId)){
                    eventId = getCorrectId();
                }
                drop(eventId, username);
            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }
    public void enroll(String eventID, String username){
        this.eventManager.enrollUser(eventID, username);
    }

    public void drop(String eventID, String username){
        this.eventManager.dropUser(eventID, username);
    }

    // View events methods
    public void viewEvents(String username) {
        Option option1 = new Option("View My Events") {
            @Override
            public void run() {
                eventPresenter.viewMyEvents();
                List<String> result = viewMyEvents(username);
                System.out.println(result);
            }
        };

        Option option2 = new Option("View Events Which I Can Register For") {
            @Override
            public void run() {
                eventPresenter.viewAvailableEvents();
                List<String> result = viewAvailableEvent(username);
                System.out.println(result);
            }
        };

        Option option3 = new Option("View All Events") {
            @Override
            public void run() {
                eventPresenter.viewAllEvents();
                List<String> result = viewAllEvents();
                System.out.println(result);
            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }
    public List<String> viewMyEvents(String userName){
        List<String>aList = eventManager.getUserEvents(userName);
        List<String> result = new ArrayList<>();
        for(int i=0; i< aList.size(); i++){
            result.add(eventManager.getFormattedEvent(aList.get(i)));
        }
        return result;
    }

    public List<String> viewAvailableEvent(String username){
        List<String>aList = this.eventManager.getAvailableEvents(username);
        List<String> result = new ArrayList<>();
        for(int i=0; i< aList.size(); i++){
            result.add(eventManager.getFormattedEvent(aList.get(i)));
        }
        return result;
    }

    public List<String> viewAllEvents(){
        List<String> result = new ArrayList<>();
        List<String> aList = eventManager.getEventList();
        for(int i=0; i< aList.size(); i++){
            result.add(eventManager.getFormattedEvent(aList.get(i)));
        }
        return result;
    }

// Edit event methods
    //TODO: Change time from string to instant
//    public void editTime(int eventID){
//        this.eventPresenter.enterTime();
//        Instant time = this.getTimeInput();
//        this.eventManager.editTime(eventID, time);
//    }

    //create/delete event
    public void deleteEvent(String eventId){
        this.eventManager.deleteEvent(eventId);
    }
    public void createEvent(String speakerName, Instant time, String eventName, String room, int capacity){
        this.eventManager.createEvent(speakerName, time, eventName, room, capacity);
    }

    public void editEvent(String eventID) {
        Option option1 = new Option("Change Speaker") {
            @Override
            public void run() {
                String speakerName = inputPrompter.getResponse("Enter the new speaker's name");
                while (!userManager.uNameExists(speakerName) &&
                        !userManager.getPermissions(speakerName).contains(Template.SPEAKER)) {
                    speakerName = inputPrompter.getResponse("The username you have entered is not a speaker." + System.lineSeparator() + "Please enter a new username");
                    while(eventManager.checkConflictSpeaker(eventManager.getEventTime(eventID), speakerName)) {
                        speakerName = inputPrompter.getResponse("The speaker is not available at that time." + System.lineSeparator() + "Please enter a new username");
                    }
                }
                eventManager.editSpeakerName(eventID, speakerName);
            }
        };

        Option option2 = new Option("Change Event Name") {
            @Override
            public void run() {
                String eventName = getEventNameInput();
                eventManager.editEventName(eventID, eventName);
            }
        };

        Option option3 = new Option("Change Event Room") {
            @Override
            public void run() {
                String eventRoom = getRoomInput();
                while(eventManager.checkRoom(eventManager.getEventTime(eventID), eventRoom)){
                    eventRoom = inputPrompter.getResponse("The room is not available at that time."+System.lineSeparator()+"Please enter a new room");
                }
                eventManager.editRoom(eventID, eventRoom);
            }
        };

        Option option4 = new Option("Change Event Capacity") {
            //Assuming we can only change the capacity larger
            @Override
            public void run() {
                String eventCapacityStr = inputPrompter.getResponse("Enter the new capacity");
                while(Integer.parseInt(eventCapacityStr.trim()) < eventManager.getCapacity(eventID)){
                    eventCapacityStr = inputPrompter.getResponse("The changed capacity must be larger than the default capacity."+System.lineSeparator()+"Please enter a new capacity");
                }
                int eventCapacity = Integer.parseInt(eventCapacityStr.trim());
                eventManager.editCapacity(eventID, eventCapacity);
            }
        };

        Option option5 = new Option("Change Event time"){
            public void run(){
                Instant time = getTimeInput();
                while (eventManager.checkConflictSpeaker(time, eventManager.getEventSpeakerName(eventID)) ||
                        eventManager.checkRoom(time, eventManager.getEventSpeakerName(eventID))){
                            eventPresenter.wrongInput();
                            time = getTimeInput();
                }
                eventManager.editTime(eventID, time);
            }
        };
        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);
        optionList.add(option5);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }

//get information from user

    private String getId(){
        return inputPrompter.getResponse("Enter EventId");
    }

    private String getCorrectId(){
        return inputPrompter.getResponse("Event does not exist, please enter the correct EventId");
    }

    private String getCorrectName(){
        return inputPrompter.getResponse("User does not exist, please enter the correct Username");
    }


    private String getEventNameInput(){
        String name = inputPrompter.getResponse("Enter the new event's name");
        return name;
    }

    private String getRoomInput(){
        String room = inputPrompter.getResponse("Enter the new event's room");
        return room;
    }

    private int getCapacityInput(){
        String capacity = inputPrompter.getResponse("Enter the new event's capacity");
        return Integer.parseInt(capacity.trim());
    }

    private Instant getTimeInput(){
        String date = inputPrompter.getResponse("Enter the date");
        while(Integer.parseInt(date.trim())< Instant.now().get(ChronoField.DAY_OF_MONTH)){
            date = inputPrompter.getResponse("The date you entered is invalid."+System.lineSeparator()+"Please enter a new date");
        }
        String time = inputPrompter.getResponse("Chose the time slot from 9 am to 16 pm");
        while (Integer.parseInt(time.trim())<9 || Integer.parseInt(time.trim())>16){
            time = inputPrompter.getResponse("The time slot you chose is invalid."+System.lineSeparator()+"Please enter a new time slot");
        }
        return Instant.parse("2020-12-" + date + "T" + time + ":00:00.00Z");
    }


}