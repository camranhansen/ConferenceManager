package csc.zerofoureightnine.conferencemanager.menu;

public class EventInputPresenter {

    public void isFull(){
        System.out.println("This event is full.");
    }

    public void alreadyEnrolled(){
        System.out.println("Already enrolled in this event.");
    }

    public void enrolledAtSameTime(){
        System.out.println("Already enrolled in an event at this time.");
    }
}
