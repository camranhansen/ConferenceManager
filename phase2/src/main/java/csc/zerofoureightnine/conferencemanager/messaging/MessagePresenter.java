package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;

public class MessagePresenter {
    private MessageManager manager;
    private HashMap<String, String> inputMap;
    public MessagePresenter(MessageManager manager, HashMap<String, String> inputMap){
        this.manager = manager;
        this.inputMap =inputMap;
    }

    public String getUserUnread(){
        return manager.unreadInboxToString(inputMap.get("username"));
    }

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