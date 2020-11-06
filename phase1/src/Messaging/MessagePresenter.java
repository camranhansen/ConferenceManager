package Messaging;

public class MessagePresenter {

    public void viewOrSend(){
        System.out.println("to view messages: enter view.\nto send a message: enter send.");
    }

    public void viewAllOrFromOne(){
        System.out.println("to view whole inbox: enter all.\nto view from one username: enter from.");
    }

    public void viewFromOneUsername(){
        System.out.println("enter sender username:");
    }

    public void sendToOne(){
        System.out.println("enter username to send to:");
    }

    public void sendToAllAttendees(){
        System.out.println("would you like to send to all attendees? answer: yes or no");
    }

    public void sendToAllSpeakers(){
        System.out.println("would you like to send to all speakers? answer: yes or no");
    }

    public void sendToEvents(){
        System.out.println("would you like to send to all your events? answer: yes or no");
    }
}