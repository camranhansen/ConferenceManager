package Events;
import java.time.Instant;
import java.util.ArrayList;


public class Event {
    public String Speakername;
    public Instant Time;
    public String Eventname;
    public ArrayList<String> Participants;
    public String Room;
    public String Title;
    public int Capacity;
    public int Id;
    public static int Count = 0;

    public Event(String name, Instant eventtime, String eventname, ArrayList<String> participants, String room, String title, int capacity ){
        this.Speakername=name;
        this.Time=eventtime;
        this.Eventname=eventname;
        this.Participants=participants;
        this.Room=room;
        this.Title=title;
        this.Capacity=capacity;
        this.Id= Event.Count;
        Event.Count += 1;
    }

    public String getSpeakername(){
        return this.Speakername;
    }

    public Instant geteventTime(){
        return this.Time;
    }

    public String getEventname(){
        return this.Eventname;
    }

    public ArrayList<String> getParticipants(){
        return this.Participants;
    }

    public String getRoom(){
        return this.Room;
    }

    public String getTitle(){
        return this.Title;
    }

    public int getCapacity(){
        return this.Capacity;
    }

    public int getId(){
        return this.Id;
    }


}
