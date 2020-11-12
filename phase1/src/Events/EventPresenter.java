package Events;

public class EventPresenter {
    public void enterId(){
        System.out.println("Please enter the event id:");
    }

    public void enrollOrDrop(){
        System.out.println("Do you want to 1. enroll an event or 2. drop an event? Reply 1 or 2:");
    }

    public void enterChange(){
        System.out.println("What information would you like to change: 1.Time 2.Speaker name 3.Event name 4.Room 5.Capacity");
    }

    //EVENT_OTHER_ENROLL
    public void enterUsername(){
        System.out.println("Please enter the username: ");
    }

    //EVENT_EDIT, EVENT_CREATE
    public void enterTime(){
        System.out.println("Please enter an event time:");
    }

    public void enterSpeakerName(){
        System.out.println("Please enter a speaker name:");
    }

    public void enterEventName(){
        System.out.println("Please enter an event name:");
    }

    public void enterRoom(){
        System.out.println("Please enter a room name:");
    }

    public void enterCapacity(){
        System.out.println("Please enter a number of capacity:");
    }

   // VIEW_ALL_EVENTS
    public void viewEvents(){
        System.out.println("What would you like to check: 1.All events; 2.All available events; 3.My events");
    }
    public void viewAllEvents(){System.out.println("List of all events:");}

    public void viewAvailableEvents(){System.out.println("List of available events:");}

    public void viewMyEvents(){System.out.println("List of events that you enroll:");}

    //VIEW_HOSTING_EVENTS
    public void viewSpeakerList(){
        System.out.println("List of events that you are hosting:");
    }


}