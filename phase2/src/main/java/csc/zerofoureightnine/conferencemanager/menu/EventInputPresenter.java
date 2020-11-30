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

    public void forSpeaker(){
        System.out.println("For speaker:");
    }

    public void isBooked(String booked){
        System.out.println(booked + " is not available at that time.");
    }

    public void notSpeaker(String speaker){
        System.out.println(speaker + " is not a speaker.");
        forSpeaker();
    }

    public void capacityWarning(){
        System.out.println("Note: capacity cannot exceed 999");
    }

    public void invalidCap(){
        System.out.println("Invalid capacity.");
    }
}
