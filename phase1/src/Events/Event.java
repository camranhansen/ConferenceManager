package Events;
import java.time.Instant;
import java.util.List;


public class Event {
    private String speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
    private int id;
    private static int count = 0;

    public Event(){

    }
    public Event(String name, Instant time, String eventName, List<String> participants, String room, int capacity ){
        this.speakerName = name;
        this.time = time;
        this.eventName = eventName;
        this.participants = participants;
        this.room = room;
        this.capacity = capacity;
        Event.count += 1;
    }

    public String getSpeakername(){
        return this.speakerName;
    }

    public void setEventid(){
        this.id = Event.count;
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

    public int getId(){
        return this.id;
    }


}
