package Events;
import java.time.Instant;


public class Event {
    public String speakername;
    public Instant time;
    public String eventname;
    public String[] participants;
    public String room;
    public String title;
    public int capacity;
    public int id;
    public static int count = 0;

    public Event(String name, Instant eventtime, String eventname, String[] participants, String room, String title, int capacity ){
        this.speakername=name;
        this.time=eventtime;
        this.eventname=eventname;
        this.participants=participants;
        this.room=room;
        this.title=title;
        this.capacity=capacity;
        this.id= Event.count;
    }

    public String getSpeakername(){
        return this.speakername;
    }

    public Instant geteventTime(){
        return this.time;
    }

    public String getEventname(){
        return this.eventname;
    }

    public String[] getParticipants(){
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
