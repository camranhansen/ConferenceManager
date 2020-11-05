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

    public List<Event> checkMyEvents(String userName){
        //TODO: Validate userName.
        List<Event> myEvents = new ArrayList<Event>;
           for (int id : eventManager.events.keySet()){
               if (eventManager.getParticipants(id).contains(userName)){
                   myEvents.add(eventManager.events.get(id));
               }
           }
       return myEvents;
    }

}