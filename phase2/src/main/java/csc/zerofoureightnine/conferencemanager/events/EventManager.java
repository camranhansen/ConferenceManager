package csc.zerofoureightnine.conferencemanager.events;

import java.time.Instant;
import java.util.*;


public class EventManager {
    /**
     * csc.zerofoureightnine.conferencemanager.events stores a hashmap that maps csc.zerofoureightnine.conferencemanager.events' IDs to an event.
     */
    private HashMap<String, Events> events;

    /**
     * Instantiates EventManager
     */
    public EventManager(){
        this.events = new HashMap<>();
    }


    //This function is necessary.
    /**
     * Returns a list of all csc.zerofoureightnine.conferencemanager.events' IDs that are stored in the instance variable csc.zerofoureightnine.conferencemanager.events.
     * @return A list of all csc.zerofoureightnine.conferencemanager.events' IDs.
     */
    public List<String> getAllEventIds(){
        ArrayList<String> allIDS = new ArrayList<>(this.events.keySet());
        return allIDS;
    }



    /**
     * Returns a list of IDs of csc.zerofoureightnine.conferencemanager.events that has a specific speaker.
     * @param userName Username of the speaker.
     * @return A list of csc.zerofoureightnine.conferencemanager.events' IDs.
     */
    public List<String> getSpkEvents(String userName){
        List<String> aList = new ArrayList<>();
        for (Events value: events.values()){
            if(value.getSpeakerName().contains(userName)){
                aList.add(value.getId());
            }
        }
        return aList;
    }

    /**
     * Defines a new event with the details speaker's name, event time, event name, room, and capacity.
     * Adds this event to the collection of all csc.zerofoureightnine.conferencemanager.events.
     * @param speakerName Name of the speaker for the event.
     * @param eventTime Time that the event takes place.
     * @param eventName Name of the event.
     * @param room Room which the event is being held in.
     * @param capacity Maximum capacity of the event.
     */
    public void createEvent(List<String> speakerName, Instant eventTime, String eventName, String room, int capacity, EventType type){
        Events newEvent = new Events(speakerName, eventTime, eventName, room, capacity, type);
        this.events.put(newEvent.getId(), newEvent);
    }


    /**
     * Defines a new event when an existing event is modified and being replaced by the new one.
     * Adds this event to the collection of all csc.zerofoureightnine.conferencemanager.events and replaces the event with a same ID.
     * @param speakerName Name of the speaker for the event.
     * @param eventTime Time that the event takes place.
     * @param eventName Name of the event.
     * @param participants Current participants of the event.
     * @param room Room which the event is being held in.
     * @param capacity Maximum capacity of the event.
     */
    private void createEditedEvent(List<String> speakerName, Instant eventTime, String eventName, List<String> participants, String room, int capacity, EventType type){
        Events newEvent = new Events(speakerName, participants,  eventTime, eventName, room, capacity, type);
        this.events.put(newEvent.getId(), newEvent);
    }


    /**
     * Deletes the event identified by an event ID from the collection of all csc.zerofoureightnine.conferencemanager.events.
     * @param eventId ID of the selected event.
     */
    public void deleteEvent(String eventId){
        this.events.remove(eventId);
    }

    /**
     * Registers the user with a given username in the event identified by an event ID.
     *
     * @param eventID ID of the selected event.
     * @param userName Username of the current user.
     */
    public void enrollUser(String eventID, String userName){

        this.events.get(eventID).addParticipant(userName);
    }

    /**
     * Withdraws the user with a given username from the event identified by an event ID.
     *
     * @param eventID ID of the selected event.
     * @param userName Username of the current user.
     */
    public void dropUser(String eventID, String userName){
        this.events.get(eventID).removeParticipant(userName);
    }

    /**
     * Returns a boolean to identify if the event reaches its capacity.
     * @param eventID ID of the selected event.
     * @return False if the event does not reach its capacity. True if the event is full.
     */
    public boolean isEventFull(String eventID){
        //Call this as !isEventFull(ID);.
        return (this.events.get(eventID).getCapacity() <=
                this.events.get(eventID).getParticipants().size());
    }

    /**
     * Returns a boolean to identify if the event exists.
     * @param eventID ID of the selected event.
     * @return False if the event does not exist. True if the event exists.
     */
    public boolean eventExists(String eventID){
        return this.events.containsKey(eventID);
    }


    /**
     * Returns a list of IDs of csc.zerofoureightnine.conferencemanager.events that does not conflict with the csc.zerofoureightnine.conferencemanager.events that the current user already enrolled in.
     * @param username Username of the current user.
     * @return A list of csc.zerofoureightnine.conferencemanager.events' IDs .
     */
    public List<String> getAvailableEvents(String username){
        List<String> myEvents = getUserEvents(username);
        List<Instant> time = myEventTime(myEvents);
        List<String> availableEvents = new ArrayList<>();
        for(Events value: this.events.values()){
            if(!time.contains(value.getEventTime())){
                availableEvents.add(value.getId());
            }
        }
        return availableEvents;
    }

    private List<Instant> myEventTime(List<String> id){
        List<Instant> time = new ArrayList<>();
        for(int x=0; x<id.size(); x++) {
            System.out.println();
            time.add(getEventTime(id.get(x)));
        }
        return time;
    }


    // Check conflict methods
    /**
     * Returns a boolean to identify if a user is enrolled in any event during a specific time slot.
     * @param timeslot A starting time of a time slot.
     * @param username Username of the current user.
     * @return False if the user is not enrolled in any event during the time slot.
     * True if the user is enrolled in some csc.zerofoureightnine.conferencemanager.events during the time slot.
     */
    public boolean checkUserInEvent(Instant timeslot, String username){
        for (String id: this.events.keySet()){
            if(getEventTime(id).equals(timeslot) && getParticipants(id).contains(username)){
                return true;
            }
        }
        return false;
    }
    /**
     * Returns a boolean to identify if a speaker is holding any event during a specific time slot.
     * @param timeSlot A starting time of a time slot.
     * @param username Username of the speaker.
     * @return False if the speaker is not holding any event during the time slot.
     * True if the speaker is holding some csc.zerofoureightnine.conferencemanager.events during the time slot.
     */
    public boolean checkConflictSpeaker(Instant timeSlot, List<String> username) {

        for (String id: this.events.keySet()){
            if(this.events.get(id).getEventTime().equals(timeSlot)){
                for(String str: username){
                    if(this.events.get(id).getSpeakerName().contains(str)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Returns a boolean to identify if a room is in use during a specific time slot.
     * @param timeslot A starting time of a time slot.
     * @param room A room name that is chosen.
     * @return False if the room is not in use during the time slot.
     * True if the room is in use during the time slot.
     */
    public boolean checkRoom(Instant timeslot, String room){
         for (String id: this.events.keySet()){
             if(getEventTime(id).equals(timeslot) && getRoom(id).equals(room)){
                 return true;
             }
         }
         return false;
    }

    /**
     * Returns a list of IDs of csc.zerofoureightnine.conferencemanager.events that a user is enrolled in.
     * @param username Username of the current user.
     * @return A list of csc.zerofoureightnine.conferencemanager.events' IDs.
     */
    public List<String> getUserEvents(String username) {
        List<String> myEvents = new ArrayList<>();
        for (Events value : this.events.values()){
            if (value.getParticipants().contains(username)){
                myEvents.add(value.getId());
            }
        }
        return myEvents;
    }

    /**
     * Changes the speaker of an event to a new speaker.
     * @param id ID of the selected event.
     * @param newSpeaker Username of the new speaker.
     */
    public void editSpeakerName(String id, List<String> newSpeaker){
        Events value = this.events.get(id);
        createEditedEvent(newSpeaker, value.getEventTime(), value.getEventName(), value.getParticipants(),
                value.getRoom(), value.getCapacity(), value.getType());
    }


    /**
     * Changes the event name of an event to a new one.
     * @param id ID of the selected event.
     * @param newEventName A new name of the event.
     */
    public void editEventName(String id, String newEventName){
        Events value = this.events.get(id);
        createEditedEvent(value.getSpeakerName(), value.getEventTime(), newEventName, value.getParticipants(),
                value.getRoom(), value.getCapacity(), value.getType());
    }
    /**
     * Changes the room of an event to a new one.
     * @param id ID of the selected event.
     * @param newRoom A new room of the event.
     */
    public void editRoom(String id, String newRoom){
        Events value = this.events.get(id);
        createEditedEvent(value.getSpeakerName(), value.getEventTime(), value.getEventName(), value.getParticipants(),
                newRoom, value.getCapacity(), value.getType());
        deleteEvent(id);
    }

    /**
     * Changes the capacity of an event to a new one.
     * @param id ID of the selected event.
     * @param newCapacity A new capacity of the event.
     */
    public void editCapacity(String id, int newCapacity){
        Events value = this.events.get(id);
        createEditedEvent(value.getSpeakerName(), value.getEventTime(), value.getEventName(), value.getParticipants(),
                value.getRoom(), newCapacity, value.getType());
    }
    /**
     * Changes the starting time of an event to a new one.
     * @param id ID of the selected event.
     * @param newTime A new starting time of the event.
     */
    public void editTime(String id, Instant newTime){
        Events value = this.events.get(id);
        createEditedEvent(value.getSpeakerName(), newTime, value.getEventName(), value.getParticipants(),
                value.getRoom(), value.getCapacity(), value.getType());
        deleteEvent(id);
    }




    //csc.zerofoureightnine.conferencemanager.gateway method

    /**
     * Returns all information about all csc.zerofoureightnine.conferencemanager.events stored in csc.zerofoureightnine.conferencemanager.events hashmap in EventManager.
     * @return An arraylist of String[]. Each String[] contains information of one event stored in csc.zerofoureightnine.conferencemanager.events hashmap in
     * EventManager.
     */
    public ArrayList<String[]> getAllEventData(){
        ArrayList<String[]> eventList = new ArrayList<>();
        for (String id: this.events.keySet()) {
            eventList.add(getSingleEventData(id));
        }
        return eventList;
    }

    /**
     * Returns all information of the given event that's stored in csc.zerofoureightnine.conferencemanager.events hashmap in EventManager.
     * @param id event id
     * @return A String[] that contains information of one event stored in csc.zerofoureightnine.conferencemanager.events hashmap, including event id, speaker's
     * name, time, event name, participants, room number, and capacity.
     */
    public String[] getSingleEventData(String id) {
        Events event = this.events.get(id);
        String speakerName = event.getSpeakerName().toString();
        String time = event.getEventTime().toString();
        String eventName = event.getEventName();
        String participants = event.getParticipants().toString();
        String room = event.getRoom();
        String capacity = String.valueOf(event.getCapacity());
        String type = event.getType().toString();
        return new String[]{id, speakerName, time, eventName, participants, room, capacity, type};
    }

    /**
     * Store the event data of an event in EventManager.
     * @param eventData A string containing all datas of one event including event id, speaker's
     * name, time, event name, participants, room number, and capacity.
     */
    public void setEventData(String[] eventData){
        String id = eventData[0];
        String speakerName[] = eventData[1].split(",");
        List<String> spk = new ArrayList<>();
        spk = Arrays.asList(speakerName);
        Instant time = Instant.parse(eventData[2]);
        String eventName = eventData[3];
        String participants = eventData[4];
        String room = eventData[5];
        int capacity = Integer.parseInt(eventData[6]);
        EventType type = EventType.valueOf(eventData[7]);
        if (!participants.equals("[]")) {
            String participants1 = participants.substring(1, participants.length()-1);
            String[] list = participants1.split(",");
            if (!this.events.containsKey(id)){
                Events event = new Events(spk, Arrays.asList(list), time, eventName, room, capacity, type);
                this.events.put(id, event);
            }
        }else{
            this.createEvent(spk, time, eventName, room, capacity, type);
        }

    }

    /**
     * Returns a string representation of an event.
     * @param id Event id
     * @return A string containing the given event's name time, room and capacity.
     */
    public String getFormattedEvent(String id){
        Events e = this.events.get(id);
        String lineSep = ":" + System.lineSeparator();
        String formatted = "Event Name: " + e.getEventName() + lineSep +
                "Event Time: " + e.getEventTime() + lineSep +
                "Room: " + e.getRoom() + lineSep +
                "Capacity: " + e.getParticipants().size() + "/" + e.getCapacity() + lineSep;
        return formatted;
    }

    //New event data getters. Please only use these!
    /**
     * Getter function for the speaker name.
     * @return String the name of the speaker.
     */
    public List<String> getEventSpeakerName(String id){
        return events.get(id).getSpeakerName();
    }
    /**
     * Getter function for the event time.
     * @return Instant represents the time of the event.
     */
    public Instant getEventTime(String id){
        return events.get(id).getEventTime();
    }
    /**
     * Getter function for the event name.
     * @return String represent the name of the event.
     */
    public String getEventName(String id){
        return events.get(id).getEventName();
    }
    /**
     * Getter function for the list of username who joined the event.
     * @return List of string represents all the username who joined the event.
     */
    public List<String> getParticipants(String id){
        return events.get(id).getParticipants();
    }
    /**
     * Getter function for the name of the room.
     * @return String represents the name of room.
     */
    public String getRoom(String id){
        return events.get(id).getRoom();
    }
    /**
     * Getter function for the maximum number of people can join the event.
     * @return Integer represents the maximum number of people can join the event.
     */
    public int getCapacity(String id){
        return events.get(id).getCapacity();
    }

    public EventType getEventType(String id){
        return events.get(id).getType();
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


