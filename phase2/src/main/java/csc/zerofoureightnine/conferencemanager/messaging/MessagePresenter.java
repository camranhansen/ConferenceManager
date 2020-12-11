package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

public class MessagePresenter {

    public String getPromptForSendTo() {
        return "User to send to";
    }

    public String getMessageSentCompletion(TopicPresentable nextNode) {
        return "Message sent!";
    }

    public String getPromptForMessageBody() {
        return "Please type your message";
    }

    public String invalidRecipient() {
        return "This recipient is invalid, please try again";
    }
}