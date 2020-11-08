package Events;

//import Messaging.MessagePresenter;
//import Users.User;
//import Users.UserManager;

import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class EventController{
    private EventManager eventManager;
    private EventPresenter eventPresenter;

    public EventController(EventManager eventManager) {
        this.eventManager = eventManager;
        this.eventPresenter = new EventPresenter();
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

    public Scanner getUserInput() {
        return new Scanner(System.in);
    }

    public String getEnrollEventInput(Scanner userInput){
        this.eventPresenter.enrollEvent();
        return userInput.nextLine();
    }

    public String getDropEventInput(Scanner userInput){
        this.eventPresenter.dropEvent();
        return userInput.nextLine();
    }

    public List<Event> getViewEventListInput(Scanner userInput){
        this.eventPresenter.viewEventList();
        String answer = userInput.nextLine();

        if (answer.equals("Yes")){
            return this.viewAllEvents();
        }else{return null;}
    }

    public List<Event> getViewMyListInput(Scanner userInput){
        this.eventPresenter.viewMyList();
        String username = userInput.nextLine();
        return this.checkMyEvents(username);
    }

//    public List<Event> getViewSpeakerList(){
//        Scanner userInput = new Scanner(System.in);
//        this.eventPresenter.viewSpeakerList();
//        String username = userInput.nextLine();
//        return this.checkMyEvents(username);
//    }

}