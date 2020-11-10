package Events;

public class EventPresenter {
    public void enterId(){
        System.out.println("Please enter the event id");
    }

    public void enrollOrdrop(){
        System.out.println("Do you want to 1. enroll event or 2. drop event?");
    }

    public void enterChange(){
        System.out.println("What information would you like to change: 1.Time 2.Speaker name 3.Event name 4.Room 5.Capacity");
    }

    public void enterTime(){}

    public void enterSpeakerName(){}

    public void enterEventName(){}

    public void enterRoom(){}

    public void enterCapacity(){}

    public void viewEventList(){
        System.out.println("Would you like to see all events we have here: Yes/No ");
    }

    public void viewAllEvents(){}

    public void viewAvailableEvents(){}

    public void viewMyEvents(){}

    public void viewSpeakerList(){
        System.out.println("List of events that you are hosting");
    }

    public void viewEvents(){
        System.out.println("What would you like to check: 1.All events 2.All available events 3.Mine events");
    }
}
