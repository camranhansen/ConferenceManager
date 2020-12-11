package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;

public class MessagePresenter {

    private MessageManager manager;
    private HashMap<String, String> inputMap;

    /**
     * Initiates MessagePresenter
     * @param manager {@link MessageManager}
     * @param inputMap {@link HashMap} mapping strings to associating user inputs
     */
    public MessagePresenter(MessageManager manager, HashMap<String, String> inputMap){
        this.manager = manager;
        this.inputMap =inputMap;
    }

    /**
     * Returns the unread inbox of the given user.
     * @return a {@link String} representing all unread messages of the user whose username is given by {@code inputMap}
     */
    public String getUserUnread(){
        return manager.unreadInboxToString(inputMap.get("username"));
    }

    /**
     * Returns the whole message inbox of the given user.
     * @return a {@link String} representing all messages of the user whose username is given by {@code inputMap}
     */
    public String getUserInbox(){
        return manager.wholeInboxToString(inputMap.get("username"));
    }

    /**
     * Returns the message inbox of the given user from the given sender.
     * @return a {@link String} representing all messages sent from a specific sender to the user whose usernames are
     * given by {@code inputMap}.
     */
    public String getUserInboxFrom(){
        return manager.singleInboxToString(inputMap.get("username"), inputMap.get("from"));

    }

    /**
     * Returns the archived inbox of the given user.
     * @return a {@link String} representing all archived messages of the user whose username is given by {@code inputMap}.
     */
    public String getUserArchived(){
        return manager.archivedMessagesToString(inputMap.get("username"));
    }

    /**
     * Returns the prompt for users to input username of whom they want to send the message to.
     * @return the {@link String} "User to send to"
     */
    public String getPromptForSendTo() {
        return "User to send to";
    }

    /**
     * Returns the prompt when users successfully send their message.
     * @param nextNode nextNode
     * @return a {@link String} "Message sent!"
     */
    public String getMessageSentCompletion(TopicPresentable nextNode) {
        return "Message sent!";
    }

    /**
     * Returns the prompt for users to input the content of their message.
     * @return a {@link String} "Please type your message"
     */
    public String getPromptForMessageBody() {
        return "Please type your message";
    }

    /**
     * Returns the prompt when the username is invalid.
     * @return a {@link String} "This recipient is invalid, please try again"
     */
    public String invalidRecipient() {
        return "This recipient is invalid, please try again";
    }
}