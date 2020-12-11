package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;

public class MessagePresenter {

    private MessageManager manager;
    private HashMap<String, String> inputMap;

    /**
     * Initiates MessagePresenter
     * @param manager MessageManager
     * @param inputMap Hashmap mapping strings to associating user inputs
     */
    public MessagePresenter(MessageManager manager, HashMap<String, String> inputMap){
        this.manager = manager;
        this.inputMap =inputMap;
    }

    /**
     * Returns the unread inbox of the given user.
     * @return a string representation of all unread messages of the user whose username is given by {@code inputMap}.
     */
    public String getUserUnread(){
        return manager.unreadInboxToString(inputMap.get("username"));
    }

    /**
     * Returns the whole message inbox of the given user.
     * @return a string representation of all messages of the user whose username is given by {@code inputMap}
     */
    public String getUserInbox(){
        return manager.wholeInboxToString(inputMap.get("username"));
    }

    /**
     * Returns the message inbox of the given user from the given sender.
     * @return a string representation of all messages sent from a specific sender to the user whose usernames are
     * given by {@code inputMap}.
     */
    public String getUserInboxFrom(){
        return manager.singleInboxToString(inputMap.get("username"), inputMap.get("from"));

    }

    /**
     * Returns the archived inbox of the given user.
     * @return a string representation of all archived messages of the user whose username is given by {@code inputMap}.
     */
    public String getUserArchived(){
        return manager.archivedMessagesToString(inputMap.get("username"));
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