package Events;

import Users.User;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import java.util.ArrayList;


public class EventManager {
    private HashMap<String, Event> events;

    public EventManager(){
        this.events = new HashMap<>();
    }

    public HashMap getEvents(){
        return this.events;
    }

    public List<Event> getEventsList() {
        List<Event> eventsList = new ArrayList<>();
        for (String key : events.keySet()) {
            eventsList.add(events.get(key));
        }
        return eventsList;
    }

    public Event getEventByName(String eventName){
        Event result = new Event();
        for (Event value : events.values()){
            if (value.getEventName().equals(eventName)) result = value;
        }
        return result;
    }

    public List<Event> getEventBySpeakerName(String userName){
        List<Event> aList = new ArrayList<>();
        for (Event value : events.values()){
            if (value.getSpeakerName().equals(userName)){
                aList.add(value);
            }
        }
        return aList;
    }

    public List<String> getSpkEvents(String userName){
        List<String> aList = new ArrayList<>();
        for (Event value: events.values()){
            if(value.getSpeakerName().equals(userName)){
                aList.add(value.getId());
            }
        }
        return aList;
    }

    public void createEvent(String speakerName, Instant eventTime, String eventName, String room, int capacity){
        Event newEvent = new Event(speakerName, eventTime, eventName, room, capacity);
        this.events.put(newEvent.getId(), newEvent);

    }

    private void createEditedEvent(String speakerName, Instant eventTime, String eventName, List<String> participants, String room, int capacity){
        Event newEvent = new Event(speakerName, eventTime, eventName, participants, room, capacity);
        this.events.put(newEvent.getId(), newEvent);

    }

    public void deleteEvent(String eventId){
        this.events.remove(eventId);
    }

    public List<String> getParticipants(String eventID){
        return events.get(eventID).getParticipants();
    }

    public boolean enrollUser(String eventID, String Username){
        List<String> result = this.getParticipants(eventID);
        boolean check = this.checkCapacity(result, events.get(eventID).getCapacity()); // To be fixed
        if(check){
            events.get(eventID).getParticipants().add(Username);
            return true;
        }
        return false;
    }

    public boolean dropUser(String eventID, String Username){
        events.get(eventID).getParticipants().remove(Username);
        return true;
    }

    public Event getInfo(String eventID){
        //TODO: DISCUSS LATER
        return events.get(eventID);
    }

    public List<Event> getAvailableEvents(String username){
        List<Event> myEvents = getUserEvents(username);
        List<Instant> time = helpMethod(myEvents);
        List<Event> availableEvents = new ArrayList<>();
        for(Event value : this.events.values()){
            if(!time.contains(value.getEventTime())){
                availableEvents.add(value);
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


    //TODO: Make Exceptions for this
    private boolean checkConflictEvent(Event event){
        boolean flag = false; //It means the event doesn't have a conflict
        String newId = event.getEventTime() + event.getRoom();
        for (String key : events.keySet()){
            if (key.equals(newId)) {
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

    private boolean checkConflictSpeaker(Event event, String username) {
        boolean flag = false; // no conflicts
        List<Event> list = getEventBySpeakerName(username);
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
        for (Event value : this.events.values()){
            if (value.getParticipants().contains(username)){
                myEvents.add(value);
            }
        }
        return myEvents;
    }

//    public String[] getSpkEvents(String username){
//        ArrayList<String> spkEvents = new ArrayList<>();
//        for(Event event:getEventsList()){
//            if(event.getSpeakerName().equals(username)){
//                spkEvents.add(event.getId());
//            }
//        }
//        String[] eventIds = new String[spkEvents.size()];
//        eventIds = spkEvents.toArray(eventIds);
//        return eventIds;
//    }

    public void editRoom(String id, String newRoom){
        Event event = this.events.get(id);
        createEditedEvent(event.getSpeakerName(), event.getEventTime(), event.getEventName(), event.getParticipants(),
                newRoom, event.getCapacity());
    }

    public void editSpeakerName(String id, String newSpeaker){
        Event event = this.events.get(id);
        if (!checkConflictSpeaker(event, newSpeaker)){
        createEditedEvent(newSpeaker, event.getEventTime(), event.getEventName(), event.getParticipants(),
                event.getRoom(), event.getCapacity());
        }
    }

    public void editCapacity(String id, int newCapacity){
        Event event = this.events.get(id);
        createEditedEvent(event.getSpeakerName(), event.getEventTime(), event.getEventName(), event.getParticipants(),
                event.getRoom(), newCapacity);
    }

    public void editTime(String id, Instant newTime){
        Event event = this.events.get(id);
        createEditedEvent(event.getSpeakerName(), newTime, event.getEventName(), event.getParticipants(),
                event.getRoom(), event.getCapacity());
    }

    public void editEventName(String id, String newEventName){
        Event event = this.events.get(id);
        createEditedEvent(event.getSpeakerName(), event.getEventTime(), newEventName, event.getParticipants(),
                event.getRoom(), event.getCapacity());
    }

    //gateway method
    public ArrayList<String[]> getAllEventData(){
        ArrayList<String[]> eventList = new ArrayList<>();
        for (String id: this.events.keySet()) {
            eventList.add(getSingleUserData(id));
        }
        return eventList;
    }

    public String[] getSingleUserData(String id) {
        Event event = this.events.get(id);
        String speakerName = event.getSpeakerName();
        String time = event.getEventTime().toString();
        String eventName = event.getEventName();
        String participants = event.getParticipants().toString();
        String room = event.getRoom();
        String capacity = String.valueOf(event.getCapacity());
        return new String[]{id, speakerName, time, eventName, participants, room, capacity};
    }

    public void setEventData(String[] eventData){
        String id = eventData[0];
        String speakerName = eventData[1];
        Instant time = Instant.parse(eventData[2]);
        String eventName = eventData[3];
        String participants = eventData[4];
        String room = eventData[5];
        int capacity = Integer.parseInt(eventData[6]);
        if (!participants.equals("[]")) {
            String participants1 = participants.substring(1, participants.length()-1);
            String[] list = participants1.split(",");
            if (!this.events.containsKey(id)){
                Event event = new Event(speakerName, time, eventName, Arrays.asList(list), room, capacity);
                this.events.put(id, event);
            }
        }else{
            this.createEvent(speakerName, time, eventName, room, capacity);
        }

    }

    private List<Instant> helpMethod(List<Event> name){
        List<Instant> time = new ArrayList<>();
        for(int x=0; x<name.size(); x++) {
            time.add(name.get(x).getEventTime());
        }
        return time;
    }
}



