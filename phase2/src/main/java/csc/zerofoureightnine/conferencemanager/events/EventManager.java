package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;

import java.time.Instant;
import java.util.*;


public class EventManager {
    /**
     * csc.zerofoureightnine.conferencemanager.events stores a hashmap that maps csc.zerofoureightnine.conferencemanager.events' IDs to an event.
     */
    private PersistentMap<String, EventData> pMap;

    /**
     * Instantiates EventManager.
     *
     * @param pMap A map storing event IDs as keys and {@link EventData} as keys.
     */
    public EventManager(PersistentMap<String, EventData> pMap){
        this.pMap = pMap;
    }


    //This function is necessary.
    /**
     * Returns a list of all {@link Event} IDs.
     * @return A list of all {@link Event} IDs as {@link String}.
     */
    public List<String> getAllEventIds(){
        return new ArrayList<>(this.pMap.keySet());
    }



    /**
     * Returns a list of IDs of csc.zerofoureightnine.conferencemanager.events that has a specific speaker.
     * @param userName Username of the speaker.
     * @return A list of csc.zerofoureightnine.conferencemanager.events' IDs.
     */
    public List<String> getHostingEvents(String userName){
        List<String> aList = new ArrayList<>();
        for (EventData value: pMap.values()){
            if(value.getSpeakers().contains(userName)){
                aList.add(value.getId());
            }
        }
        return aList;
    }

    /**
     * Defines a new event with the details speaker's name, event time, event name, room, and capacity.
     * Automatically determines event type {@link EventType} depending on how many speakers are inputted.
     * Adds this event to the collection of all csc.zerofoureightnine.conferencemanager.events.
     * @param speakerName Name of the speaker for the event.
     * @param eventTime Time that the event takes place.
     * @param eventName Name of the event.
     * @param room Room which the event is being held in.
     * @param capacity Maximum capacity of the event.
     */
    public void createEvent(List<String> speakerName, Instant eventTime, String eventName, String room, int capacity){
        createEvent(speakerName, eventTime, eventName, room, capacity, getEventTypeForNumberOfSpeakers(speakerName.toArray().length));
    }

    /**
     * Defines a new event with the details speaker's name, event time, event name, room, and capacity, and type.
     * Adds this event to the collection of all csc.zerofoureightnine.conferencemanager.events.
     * @param speakerName Name of the speaker for the event.
     * @param eventTime Time that the event takes place.
     * @param eventName Name of the event.
     * @param room Room which the event is being held in.
     * @param capacity Maximum capacity of the event.
     * @param type The event type. see the {@link EventType} for more information.
     */
    public void createEvent(List<String> speakerName, Instant eventTime, String eventName, String room, int capacity, EventType type){
        EventData newEvent = new EventData();
        newEvent.setId(getID(room, eventTime));
        newEvent.setEventData(speakerName, eventTime, eventName, room, capacity, type);
        this.pMap.put(newEvent.getId(), newEvent);
    }

    /**
     * Deletes the event identified by an event ID from the collection of all csc.zerofoureightnine.conferencemanager.events.
     * @param eventId ID of the selected event.
     */
    public void deleteEvent(String eventId){
        this.pMap.remove(eventId);
    }

    /**
     * Registers the user with a given username in the event identified by an event ID.
     *
     * @param eventID ID of the selected event.
     * @param userName Username of the current user.
     */
    public void enrollUser(String eventID, String userName){
        this.pMap.beginInteraction();
        this.pMap.get(eventID).addParticipants(userName);
        this.pMap.endInteraction();
    }

    /**
     * Withdraws the user with a given username from the event identified by an event ID.
     *
     * @param eventID ID of the selected event.
     * @param userName Username of the current user.
     */
    public void dropUser(String eventID, String userName){
        this.pMap.beginInteraction();
        this.pMap.get(eventID).removeParticipant(userName);
        this.pMap.endInteraction();
    }

    /**
     * Returns a boolean to identify if the event reaches its capacity.
     * @param eventID ID of the selected event.
     * @return False if the event does not reach its capacity. True if the event is full.
     */
    public boolean isEventFull(String eventID){
        //Call this as !isEventFull(ID);.
        return (this.pMap.get(eventID).getCapacity() <=
                this.pMap.get(eventID).getParticipants().size());
    }

    /**
     * Returns a boolean to identify if the event exists.
     * @param eventID ID of the selected event.
     * @return False if the event does not exist. True if the event exists.
     */
    public boolean eventExists(String eventID){
        return this.pMap.containsKey(eventID);
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
        for(EventData value: pMap.values()) {
            if(!time.contains(value.getTime())){
                availableEvents.add(value.getId());
            }
        }
        return availableEvents;
    }

    /**
     * Returns a list of time of csc.zerofoureightnine.conferencemanager.events from given a list of events' IDs.
     * @param id A list of csc.zerofoureightnine.conferencemanager.events' IDs.
     * @return A list of csc.zerofoureightnine.conferencemanager.events' time .
     */
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
        for (String id: this.pMap.keySet()){
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

        for (EventData event: this.pMap.values()){
            if(event.getTime().equals(timeSlot)){
                for(String str: username){
                    if(event.getSpeakers().contains(str)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Returns a boolean to identify if a room is available during a specific time slot.
     * @param timeslot A starting time of a time slot.
     * @param room A room name that is chosen.
     * @return False if the room is not in use during the time slot.
     * True if the room is available during a specific timeslot
     */
    public boolean checkRoom(Instant timeslot, String room){
         for (String id: pMap.keySet()) {
             if(getEventTime(id).equals(room+timeslot)){
                 return false;
             }
         }
         return true;
    }



    /**
     * Returns a list of IDs of csc.zerofoureightnine.conferencemanager.events that a user is enrolled in.
     * @param username Username of the current user.
     * @return A list of csc.zerofoureightnine.conferencemanager.events' IDs.
     */
    public List<String> getUserEvents(String username) {
        List<String> myEvents = new ArrayList<>();
        for (EventData value : pMap.values()){
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
        this.pMap.beginInteraction();
        EventData value = this.pMap.get(id);
        value.getSpeakers().clear();
        value.addSpeakers(newSpeaker);
        this.pMap.endInteraction();
    }


    /**
     * Changes the event name of an event to a new one.
     * @param id ID of the selected event.
     * @param newEventName A new name of the event.
     */
    public void editEventName(String id, String newEventName){
        this.pMap.beginInteraction();
        this.pMap.get(id).setEventName(newEventName);
        this.pMap.endInteraction();
    }

    /**
     * Changes the room of an event to a new one.
     * @param id ID of the selected event.
     * @param newRoom A new room of the event.
     */
    public void editRoom(String id, String newRoom) {

        this.pMap.beginInteraction();
        this.pMap.get(id).setRoom(newRoom);
        EventData data = this.pMap.remove(id);
        data.setId(id);
        this.pMap.endInteraction();
    }

    /**
     * Changes the capacity of an event to a new one.
     * @param id ID of the selected event.
     * @param newCapacity A new capacity of the event.
     */
    public void editCapacity(String id, int newCapacity){
        this.pMap.beginInteraction();
        EventData ed = this.pMap.get(id);
        ed.setCapacity(newCapacity);
        this.pMap.endInteraction();
    }
    /**
     * Changes the starting time of an event to a new one.
     * @param id ID of the selected event.
     * @param newTime A new starting time of the event.
     */
    public void editTime(String id, Instant newTime){
        this.pMap.beginInteraction();
        EventData ed = this.pMap.remove(id);
        ed.setTime(newTime);
        ed.setId(getID(ed.getRoom(), newTime));
        pMap.put(ed.getId(), ed);
        this.pMap.endInteraction();
    }

    /**
     * Returns a string representation of an event.
     * @param id Event id
     * @return A string containing the given event's name time, room and capacity.
     */
    public Map<String,String> getEventData(String id){
        EventData e = this.pMap.get(id);
        LinkedHashMap<String,String> eventData = new LinkedHashMap<>();
        eventData.put("Name",e.getEventName());
        eventData.put("Speaker Name",e.getSpeakers().toString());
        eventData.put("Event Time",e.getTime().toString());
        eventData.put("Room",e.getRoom());
        eventData.put("Capacity", String.valueOf(e.getCapacity()));
        eventData.put("Type",e.getType().toString());
        eventData.put("ID", e.getId());

        return eventData;
    }

    //New event data getters. Please only use these!
    /**
     * Getter function for the speaker name.
     * @return String the name of the speaker.
     */
    public Collection<String> getEventSpeakerName(String id){
        return pMap.get(id).getSpeakers();
    }
    /**
     * Getter function for the event time.
     * @return Instant represents the time of the event.
     */
    public Instant getEventTime(String id) {
        return pMap.get(id).getTime();
    }
    /**
     * Getter function for the event name.
     * @return String represent the name of the event.
     */
    public String getEventName(String id){
        return pMap.get(id).getEventName();
    }
    /**
     * Getter function for the list of username who joined the event.
     * @return List of string represents all the username who joined the event.
     */
    public Collection<String> getParticipants(String id) {
        return pMap.get(id).getParticipants();
    }
    /**
     * Getter function for the name of the room.
     * @return String represents the name of room.
     */
    public String getRoom(String id){
        return pMap.get(id).getRoom();
    }
    /**
     * Getter function for the maximum number of people can join the event.
     * @return Integer represents the maximum number of people can join the event.
     */
    public int getCapacity(String id){
        return pMap.get(id).getCapacity();
    }

    public EventType getEventType(String id){
        return pMap.get(id).getType();
    }

    /**
     * Returns the total number of events.
     * @return An integer representing the number of events which are currently registered.
     */
    public int totalEventNumber() { return pMap.size(); }

    /**
     * Returns the total number of events of a specific {@link EventType}.
     * @param eventType The type of event to search for.
     * @return An integer representing the number of events of the specified {@link EventType}.
     */
    public int totalOfEventType(EventType eventType) {
        int total = 0;
        for (String key: getAllEventIds()) {
            if (getEventType(key).equals(eventType)) { total++; }
        }
        return total;
    }

    /**
     * Returns the most common {@link EventType} which events are registered as.
     * @return The most commonly-occuring {@link EventType}.
     */
    public EventType mostPopularEventType() {
        int parties = totalOfEventType(EventType.PARTY);
        int singles = totalOfEventType(EventType.SINGLE);
        int multies = totalOfEventType(EventType.MULTI);
        if (parties >= singles && parties >= multies) { return EventType.PARTY; }
        if (singles >= parties && singles >= multies) { return EventType.SINGLE; }
        return EventType.MULTI;
    }

    /**
     * Returns the truncated average capacity of all registered events.
     * @return An integer representing the truncated average capacity of all registered events.
     */
    public int averageCapacity() {
        int total = 0;
        for (String key: getAllEventIds()) {
            total += pMap.get(key).getCapacity();
        }
        return total / pMap.size();
    }

    /**
     * Converts a string representation of time to an Instant.
     *
     * @param dayOfMonth the day of the month.
     * @param hour the hour.
     * @return An Instant representation of the input data.
     */
    public Instant parseTime(String dayOfMonth, String hour) {
        if (dayOfMonth.length() == 1) {
            dayOfMonth = "0" + dayOfMonth;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return Instant.parse("2020-12-" + dayOfMonth.trim() + "T" + hour.trim() + ":00:00.00Z");
    }

    /**
     * Gets the appropriate {@link EventType} for the number of speakers
     *
     * @param numOfSpeakers the number of speakers
     * @return the appropriate {@link EventType}
     */
    public EventType getEventTypeForNumberOfSpeakers(int numOfSpeakers) {
        switch (numOfSpeakers) {
            case 0:
                return EventType.PARTY;
            case 1:
                return EventType.SINGLE;
            default:
                return EventType.MULTI;
        }
    }

    /**
     * Returns the record of event data from the database.
     * @param id Event id
     * @return A record of event data.
     */
    public EventData getDataById(String id){
        return this.pMap.get(id);
    }

    /**
     * Returns the number of records in the event database.
     * @return The size of the database.
     */
    public int size(){
        return this.pMap.size();
    }

    /**
     * Generates the corresponding ID for an event.
     * @param room The room of the event.
     * @param time The time of the event represented by a {@link Instant}.
     * @return A string representing the ID of the event.
     */
    public String getID(String room, Instant time) {
        return room + time.toString();
    }
    
    /**
     * Clear all the records in the database.
     */
    public void clear(){
        this.pMap.clear();
    }
}


