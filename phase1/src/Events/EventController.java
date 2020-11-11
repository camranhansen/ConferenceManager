package Events;

//import Messaging.MessagePresenter;
//import Users.User;
//import Users.UserManager;

import Menus.SubController;
import Users.Permission;
//import Users.Template;

import java.time.Instant;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class EventController implements SubController {
    private EventManager eventManager;
    private EventPresenter eventPresenter;
    private Scanner eventScanner;

    public EventController(EventManager eventManager) {
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
        this.eventScanner = new Scanner(System.in);
    }

    public List<Event> viewAllEvents(){
        return eventManager.getEventsList();
    }

    public List<Event> checkMyEvents(String userName){
        //TODO: Validate userName.
        return eventManager.getUserEvents(userName);
    }

    public void enroll(int eventId, String username){
        this.eventManager.enrollUser(eventId, username);
    }

    public void drop(int eventId, String username){
        this.eventManager.dropUser(eventId, username);
    }

    public List<Event> viewAvailableEvent(String username){
        return this.eventManager.getAvailableEvents(username);
    }

    public void addEvent(String name, Instant time, String eventName, List<String> participants, String room, int capacity){
        this.eventManager.createEvent(name, time, eventName, participants, room, capacity);
    }

    public void enrollSelf(String username){
        this.eventPresenter.enrollOrDrop();
        int choice = eventScanner.nextInt();
        this.eventPresenter.enterId();
        int id = eventScanner.nextInt();
        if (choice == 1){
            this.enroll(id, username);
        }
        else{
            this.drop(id, username);
        }
    }

    public void deleteEvent(int eventId){
        this.eventManager.deleteEvent(eventId);
    }

    //TODO: Change time from string to instant
//    public Instant getTimeInput(){
//        this.eventPresenter.enterTime();
//        return eventScanner.nextLine();
//    }

    public String getSpeakerNameInput(){
        this.eventPresenter.enterSpeakerName();
        return eventScanner.nextLine();
    }

    public String getEventNameInput(){
        this.eventPresenter.enterEventName();
        return eventScanner.nextLine();
    }

    public String getRoomInput(){
        this.eventPresenter.enterRoom();
        return eventScanner.nextLine();
    }

    public int getCapacityInput(){
        this.eventPresenter.enterCapacity();
        return eventScanner.nextInt();
    }

    //TODO: Change time from string to instant
//    public void editTime(int eventId){
//        this.eventPresenter.enterTime();
//        Instant time = this.getTimeInput();
//        this.eventManager.editTime(eventId, time);
//    }

    public void editSpeakerName(int eventId){
        String name = this.getSpeakerNameInput();
        this.eventManager.editSpeakerName(eventId, name);
    }

    public void editEventName(int eventId){
        String name = this.getEventNameInput();
        this.eventManager.editEventName(eventId, name);
    }

    public void editRoom(int eventId){
        String name = this.getRoomInput();
        this.eventManager.editRoom(eventId, name);
    }

    public void editCapacity(int eventId){
        int capacity = this.getCapacityInput();
        this.eventManager.editCapacity(eventId, capacity);
    }

    public void performSelectedAction(String username, Permission permissionSelected){
        if (permissionSelected== Permission.EVENT_SELF_ENROLL){
            enrollSelf(username);
        }
        else if(permissionSelected == Permission.EVENT_OTHER_ENROLL){
            this.eventPresenter.enterUsername();
            String name = eventScanner.nextLine();
            enrollSelf(name);
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
            this.eventPresenter.enterId();
            int id = eventScanner.nextInt();
            deleteEvent(id);
        }
        else if(permissionSelected == Permission.EVENT_EDIT){
            this.eventPresenter.enterId();
            int id = eventScanner.nextInt();
            this.eventPresenter.enterChange();
            int typeofChanges = eventScanner.nextInt();
            if(typeofChanges == 1){
                //editTime(id);
            }
            else if(typeofChanges == 2){
                editSpeakerName(id);
            }
            else if(typeofChanges == 3){
                editEventName(id);
            }
            else if(typeofChanges == 4){
                editRoom(id);
            }
            else if(typeofChanges == 5){
                editCapacity(id);
            }
        }
        else if(permissionSelected == Permission.VIEW_HOSTING_EVENTS){
            this.eventPresenter.viewSpeakerList();
            List<Event> result = this.eventManager.getEventBySpeakerName(username);
            System.out.println(result);
        }
        else if(permissionSelected == Permission.VIEW_ALL_EVENTS){
            this.eventPresenter.viewEvents();
            int choice = eventScanner.nextInt();
            if(choice == 1){
                this.eventPresenter.viewAllEvents();
                List<Event> result = this.viewAllEvents();
                System.out.println(result);
            }
            else if(choice ==2){
                this.eventPresenter.viewAvailableEvents();
                List<Event> result = this.viewAvailableEvent(username);
                System.out.println(result);
            }
            else if(choice ==3){
                this.eventPresenter.viewMyEvents();
                List<Event> result = this.checkMyEvents(username);
                System.out.println(result);
            }
        }

    }



}