package csc.zerofoureightnine.conferencemanager.events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private List<String> speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
    private String id;
    private EventType type;





    /**
     * Construct an event with an empty list of participants.
     * @param speakerName A list of the speaker's name(s). Empty if <code>type</code> is PARTY,
     *                    Size 1 if SINGLE, Size 2 or greater if MULTI.
     * @param time Instant representing the time the event will start at.
     *             We assume that all events last for one hour.
     * @param eventName The event name. May be duplicated across multiple events.
     * @param room The name of the room. Can only have one event per unique room name per 1-hour timeslot.
     * @param capacity The maximum capacity capacity for individuals in this specific event.
     * @param type Event type, taken from {@link EventType} enum
     */
    public Event(List<String> speakerName, Instant time, String eventName, String room, int capacity, EventType type){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = new ArrayList<>();
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString();
        this.type = type;
    }

    /**
     * Construct an event with a predetermined list of participants
     * @param speakerName A list of the speaker's name(s). Empty if <code>type</code> is PARTY,
     *                    Size 1 if SINGLE, Size 2 or greater if MULTI.
     * @param time Instant representing the time the event will start at.
     *             We assume that all events last for one hour.
     * @param eventName The event name. May be duplicated across multiple events.
     * @param room The name of the room. Can only have one event per unique room name per 1-hour timeslot.
     * @param capacity The maximum capacity capacity for individuals in this specific event.
     * @param type Event type, taken from {@link EventType} enum
     */
    public Event(List<String> speakerName, List<String> participants, Instant time, String eventName, String room, int capacity, EventType type ){
        this(speakerName, time, eventName, room, capacity, type);
        this.participants = participants;
    }

    public EventType getType(){
        return this.type;
    }

    public List<String> getSpeakerName(){
        return this.speakerName;
    }
    /**
     * Get the event time
     * @return Instant represents the time of the event.
     */
    public Instant getEventTime(){
        return this.time;
    }

    /**
     * Get the event name.
     * @return String represent the name of the event.
     */
    public String getEventName(){
        return this.eventName;
    }

    /**
     * Get a list of usernames representing the users who joined the event.
     * @return List of string represents all the username who joined the event.
     */
    public List<String> getParticipants(){
        return this.participants;
    }

    /**
     * Add a participant.
     * @param username the username of the participant in question
     */
    public void addParticipant(String username){
        this.participants.add(username);
    }

    /**
     * Removes a participant.
     * @param username the username of the participant in question
     */
    public void removeParticipant(String username){
        this.participants.remove(username);
    }

    /**
     * Get the name of the room that this event is taking place in.
     * @return String represents the name of room.
     */
    public String getRoom(){
        return this.room;
    }

    /**
     * Get the maximum capacity for individuals in this specific event.
     * Note that this is not tied to room: there can be multiple events in the same room
     * at different times with different capacities
     * @return Integer represents the maximum number of people can join the event.
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Get the id of the event, which is the concatenation of the room name + the event time,
     * which are by definition unique.
     * @return String represents the eventId
     */
    public String getId(){
        return this.id;
    }

}
