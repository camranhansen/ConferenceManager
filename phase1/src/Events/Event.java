package Events;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Event {
    private String speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
    //TODO UUID
    private String id;

    /**
     * create a new event with no input parameter.
     */
    public Event(){
    }

    /**
     * When organizer wants to edit a event, use this constructor to create a new event.
     * @param speakerName The name of the speaker.
     * @param time When does the event happen.
     * @param eventName The name of the event.
     * @param participants A list of username who joined the event.
     * @param room The name of the room of the event.
     * @param capacity The maximum number of people can join this event.
     */
    public Event(String speakerName, Instant time, String eventName,List<String> participants, String room, int capacity ){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = participants;
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString() ;
    }

    /**
     * Create a new event with given information.
     * @param speakerName The name of the speaker.
     * @param time When does the event happen.
     * @param eventName The name of the event.
     * @param room The name of the room of the event.
     * @param capacity The maximum number of people can join the event.
     */
    public Event(String speakerName, Instant time, String eventName, String room, int capacity ){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = new ArrayList<>();
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString();
    }

    /**
     * Getter function for the speaker name.
     * @return String the name of the speaker.
     */
    public String getSpeakerName(){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return capacity == event.capacity &&
                speakerName.equals(event.speakerName) &&
                time.equals(event.time) &&
                eventName.equals(event.eventName) &&
                participants.equals(event.participants) &&
                room.equals(event.room) &&
                id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speakerName, time, eventName, participants, room, capacity, id);
    }
}
