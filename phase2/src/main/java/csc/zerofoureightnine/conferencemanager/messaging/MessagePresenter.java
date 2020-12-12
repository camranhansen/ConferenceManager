package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;

public class MessagePresenter {
    private MessageManager manager;
    private HashMap<String, String> inputMap;

    /**
     * Initiates MessagePresenter
     * 
     * @param manager  {@link MessageManager}
     */
    public MessagePresenter(MessageManager manager) {
        this.manager = manager;
        this.inputMap = new HashMap<>();
    }

    /**
     * Returns the unread inbox of the given user.
     * 
     * @return a {@link String} representing all unread messages of the user whose
     *         username is given by {@code inputMap}
     */
    public String getUserUnread(String username, List<TopicPresentable> nextNode) {
        return manager.unreadInboxToString(username);
    }

    /**
     * Returns the whole message inbox of the given user.
     * 
     * @return a {@link String} representing all messages of the user whose username
     *         is given by {@code inputMap}
     */
    public String getUserInbox(String username, List<TopicPresentable> topics) {
        return manager.wholeInboxToString(username);
    }

    /**
     * Returns the message inbox of the given user from the given sender.
     * 
     * @return a {@link String} representing all messages sent from a specific
     *         sender to the user whose usernames are given by {@code inputMap}.
     */
    public String getUserInboxFrom(String username, List<TopicPresentable> nextNode) {
        return manager.singleInboxToString(username, inputMap.get("from"));

    }

    /**
     * Returns the archived inbox of the given user.
     * 
     * @return a {@link String} representing all archived messages of the user whose
     *         username is given by {@code inputMap}.
     */
    public String getUserArchived(String username, List<TopicPresentable> nextNode) {
        return manager.archivedMessagesToString(username);
    }

    /**
     * Returns the prompt for users to input username of whom they want to send the
     * message to.
     * 
     * @return the {@link String} "User to send to"
     */
    public String getPromptForSendTo(String username) {
        return "User to send to";
    }

    /**
     * Returns the prompt when users successfully send their message.
     * 
     * @param nextNode nextNode
     * @return a {@link String} "Message sent!"
     */
    public String getMessageSentCompletion(String username, TopicPresentable nextNode) {
        return "Message sent!";
    }

    /**
     * Returns the prompt for users to input the content of their message.
     * 
     * @return a {@link String} "Please type your message"
     */
    public String getPromptForMessageBody(String username) {
        return "Please type your message";
    }

    /**
     * Returns the prompt for users to re-enter their username.
     * 
     * @return a {@link String} "Please re-enter your username for security
     *         purposes"
     */
    public String getPromptUsername(String username) {
        return "Please re-enter your username for security purposes";
    }

    /**
     * Returns the prompt for users to enter who a message is from.
     * 
     * @return a {@link String} "Please enter the username that the message is from"
     */
    public String getPromptForFrom(String username) {
        return "Please enter the username that the message is from";
    }

    /**
     * Returns the prompt for users to enter the time a message was sent.
     * 
     * @return a {@link String} "Please enter the time the message was sent"
     */
    public String getPromptMessageTime(String username) {
        return "Please enter the time the message was sent";
    }

    /**
     * Returns the prompt for users to enter the content of a previous message.
     * Warns users that if any of the previous message details do not align with
     * their response, will return to the main menu.
     *
     * @return a {@link String} "Please enter content of the message. Note: if
     *         message does not exist, you will return to menu"
     */
    public String getPromptPreviousContent(String username) {
        return "Please enter content of the message. Note: if message does not exist, you will return to menu";
    }

    /**
     * Returns a prompt for users to enter the event id they'd like to message.
     * 
     * @return a {@link String} "Please enter event id to send to"
     */
    public String getPromptForEventId(String username) {
        return "Please enter event id to send to";
    }

    /**
     * Returns a message telling the user they have inputted something invalid.
     * 
     * @return a {@link String} "Invalid Input"
     */
    public String wrongInput() {
        return ("Invalid Input");
    }

    /**
     * Returns the prompt when the username is invalid.
     * 
     * @return a {@link String} "This recipient is invalid, please try again"
     */
    public String invalidRecipient() {
        return "This recipient is invalid, please try again";
    }

    /**
     * Returns the prompt when the username is invalid.
     * 
     * @return a {@link String} "This username is invalid, please try again"
     */
    public String invalidUsername() {
        return "This username is invalid, please try again";
    }

    /**
     * Returns the prompt when the time is invalid.
     * 
     * @return a {@link String} "This time is invalid, please try again"
     */
    public String invalidTime() {
        return "This time is invalid, please try again";
    }

    /**
     * Returns the prompt when the event id is invalid.
     * 
     * @return a {@link String} "This event id is invalid, please try again"
     */
    public String invalidEventId() {
        return "This event id is invalid, please try again";
    }

    public String promptForConfirmation(String username) {
        return "Press enter to continue";
    }
}