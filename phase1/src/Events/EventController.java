package Events;

import Menus.InputPrompter;
import Menus.Option;
import Menus.SubController;
import Users.Permission;
import Users.Template;
import Users.UserManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EventController implements SubController {
    private EventManager eventManager;
    private EventPresenter eventPresenter;
    private InputPrompter inputPrompter;
    private UserManager userManager;

    public EventController(EventManager eventManager) {
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.inputPrompter = new InputPrompter();
    }

    public EventController(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.inputPrompter = new InputPrompter();
        this.userManager = userManager;
    }
    public void addEvent(String speakerName, Instant time, String eventName, String room, int capacity){
        this.eventManager.createEvent(speakerName, time, eventName, room, capacity);
    }

    public List<String> viewAllEvents(){
        return eventManager.getEventList();
    }

    public List<String> checkMyEvents(String userName){
        //TODO: Validate userName.
        return eventManager.getUserEvents(userName);
    }

    public void enroll(String eventID, String username){
        this.eventManager.enrollUser(eventID, username);
    }

    public void drop(String eventID, String username){
        this.eventManager.dropUser(eventID, username);
    }

    public List<String> viewAvailableEvent(String username){
        return this.eventManager.getAvailableEvents(username);
    }

    public void createEvent(String name, Instant time, String eventName, List<String> participants, String room, int capacity){
        this.eventManager.createEvent(name, time, eventName, room, capacity);
    }

    public void enrollSelf(String eventID, String username) {

        Option option1 = new Option("Enroll In Event") {
            @Override
            public void run() {
                enroll(eventID, username);
            }
        };

        Option option2 = new Option("Withdraw From Event") {
            @Override
            public void run() {
                drop(eventID, username);
            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }

    public void deleteEvent(String eventID){
        this.eventManager.deleteEvent(eventID);
    }

    //TODO: Change time from string to instant
//    public void editTime(int eventID){
//        this.eventPresenter.enterTime();
//        Instant time = this.getTimeInput();
//        this.eventManager.editTime(eventID, time);
//    }

    public void editEvent(String eventID) {
        Option option1 = new Option("Change Speaker") {
            @Override
            public void run() {
                String speakerName = inputPrompter.getResponse("Enter the new speaker's name");

                while(!userManager.uNameExists(speakerName)&&
                        !userManager.getPermissions(speakerName).contains(Template.SPEAKER)){
                    speakerName = inputPrompter.getResponse("The username you have entered is not a speaker."+System.lineSeparator()+"Please enter a new username");
                }

                eventManager.editSpeakerName(eventID, speakerName);
            }
        };

        Option option2 = new Option("Change Event Name") {
            @Override
            public void run() {
                String eventName = inputPrompter.getResponse("Enter the new event name");
                eventManager.editEventName(eventID, eventName);
            }
        };

        Option option3 = new Option("Change Event Room") {
            @Override
            public void run() {
                String eventRoom = inputPrompter.getResponse("Enter the new room name");
                eventManager.editRoom(eventID, eventRoom);
            }
        };

        Option option4 = new Option("Change Event Capacity") {
            @Override
            public void run() {
                String eventCapacityStr = inputPrompter.getResponse("Enter the new capacity");
                int eventCapacity = Integer.parseInt(eventCapacityStr.trim());
                eventManager.editCapacity(eventID, eventCapacity);
            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }

    public void viewEvents(String username) {
        Option option1 = new Option("View My Events") {
            @Override
            public void run() {
                eventPresenter.viewMyEvents();
                List<String> result = checkMyEvents(username);
                for (String event : result) {
                    System.out.println(event);
                }
            }
        };

        Option option2 = new Option("View Events Which I Can Register For") {
            @Override
            public void run() {
                eventPresenter.viewAvailableEvents();
                List<String> result = viewAvailableEvent(username);
                for (String event : result) {
                    System.out.println(event);
                }
            }
        };

        Option option3 = new Option("View All Events") {
            @Override
            public void run() {
                eventPresenter.viewAllEvents();
                List<String> result = viewAllEvents();
                for (String event : result) {
                    System.out.println(event);
                }
            }
        };

        ArrayList<Option> optionList = new ArrayList<>();
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);

        Option choice = inputPrompter.menuOption(optionList);
        choice.run();
    }



    public void performSelectedAction(String username, Permission permissionSelected){
        String eventIDStr = inputPrompter.getResponse("Enter the ID of the event you wish to interact with");

        if (permissionSelected== Permission.EVENT_SELF_ENROLL){
            enrollSelf(eventIDStr, username);
        }
        else if(permissionSelected == Permission.EVENT_OTHER_ENROLL){
            String name = inputPrompter.getResponse("Enter the username of the user you would like to enroll or withdraw");
            enrollSelf(eventIDStr, name);
        }
        //TODO: Change time from string to instant
//        else if(permissionSelected == Permission.EVENT_CREATE){
//            String speakerName = getSpeakerNameInput();
//            Instant time = getTimeInput()
//            String eventName = getEventNameInput();
//            List<String> participants = new ArrayList<>();
//            String roomName = getRoomInput();
//            int capacity = getCapacityInput();
//            addEvent(speakerName, time, eventName, participants, roomName, capacity);
//        }
        else if(permissionSelected == Permission.EVENT_DELETE){
            deleteEvent(eventIDStr);
        }
        else if(permissionSelected == Permission.EVENT_EDIT){
            editEvent(eventIDStr);
        }
        else if(permissionSelected == Permission.VIEW_HOSTING_EVENTS){
            eventPresenter.viewSpeakerList();
            List<String> result = eventManager.getSpkEvents(username);
            for (String event : result) {
                System.out.println(event);
            }
        }
        else if(permissionSelected == Permission.VIEW_ALL_EVENTS){
            viewEvents(username);
        }

    }

}