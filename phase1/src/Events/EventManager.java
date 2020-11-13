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

    public void createEvent(String speakerName, Instant eventTime, String eventName, String room, int capacity){
        Event newEvent = new Event(speakerName, eventTime, eventName, room, capacity);
        String newId = newEvent.getEventTime() + newEvent.getRoom();
        boolean flag = checkConflictEvent(newEvent);
        if (!flag){
            this.events.put(newId, newEvent);
        }
    }

    private void createEditedEvent(String speakerName, Instant eventTime, String eventName, List<String> participants, String room, int capacity){
        Event newEvent = new Event(speakerName, eventTime, eventName, participants, room, capacity);
        String newId = newEvent.getEventTime() + newEvent.getRoom();
        boolean flag = checkConflictEvent(newEvent);
        if (!flag){
            this.events.put(newId, newEvent);
        }
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

    public HashMap<String, Event> getEventsHash() { // Getter required as events variable is private
        return events;
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
        for (String id : getEventsHash().keySet()){
            if (getParticipants(id).contains(username)){
                myEvents.add(getEventsHash().get(id));
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
        String eventId = String.valueOf(id);
        String speakerName = event.getSpeakerName();
        String time = event.getEventTime().toString();
        String eventName = event.getEventName();
        String participants = event.getParticipants().toString();
        String room = event.getRoom();
        String capacity = String.valueOf(event.getCapacity());
        return new String[]{eventId, speakerName, time, eventName, participants, room, capacity};
    }
}


