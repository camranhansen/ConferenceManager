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
    private boolean exiting;

    /**
     * Creates a new EventController with event manager eventManager.
     * THIS SHOULD NOT BE USED EXCEPT FOR IN TESTING
     * @param eventManager Manager of the events.
     */

    public EventController(EventManager eventManager){
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.inputPrompter = new InputPrompter();
        inputPrompter.attach(this);
        this.exiting = false;

    }


    /**
     * Creates a new EventController with event manager eventManager
     * and user manager userManager.
     *
     * @param userManager Manager of the users.
     * @param eventManager Manager of the events.
     */

    public EventController(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.inputPrompter = new InputPrompter();
        this.userManager = userManager;
    }

    /**
     * Directs the permission permissionSelected selected by the user with String username to further function.
     *
     * @param username Username of the current user.
     * @param permissionSelected Permission the user would like to pursue.
     */

    public void performSelectedAction(String username, Permission permissionSelected){
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
            speakerName = getExistUserInput(speakerName);
            while (!userManager.getPermissions(speakerName).containsAll(Template.SPEAKER.getPermissions())) {
//                !userManager.getPermissions(speakerName).contains(Template.SPEAKER.getPermissions()))
                speakerName = inputPrompter.getResponse("The username you have entered is not a speaker." + System.lineSeparator() + "Please enter a new username");
                speakerName = getExistUserInput(speakerName);
            }
            while(eventManager.checkConflictSpeaker(time, speakerName)) {
                speakerName = inputPrompter.getResponse("The speaker is not available at that time." + System.lineSeparator() + "Please enter a new username");
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

            String eventIDStr = getId();
            while(!eventManager.eventExists(eventIDStr)){
                eventIDStr = getCorrectId();
            }
            editEvent(eventIDStr);
        }
        else if(permissionSelected == Permission.VIEW_ALL_EVENTS){
            viewEvents(username);
        }
        else if(permissionSelected == Permission.VIEW_HOSTING_EVENTS){
            viewHostingEvent(username);
        }

    }

    public void exitEarly(){
        this.exiting = true;
    }

    public List<String> viewHostingEvent(String name) {
        List<String> List = new ArrayList<>();
        List<String> aList = eventManager.getEventList();
        for (int i = 0; i < aList.size(); i++) {
            if (eventManager.getEventSpeakerName(aList.get(i)) == name) {
                List.add(eventManager.getFormattedEvent(aList.get(i)));
            }
        }
        return List;
    }
    //enroll methods

    /**
     * Allows the user with String username to choose between registering in or withdrawing from an event. Performs
     * selected action based on the user's selection.
     *
     * @param username Username of the current user.
     */

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

    /**
     * Registers the user with String username in the event identified by String eventID.
     *
     * @param eventID ID of the selected event.
     * @param username Username of the current user.
     */

    public void enroll(String eventID, String username){
        this.eventManager.enrollUser(eventID, username);
    }

    /**
     * Withdraws the user with String username from the event identified by String eventID.
     *
     * @param eventID ID of the selected event.
     * @param username Username of the current user.
     */

    public void drop(String eventID, String username){
        this.eventManager.dropUser(eventID, username);
    }

    /**
     * Allows the user with String username to choose whether they wish to view the events they are enrolled in,
     * the ones they can register for, or the entire selection of events.
     * Shows the user the respective list given their choice.
     *
     * @param username Username of the current user.
     */

    // View events methods
    public void viewEvents(String username) {
        Option option1 = new Option("View My Events") {
            @Override
            public void run() {
                eventPresenter.viewMyEvents();
                List<String> result = viewMyEvents(username);
                eventPresenter.renderEvents(result);
            }
        };

        Option option2 = new Option("View Events Which I Can Register For") {
            @Override
            public void run() {
                eventPresenter.viewAvailableEvents();
                List<String> result = viewAvailableEvent(username);
                eventPresenter.renderEvents(result);
            }
        };

        Option option3 = new Option("View All Events") {
            @Override
            public void run() {
                eventPresenter.viewAllEvents();
                List<String> result = viewAllEvents();
                eventPresenter.renderEvents(result);

            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }

    /**
     * Returns the list of events which the user with String username is registered for.
     *
     * @param username Username of the current user.
     */

    public List<String> viewMyEvents(String username){
        List<String>aList = eventManager.getUserEvents(username);
        List<String> result = new ArrayList<>();
        for(int i=0; i< aList.size(); i++){
            result.add(eventManager.getFormattedEvent(aList.get(i)));
        }
        return result;
    }

    /**
     * Returns the list of events which the user with String username can register in.
     *
     * @param username Username of the current user.
     */

    public List<String> viewAvailableEvent(String username){
        List<String>aList = this.eventManager.getAvailableEvents(username);
        List<String> result = new ArrayList<>();
        for(int i=0; i< aList.size(); i++){
            result.add(eventManager.getFormattedEvent(aList.get(i)));
        }
        return result;
    }

    /**
     * Returns a list of all the events.
     *
     */

    public List<String> viewAllEvents(){
        List<String> result = new ArrayList<>();
        List<String> aList = eventManager.getEventList();
        for(int i=0; i< aList.size(); i++){
            result.add(eventManager.getFormattedEvent(aList.get(i)));
        }
        return result;
    }


    /**
     * Deletes the event identified by String eventID from the collection of all events.
     *
     * @param eventID ID of the selected event.
     */

    public void deleteEvent(String eventID){
        this.eventManager.deleteEvent(eventID);
    }

    /**
     * Defines a new event with the details String speakerName, Instant time, String eventName, String room,
     * and int capacity.
     * Adds this event to the collection of all events.
     *
     * @param speakerName Name of the speaker for the event.
     * @param time Time that the event takes place.
     * @param eventName Name of the event.
     * @param room Room which the event is being held in.
     * @param capacity Maximum capacity of the event.
     */

    public void createEvent(String speakerName, Instant time, String eventName, String room, int capacity){
        this.eventManager.createEvent(speakerName, time, eventName, room, capacity);
    }

    /**
     * Presents the options to modify either the speaker, the event name, the room, the capacity, or the time
     * for the event identified by String eventID.
     * Performs selected action.
     *
     * @param eventID ID of the selected event.
     */

    public void editEvent(String eventID) {
        Option option1 = new Option("Change Speaker") {
            @Override
            public void run() {
                String speakerName = inputPrompter.getResponse("Enter the new speaker's name");
                while (!userManager.uNameExists(speakerName) &&
                        !userManager.getPermissions(speakerName).containsAll(Template.SPEAKER.getPermissions())) {
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

    /**
     * Prompts the user to enter the ID of the event which they wish to perform actions with.
     * Returns the input.
     *
     */

    private String getId(){
        return inputPrompter.getResponse("Enter Event ID");
    }

    /**
     * Prompts the user to enter the correct ID of the event which they wish to perform actions with, as they have
     * entered an invalid response.
     * Returns the input.
     *
     */

    private String getCorrectId(){
        return inputPrompter.getResponse("Event does not exist, please enter the correct EventId");
    }

    /**
     * Prompts the user to enter their correct username, as they have entered a non-existent username.
     * Returns the input.
     *
     */

    private String getCorrectName(){
        return inputPrompter.getResponse("User does not exist, please enter the correct Username");
    }

    /**
     * Prompts the user to enter the new name of the event.
     * Returns the input.
     *
     */

    private String getEventNameInput(){
        String name = inputPrompter.getResponse("Enter the new event's name");
        return name;
    }

    /**
     * Prompts the user to enter the new room of the event.
     * Returns the input.
     *
     */

    private String getRoomInput(){
        String room = inputPrompter.getResponse("Enter the new event's room");
        return room;
    }

    /**
     * Prompts the user to enter the new capacity of the event.
     * Returns the input.
     *
     */

    private int getCapacityInput(){
        String capacity = inputPrompter.getResponse("Enter the new event's capacity");
        return Integer.parseInt(capacity.trim());
    }

    /**
     * Prompts the user to enter the new date and time slot of the event.
     * Prompts the user to enter a valid input if an invalid response is received at any point during this inquiry.
     * Returns the input as an Instant.
     *
     */

    private Instant getTimeInput(){
        String date = inputPrompter.getResponse("Enter the day of the month");
        while(!date.trim().matches("[0-2][0-9]|[3][0-1]|[1-9]")){
            date = inputPrompter.getResponse("The date you entered is invalid."+System.lineSeparator()+
                    "Please enter a new day of the month");
        }
        if(date.length() == 1){
            date = "0"+date;
        }
        String time = inputPrompter.getResponse("Chose the time slot from 9 to 16 on a 24-hour clock");
        while (!time.trim().matches("[9]|[1][0-6]")){
            time = inputPrompter.getResponse("The time slot you chose is invalid."+System.lineSeparator()+
                    "Please enter a new time slot");
        }
        if(time.length() == 1) {
            time = "0" + time;
        }
        return Instant.parse("2020-12-" + date.trim() + "T" + time.trim() + ":00:00.00Z");
    }

    private String getExistUserInput(String name){
        while(!userManager.uNameExists(name)){
            name = inputPrompter.getResponse("The username you have entered does not exist." + System.lineSeparator() + "Please enter a new username");
        }
        return name;
    }
}