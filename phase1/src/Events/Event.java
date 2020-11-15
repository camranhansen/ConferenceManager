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

    public Event(){
    }
    public Event(String speakerName, Instant time, String eventName,List<String> participants, String room, int capacity ){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = participants;
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString() ;
    }
    public Event(String speakerName, Instant time, String eventName, String room, int capacity ){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = new ArrayList<>();
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString();
    }



    public String getSpeakerName(){
        return this.speakerName;
    }

    public Instant getEventTime(){
        return this.time;
    }

    public String getEventName(){
        return this.eventName;
    }

    public List<String> getParticipants(){
        return this.participants;
    }

    public String getRoom(){
        return this.room;
    }

    public int getCapacity(){
        return this.capacity;
    }

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
