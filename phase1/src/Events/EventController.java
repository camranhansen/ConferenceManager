package Events;

import Messaging.MessagePresenter;
//import Users.User;
//import Users.UserManager;

import Menus.SubController;
import Users.Permission;
import Users.Template;

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
        List<Event> myEvents = new ArrayList<>();
           for (int id : eventManager.getEventsHash().keySet()){
               if (eventManager.getParticipants(id).contains(userName)){
                   myEvents.add(eventManager.getEventsHash().get(id));
               }
           }
       return myEvents;
    }

    public void enroll(int eventId, String username){
        this.eventManager.enrollUser(eventId, username);
    }

    public void drop(int eventId, String username){
        this.eventManager.dropUser(eventId, username);
    }


    public List<Event> getViewEventListInput(Scanner userInput){
        this.eventPresenter.viewEventList();
        String answer = userInput.nextLine();

        if (answer.equals("Yes")){
            return this.viewAllEvents();
        }
        else {
            return null;
        }
    }

    public List<Event> viewAvailableEvent(String username){
        return this.eventManager.getAvailableEvent(username);
    }

    public void addEvent(String name, Instant time, String eventName, List<String> participants, String room, int capacity){
        this.eventManager.createEvent(name, time, eventName, participants, room, capacity);
    }

    public void enrollSelf(String username){
        this.eventPresenter.enrollOrdrop();
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

    //public void editTime(int eventId){
        //this.eventPresenter.enterTime();
        //Instant time = eventScanner.next();
        //this.eventManager.editTime(eventId, time);
    //}

    public void editSpekaername(int eventId){
        this.eventPresenter.enterSpeakerName();
        String name = eventScanner.nextLine();
        this.eventManager.editSpekaername(eventId, name);
    }

    public void editEventname(int eventId){
        this.eventPresenter.enterEventName();
        String name = eventScanner.nextLine();
        this.eventManager.editEventname(eventId, name);
    }

    public void editRoom(int eventId){
        this.eventPresenter.enterRoom();
        String name = eventScanner.nextLine();
        this.eventManager.editRoom(eventId, name);
    }

    public void editCapacity(int eventId){
        this.eventPresenter.enterCapacity();
        int capacity = eventScanner.nextInt();
        this.eventManager.editCapacity(eventId, capacity);
    }

    public void performSelectedAction(String username, Permission permissionSelected){
        if (permissionSelected== Permission.EVENT_SELF_ENROLL){
            enrollSelf(username);
        }
        else if(permissionSelected == Permission.EVENT_OTHER_ENROLL){
            String name = eventScanner.nextLine();
            enrollSelf(name);
        }
        //else if(permissionSelected == Permission.EVENT_CREATE){
            //this.eventScanner.enterInfo();
            //addEvent();
       // }
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
                editSpekaername(id);
            }
            else if(typeofChanges == 3){
                editEventname(id);
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