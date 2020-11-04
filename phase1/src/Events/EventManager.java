package Events;

import java.util.HashMap;
import java.util.ArrayList;

public class EventManger {
    private HashMap<int, Event> events;

    public void readFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            events = (Hashmap<int, Event>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

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
            };
        }
        return result;
    }

    public boolean createEvent(String name, Instant eventTime, String eventName, ArrayList<String> participants,
                               String room, String title, int capacity){
        boolean noConflict = true;
        Event newEvent = new Event(name, eventTime, eventName, participants, room, title, capacity);
        for (Event value : events.values()){
            if ((value.eventTime == newEvent.eventTime) && (value.room == newEvent.room)){
                noConflict = false;
            }
        }

        if (noConflict == false){
            System.out.println("Cannot creat this event because it conflicts with an existing event.");
        }else{
            this.events.put(newEvent.id, newEvent);
        }

        return noConflict;
    }

    public ArrayList<String> getParticipants(int eventID){
        return events.get(eventID).getParticipants();
    }

}


