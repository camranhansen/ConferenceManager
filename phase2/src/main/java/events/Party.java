package events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Party extends Events {
    private List<String> speakerName;
    private Instant time;
    private String eventName;
    private List<String> participants;
    private String room;
    private int capacity;
    private String id;
    private String type;

    public Party(Instant time, String eventName, String room, int capacity ){
        this.time = time;
        this.eventName = eventName;
        this.room = room;
        this.capacity = capacity;
        this.participants = new ArrayList<>();
        this.type = "P";
        this.id = room + time.toString();
        this.speakerName = new ArrayList<>();
    }

    public Party(Instant time, String eventName, List<String> participants, String room, int capacity){
        this(time, eventName, room, capacity);
        this.participants = participants;
    }

    public String getType(){
        return this.type;
    }


}
