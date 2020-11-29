package csc.zerofoureightnine.conferencemanager.events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Events {
    private List<String> speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
    private String id;
    private EventType type;


    public Events(List<String> speakerName, Instant time, String eventName, String room, int capacity, EventType type){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = new ArrayList<>();
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString();
        this.type = type;
    }

    public Events(List<String> speakerName, List<String> participants, Instant time, String eventName, String room, int capacity, EventType type ){
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
     * Getter function for the event time.
     * @return Instant represents the time of the event.
     */
    public Instant getEventTime(){
        return this.time;
    }

    /**
     * Getter function for the event name.
     * @return String represent the name of the event.
     */
    public String getEventName(){
        return this.eventName;
    }

    /**
     * Getter function for the list of username who joined the event.
     * @return List of string represents all the username who joined the event.
     */
    public List<String> getParticipants(){
        return this.participants;
    }

    /**
     * Add a participant
     * @param username the username of the participant in question
     */
    public void addParticipant(String username){
        this.participants.add(username);
    }

    public void removeParticipant(String username){
        this.participants.remove(username);
    }
    /**
     * Getter function for the name of the room.
     * @return String represents the name of room.
     */
    public String getRoom(){
        return this.room;
    }

    /**
     * Getter function for the maximum number of people can join the event.
     * @return Integer represents the maximum number of people can join the event.
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Getter function for the id of the event, which is the combination of the room name + the event time.
     * @return String represents the eventId
     */
    public String getId(){
        return this.id;
    }

}
