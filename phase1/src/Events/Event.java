package Events;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Event {
    private String speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
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
        this.id = time + room;
    }
    public Event(String speakerName, Instant time, String eventName, String room, int capacity ){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = new ArrayList<>();
        this.room = room;
        this.capacity = capacity;
        this.id = time + room;
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

}
