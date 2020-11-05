package Events;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import java.util.ArrayList;


public class EventManager {
    private HashMap<Integer, Event> events;

    public EventManager(){
        HashMap<Integer, Event> events = new HashMap<>();
    }

    //public void readFromFile(String path) throws ClassNotFoundException {
        //try {
            //InputStream file = new FileInputStream(path);
            //InputStream buffer = new BufferedInputStream(file);
            //ObjectInput input = new ObjectInputStream(buffer);

            //events = (Hashmap<int, Event.java>) input.readObject();
            //input.close();
        //} catch (IOException ex) {
            //logger.log(Level.SEVERE, "Cannot read from input.", ex);
        //}
    //}

    public List<Event> getEventsList() {
        List<Event> eventsList = new ArrayList<>();
        for (int key : events.keySet()) {
            eventsList.add(events.get(key));
        }
        return eventsList;
    }

    public Event getEventByName(String eventName){
        Event result = new Event();
        for (Event value : events.values()){
            if (value.eventName.equals(eventName)){
                result = value;
            }
        }
        return result;
    }

    public void createEvent(String name, Instant eventTime, String eventName, List<String> participants,
                               String room, String title, int capacity){
        //TODO: Validate events.
        //boolean noConflict = true;
        Event newEvent = new Event(name, eventTime, eventName, participants, room, title, capacity);
//        for (Event.java value : events.values()){
//            if ((value.eventTime == newEvent.eventTime) && (value.room == newEvent.room)) {
//                noConflict = false;
//                break;
//            }
//        }

//        if (!noConflict){
//            System.out.println("Cannot creat this event because it conflicts with an existing event.");
//        }else{
//            this.events.put(newEvent.id, newEvent);
//        }
        this.events.put(newEvent.id, newEvent);
        return noConflict;
    }

    public List<String> getParticipants(int eventID){
        return events.get(eventID).getParticipants();
    }

    public boolean enrolluser(int eventID, String Username){
        List<String> result = this.getParticipants(eventID);
        boolean check = this.checkCapacity(result); // To be fixed
        if(check==true){
            events.get(eventID).participants.add(Username);
            return true;
        }
        return false;
    }

    public boolean dropuser(int eventID, String Username){
        events.get(eventID).participants.remove(Username);
        return true;
    }

    public Event getinfo(int eventID){
        return events.get(eventID);
    }

    public List<Event> getavaliableevent(String username){
        List<Event> avaliableEvents = new ArrayList<>();
        for(int i=0; i<events.size(); i++){
            if(events.get(i).participants.contains(username)){
                avaliableEvents.add(events.get(i));
            }
            return avaliableEvents;
        }
    }

    public void addEventToHash(Event event) { // Temporary method for testing purposes only
        events.put(event.getId(), event);
    }

    public boolean checkCapacity(List<String> participants, int maxCapacity){
        return participants.size() < maxCapacity;
    }

    public HashMap<Integer, Event> getEventsHash() { // Getter required as events variable is private
        return events;
    }

    //TODO: Make Exceptions for this
    public boolean checkConflict(Event event){ // Made public for test purposes
        boolean flag = false;
        for(Event values : events.values()) {
            if ((values.getEventTime() == event.getEventTime()) && (values.getRoom().equals(event.getRoom()) || values.getSpeakername().equals(event.getSpeakername()))) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}


