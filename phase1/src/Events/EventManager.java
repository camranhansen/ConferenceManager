package Events;

import Users.User;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import java.util.ArrayList;


public class EventManager {
    public HashMap<Integer, Event> events;

    public EventManager(){
        this.events = new HashMap<>();
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
        //TODO: Validate events
        Event result = new Event();
        for (Event value : events.values()){
            if (value.getEventName().equals(eventName)) result = value;
        }
        return result;
    }

    public List<Event> getEventBySpeakerName(String userName){
        //TODO: Validate events
        List<Event> aList = new ArrayList<>();
        for (Event value : events.values()){
            if (value.getSpeakerName().equals(userName)){
                aList.add(value);
            }
        }
        return aList;
    }

    public void createEvent(String name, Instant eventTime, String eventName, List<String> participants,
                               String room, int capacity){
        //TODO: Validate events.
        //boolean noConflict = true;
        Event newEvent = new Event(name, eventTime, eventName, participants, room, capacity);
//        for (Event.java value : events.values()){
//            if ((value.eventTime == newEvent.eventTime) && (value.room == newEvent.room)) {
//                noConflict = false;
//                break;
//            }
//        }

//        if (!noConflict){
//            System.out.println("Cannot create this event because it conflicts with an existing event.");
//        }else{
//            this.events.put(newEvent.id, newEvent);
//        }
        this.events.put(newEvent.getId(), newEvent);

    }

    public void deleteEvent(int eventId){
        this.events.remove(eventId);
    }

    public List<String> getParticipants(int eventID){
        return events.get(eventID).getParticipants();
    }

    public boolean enrollUser(int eventID, String Username){
        List<String> result = this.getParticipants(eventID);
        boolean check = this.checkCapacity(result, events.get(eventID).getCapacity()); // To be fixed
        if(check){
            events.get(eventID).getParticipants().add(Username);
            return true;
        }
        return false;
    }

    public boolean dropUser(int eventID, String Username){
        events.get(eventID).getParticipants().remove(Username);
        return true;
    }

    public Event getInfo(int eventID){
        //TODO: DISCUSS LATER
        return events.get(eventID);
    }

    public List<Event> getAvailableEvents(String username){
        List<Event> availableEvents = new ArrayList<>();
        for(int i=0; i<events.size(); i++){
            if(!events.get(i).getParticipants().contains(username) && !checkConflictUser(events.get(i), username)){
                availableEvents.add(events.get(i));
            }
        }
        return availableEvents;
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
    private boolean checkConflictEvent(Event event){
        boolean flag = false;
        for(Event values : events.values()) {
            if ((values.getEventTime() == event.getEventTime()) && (values.getRoom().equals(event.getRoom()) || values.getSpeakerName().equals(event.getSpeakerName()))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private boolean checkConflictUser(Event event, String username) {
        boolean flag = false;
        List<Event> list = getUserEvents(username);
        for (Event value : list) {
            if (value.getEventTime().equals(event.getEventTime())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public List<Event> getUserEvents(String username) {
        List<Event> myEvents = new ArrayList<>();
        for (int id : getEventsHash().keySet()){
            if (getParticipants(id).contains(username)){
                myEvents.add(getEventsHash().get(id));
            }
        }
        return myEvents;
    }

    public Integer[] getSpkEvents(String username){
        ArrayList<Integer> spkEvents = new ArrayList<>();
        for(Event event:getEventsList()){
            if(event.getSpeakerName().equals(username)){
                spkEvents.add(event.getId());
            }
        }
        Integer[] eventIds = new Integer[spkEvents.size()];
        eventIds = spkEvents.toArray(eventIds);
        return eventIds;
    }

    public void editTime(int eventId, Instant time){
        this.events.get(eventId).setTime(time);
    }

    public void editSpeakerName(int eventId, String name){
        this.events.get(eventId).setSpeakerName(name);
    }
    public void editEventName(int eventId, String name){
        this.events.get(eventId).setEventName(name);
    }
    public void editRoom(int eventId, String name){
        this.events.get(eventId).setRoom(name);
    }
    public void editCapacity(int eventId, int capacity){
        this.events.get(eventId).setCapacity(capacity);
    }


    //gateway method
    public ArrayList<String[]> getAllEventData(){
        ArrayList<String[]> eventList = new ArrayList<>();
        for (String id: this.events.keySet()) {
            eventList.add(getSingleEventData(id));
        }
        return eventList;
    }

    public String[] getSingleEventData(String id) {
        Event event = this.events.get(id);
        String eventId = id;
        String speakerName = event.getSpeakerName();
        String time = event.getEventTime().toString();
        String eventName = event.getEventName();
        String participants = event.getParticipants().toString();
        String room = event.getRoom();
        String capacity = String.valueOf(event.getCapacity());
        return new String[]{eventId, speakerName, time, eventName, participants, room, capacity};
    }

    public void setEventData(String[] eventData) {
        String eventId = eventData[0];
        String speakerName = eventData[1];
        Instant time = Instant.parse(eventData[2]);
        String eventName = eventData[3];
        String participants = eventData[4];
        String[] listOfParticipants = participants.split(",");
        String room = eventData[5];
        int capacity = Integer.parseInt(eventData[6]);
        if (!this.events.containsKey(eventId)) {
            this.createEvent(speakerName, time, eventName, Arrays.asList(listOfParticipants), room, capacity);
        }
    }
    }


