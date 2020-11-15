package Events;

import java.time.Instant;
import java.util.*;


public class EventManager {
    private HashMap<String, Event> events;

    public EventManager(){
        this.events = new HashMap<>();
    }

    public HashMap<String, Event> getEvents(){
        //TODO remove this function because it returns events, thereby breaking dependency rule
        return this.events;
    }
    //This function is necessary.
    public List<String> getAllEventIds(){
        ArrayList<String> allIDS = new ArrayList<>(this.events.keySet());
        return allIDS;
    }


    public List<String> getEventList(){
        /// return a list of eventId of all event
        List<String> aList = new ArrayList<>();
        for (Event value: this.events.values()){
            aList.add(value.getId());
        }
        return aList;
    }

    public Event getEventByName(String eventName){
        //TODO remove this function, because it assumes that all event names are unique.
        Event result = new Event();
        for (Event value : events.values()){
            if (value.getEventName().equals(eventName)) result = value;
        }
        return result;
    }


    public List<String> getSpkEvents(String userName){
        //This method is better since it returns event IDs
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

    public void enrollUser(String eventID, String userName){
        //TODO refactor this method into only doing the enrollUser.
        //Call isEventFull in controller to check
        this.events.get(eventID).getParticipants().add(userName);
    }

    public void dropUser(String eventID, String userName){
        this.events.get(eventID).getParticipants().remove(userName);
    }

    public boolean isEventFull(String eventID){
        //Call this as !isEventFull(ID);.
        return (this.events.get(eventID).getCapacity() <=
                this.events.get(eventID).getParticipants().size());
    }

    public boolean eventExists(String eventID){
        return this.events.containsKey(eventID);
    }



    public Event getInfo(String eventID){
        //TODO: remove this, since it returns an Event
        return events.get(eventID);
    }

    public List<String> getAvailableEvents(String username){
        //Return a list of event IDs
        List<String> myEvents = getUserEvents(username);
        List<Instant> time = helpMethod(myEvents);
        List<String> availableEvents = new ArrayList<>();
        for(Event value: this.events.values()){
            if(!time.contains(value.getEventTime())){
                availableEvents.add(value.getId());
            }
        }
        return availableEvents;
    }

    private List<Instant> helpMethod(List<String> id){
        List<Instant> time = new ArrayList<>();
        for(int x=0; x<id.size(); x++) {
            System.out.println();
            time.add(getEventTime(id.get(x)));
        }
        return time;
    }

    public void addEventToHash(Event event) { // Temporary method for testing purposes only
        //TODO remove this. just use Create event
        events.put(event.getId(), event);
        this.events.put(event.getId(), event);
    }





    // Check conflict methods
    public boolean checkUserInEvent(Instant timeslot, String username){
        for (String id: this.events.keySet()){
            if(getEventTime(id).equals(timeslot) && getParticipants(id).contains(username)){
                return true;
            }
        }
        return false;
    }

    public boolean checkConflictSpeaker(Instant timeSlot, String username) {

        for (String id: this.events.keySet()){
            if(this.events.get(id).getEventTime().equals(timeSlot) &&
                    this.events.get(id).getSpeakerName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean checkRoom(Instant timeslot, String room){
         for (String id: this.events.keySet()){
             if(getEventTime(id).equals(timeslot) && getRoom(id).equals(room)){
                 return true;
             }
         }
         return false;
    }

    public boolean checkCapacity(String eventId) {
        Event event = this.events.get(eventId);
        return event.getParticipants().size() < event.getCapacity(); //return ture if the event is not full.
    }

    // Check user not appears in different places at the same time.

    public List<String> getUserEvents(String username) {
        //TODO should be refactored into returning a list of Strings
        List<String> myEvents = new ArrayList<>();
        for (Event value : this.events.values()){
            if (value.getParticipants().contains(username)){
                myEvents.add(value.getId());
            }
        }
        return myEvents;
    }
    public void editSpeakerName(String id, String newSpeaker){
        Event event = this.events.get(id);
        createEditedEvent(newSpeaker, event.getEventTime(), event.getEventName(), event.getParticipants(),
                event.getRoom(), event.getCapacity());

    }

    public void editEventName(String id, String newEventName){
        Event event = this.events.get(id);
        createEditedEvent(event.getSpeakerName(), event.getEventTime(), newEventName, event.getParticipants(),
                event.getRoom(), event.getCapacity());
    }

    public void editRoom(String id, String newRoom){
        Event event = this.events.get(id);
        createEditedEvent(event.getSpeakerName(), event.getEventTime(), event.getEventName(), event.getParticipants(),
                newRoom, event.getCapacity());
        deleteEvent(id);
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
        deleteEvent(id);
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

    public String getFormattedEvent(String id){
        Event e = this.events.get(id);
        String lineSep = ":" + System.lineSeparator();
        String formatted = "Event Name: " + e.getEventName() + lineSep +
                "Event Time: " + e.getEventTime() + lineSep +
                "Room: " + e.getRoom() + lineSep +
                "Capacity: " + e.getParticipants().size() + "/" + e.getCapacity() + lineSep;
        return formatted;
    }

    //New event data getters. Please only use these!
    public String getEventSpeakerName(String id){
        return events.get(id).getSpeakerName();
    }

    public Instant getEventTime(String id){
        return events.get(id).getEventTime();
    }

    public String getEventName(String id){
        return events.get(id).getEventName();
    }

    public List<String> getParticipants(String id){
        return events.get(id).getParticipants();
    }

    public String getRoom(String id){
        return events.get(id).getRoom();
    }

    public int getCapacity(String id){
        return events.get(id).getCapacity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventManager that = (EventManager) o;
        return events.equals(that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events);
    }
}


