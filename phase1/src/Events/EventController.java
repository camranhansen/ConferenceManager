package Events;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EventController{
    private UserManager userManager;
    private EventManager eventManager;

    public EventController(UserManager userManager, EventManager eventManager) {
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    public List<Event> viewAllEvents(){
        return eventManager.getEventsList();
    }

    public ArrayList<Event> checkMyEvents(String userName){
        ArrayList<Event> myEvents = new ArrayList<Event>;
       if (userManager.userExist(userName)){
           for (int id : eventManager.events.keySet()){
               if (eventManager.getParticipants(id).contains(userName)){
                   myEvents.add(eventManager.events.get(id))
               }
           }
       }
       return myEvents;
    }

    public void enroll(){

    }

    public void enroll(){

    }

}