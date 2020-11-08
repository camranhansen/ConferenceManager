package Messaging;

public class MessagePresenter {

    public void viewOrSend(){
        System.out.println("to view messages: enter 'view'.\nto send a message: enter 'send'.");
    }

    public void viewAllOrFromOne(){
        System.out.println("to view whole inbox: enter 'all'.\nto view from one username: enter 'from'.");
    }

    public void viewFromOneUsername(){
        System.out.println("enter sender username:");
    }

    public void sendToOne(){
        System.out.println("Would you like to send to one? enter: 'yes' or 'no'");
    }

    public void enterUsername(){
        System.out.println("enter username to send to:");
    }

    public void enterContent(){
        System.out.println("enter text:");
    }

    public void sendToAllAttendees(){
        System.out.println("would you like to send to all attendees? enter: 'yes' or 'no'");
    }

    public void sendToAllSpeakers(){
        System.out.println("would you like to send to all speakers? enter: 'yes' or 'no'");
    }

    public void sendToEvents(){
        System.out.println("would you like to send to all your events? enter: 'yes' or 'no'");
    }
}