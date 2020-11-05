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

            //events = (Hashmap<int, Event>) input.readObject();
            //input.close();
        //} catch (IOException ex) {
            //logger.log(Level.SEVERE, "Cannot read from input.", ex);
        //}
    //}

    public ArrayList<Event> getEventsList() {
        ArrayList<Event> eventsList = new ArrayList<Event>;
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

    public boolean createEvent(String name, Instant eventTime, String eventName, ArrayList<String> participants,
                               String room, String title, int capacity){
        boolean noConflict = true;
        Event newEvent = new Event(name, eventName, participants, room, title, capacity);
        for (Event value : events.values()){
            if ((value.eventTime == newEvent.eventTime) && (value.room == newEvent.room)) {
                noConflict = false;
                break;
            }
        }

        if (!noConflict){
            System.out.println("Cannot creat this event because it conflicts with an existing event.");
        }else{
            this.events.put(newEvent.id, newEvent);
        }

        return noConflict;
    }

    public ArrayList<String> getParticipants(int eventID){
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


