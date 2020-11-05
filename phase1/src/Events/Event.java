package Events;
import java.time.Instant;
import java.util.List;


public class Event {
    public String speakerName;
    public Instant time;
    public String eventName;
    public List<String> participants;
    public String room;
    public String title;
    public int capacity;
    public int id;
    public static int count = 0;

    public Event() {}
    public Event(String name, Instant time, String eventName, List<String> participants, String room, String title, int capacity ){
        this.speakerName = name;
        this.time = time;
        this.eventName = eventName;
        this.participants = participants;
        this.room = room;
        this.title = title;
        this.capacity = capacity;
        this.id = Event.count;
        Event.count += 1;
    }

    public String getSpeakername(){
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

    public String getTitle(){
        return this.title;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public int getId(){
        return this.id;
    }


}
