package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.NameablePresentation;

public class MessagePresenter {

    public void printMessages(String messages) {
        System.out.println(messages);
    }

    public void noAttInEvent() {
        System.out.println("there is no one in these csc.zerofoureightnine.conferencemanager.events to send to");
    }

    public void noAttendees() {
        System.out.println("There are no attendees to send to");
    }

    public void noSpeakers() {
        System.out.println("There are no speakers to send to");
    }

    public void noEvent() {
        System.out.println("This event doesn't exist");
    }

    public void notSpeakerEvent() {
        System.out.println("This event is not your event");
    }

    public void usernameInvalid() {
        System.out.println("Invalid username. Please try again.");
    }

    public String getPromptForSendTo() {
        return "User to send to";
    }

    public String getMessageSentCompletion(NameablePresentation nextNode) {
        return "Message sent! :)";
    }

    public String getPromptForMessageBody() {
        return "Please type your message";
    }

    public String invalidRecipient() {
        return "This recipient is invalid, please try again";
    }
}