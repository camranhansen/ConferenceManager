package Events;

public class EventPresenter {
    public void enrollEvent(){
        System.out.println("Enter the event name you would like to enroll:");
    }

    public void dropEvent(){
        System.out.println("Enter the event name you would like to drop:");
    }

    public void viewEventList(){
        System.out.println("Would you like to see all events we have here: Yes/No ");
    }

    public void viewMyList(){
        System.out.println("Check mine events: Yes/No ");
    }

    public void viewSpeakerList(){
        System.out.println("Check events that I am register as speaker: Yes/No");
    }
}
