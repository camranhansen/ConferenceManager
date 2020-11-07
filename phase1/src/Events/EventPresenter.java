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
        System.out.println("Enter the username to see events you enroll:");
    }

    public void viewSpeakerList(){
        System.out.println("Check events that I am registered as speaker: Yes/No");
    }
}
