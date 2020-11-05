package Events;

import java.time.Instant;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.String;


public class EventManger {
    private HashMap<Integer, Event> events;

    public EventManger(){
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
        List<Event> eventsList = new List<Event>;
        for (int key : events.keySet()) {
            eventsList.add(events.get(key));
        }
        return eventsList;
    }

    public Event getEventByName(String eventName){
        Event result = new Event;
        for (Event value : events.values()){
            if (value.eventName == eventName){
                result = value;
            }
        }
        return result;
    }

    public void createEvent(String name, Instant eventTime, String eventName, List<String> participants,
                               String room, String title, int capacity){
        //TODO: Validate events.
        //boolean noConflict = true;
        Event newEvent = new Event(name, eventName, participants, room, title, capacity);
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
        //TODO: validate event capacity
        ArrayList<String> reuslt = this.getParticipants(eventID);
        //boolean check = this.checkCapacity( result);
        if(check==true){
            events.get(eventID).participants.add(Username);
        }
    }

    public boolean dropuser(int eventID, String Username){


    }

    public Event getinfo(){

    }

    public int getavaliableevent(){

    }

    public boolean checkCapacity(){

    }

    private boolean checkConflict(){

    }

}


