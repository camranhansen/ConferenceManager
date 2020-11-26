package events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MultiSpkEvent extends Events{
    private List<String> speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
    private String id;
    private String type;

    public MultiSpkEvent(List<String> speakerName, Instant time, String eventName, String room, int capacity ){
        this.speakerName = speakerName;
        this.time = time;
        this.eventName = eventName;
        this.participants = new ArrayList<>();
        this.room = room;
        this.capacity = capacity;
        this.id = room + time.toString();
        this.type = "M";
    }

    public MultiSpkEvent(List<String> speakerName, Instant time, String eventName, List<String> participants, String room, int capacity){
        this(speakerName, time, eventName, room, capacity);
        this.participants = participants;
    }

    public String getType(){
        return this.type;
    }
}
