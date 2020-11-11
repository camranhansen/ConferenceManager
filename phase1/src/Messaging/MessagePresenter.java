package Messaging;

public class MessagePresenter {

    public void viewAllOrFromOne(){
        System.out.println("to view whole inbox: enter '1'.\nto view from one username: enter '2''.");
    }

    public void enterUsername(){
        System.out.println("enter username to send to:");
    }

    public void enterContent(){
        System.out.println("Enter option number, or 0 to return:");
    }

    public void sendToAll(){
        System.out.println("to send to all attendees: enter '1'\nto send to all speakers: enter '2'");
    }

    public void sendToEvents(){
        System.out.println("to send to all your events: enter '1'\nto send to one event: enter '2'");
    }

    public void sendToOneEvent(){
        System.out.println("enter event id:");
    }

    public  void errorMessage() {
        System.out.println("Please make a selection by typing the options corresponding integer.");
    }
}