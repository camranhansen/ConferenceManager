package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.List;
import java.util.Map;

public class MessagePresenter {
    private MessageManager manager;
    private List<String> selectedMessageIDs;
    private Map<String, String> inputMap;

    /**
     * Initiates MessagePresenter
     * 
     * @param manager  {@link MessageManager}
     */
    public MessagePresenter(MessageManager manager, Map<String, String> inputMap) {
        this.manager = manager;
        this.inputMap = inputMap;
    }

    /**
     * Returns the unread inbox of the given user.
     * 
     * @return a {@link String} representing all unread messages of the user whose
     *         username is given by {@code inputMap}
     */
    public String getUserUnread(String username, List<TopicPresentable> nextNode) {
        List<String> unreadInbox = manager.unreadInboxToString(username);
        updateSelectedMessageIDs(unreadInbox);
        return this.buildMessageList(unreadInbox, 37);
    }

    /**
     * Returns a {@link String} of the messages that are being formatted.
     *
     * @param strings a list of {@link String} representing messages
     * @param idHeaderSize an int
     * @return a {@link String} representing messages
     */
    private String buildMessageList(List<String> strings, int idHeaderSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(i + ") ");
            sb.append(strings.get(i).substring(idHeaderSize));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the whole message inbox of the given user.
     * 
     * @return a {@link String} representing all messages of the user whose username
     *         is given by {@code inputMap}
     */
    public String getUserInbox(String username, List<TopicPresentable> topics) {
        List<String> userInbox = manager.wholeInboxToString(username);
        updateSelectedMessageIDs(userInbox);
        return this.buildMessageList(userInbox, 37);
    }

    /**
     * Returns the read message inbox of the given user.
     *
     * @return a {@link String} representing read messages of the user whose username
     *         is given by {@code inputMap}
     */
    public String getUserInboxRead(String username, List<TopicPresentable> topics) {
        List<String> userInbox = manager.readInboxToString(username);
        updateSelectedMessageIDs(userInbox);
        return this.buildMessageList(userInbox, 37);
    }

    /**
     * Returns the message inbox of the given user from the given sender.
     * 
     * @return a {@link String} representing all messages sent from a specific
     *         sender to the user whose usernames are given by {@code inputMap}.
     */
    public String getUserInboxFrom(String username, List<TopicPresentable> nextNode) {
        List<String> userInbox = manager.singleInboxToString(username, inputMap.get("from"));
        updateSelectedMessageIDs(userInbox);
        return this.buildMessageList(userInbox, 37);
    }

    /**
     * Returns the archived inbox of the given user.
     * 
     * @return a {@link String} representing all archived messages of the user whose
     *         username is given by {@code inputMap}.
     */
    public String getUserArchived(String username, List<TopicPresentable> nextNode) {
        List<String> archivedInbox = manager.archivedMessagesToString(username);
        updateSelectedMessageIDs(archivedInbox);
        return this.buildMessageList(archivedInbox, 37);
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
     * Returns the prompt for users to enter who a message is from.
     * 
     * @return a {@link String} "Please enter the username that the message is from"
     */
    public String getPromptForFrom(String username) {
        return "Please enter the username that the message is from";
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
     * Returns the prompt when the event id is invalid.
     * 
     * @return a {@link String} "This event id is invalid, please try again"
     */
    public String invalidEventId() {
        return "This event id is invalid, please try again";
    }

    /**
     * Returns a string prompting the user to enter an index.
     *
     * @param username a {@link String} of the current username
     * @return a {@link String} "Please enter message index"
     */
    public String promptForMessageIndex(String username) {
        return "Please enter message index";
    }

    /**
     * Returns a string prompting the user to press enter.
     *
     * @param username a {@link String} of the current username
     * @return a {@link String} "Press enter to continue"
     */
    public String promptForConfirmation(String username) {
        return "Press enter to continue";
    }

    /**
     * Returns a string that a message has successfully been moved.
     *
     * @param username a {@link String} of the current username
     * @return a {@link String} "Successfully moved!"
     */
    public String messageMoveConfirmation(String username, TopicPresentable nextNode){
        return "Successfully moved!";
    }

    /**
     * Returns a string that a message/inbox has successfully been deleted.
     *
     * @param username a {@link String} of the current username
     * @return a {@link String} "Successfully deleted!"
     */
    public String messageDeletedConfirmation(String username, TopicPresentable nextNode){
        return "Successfully deleted!";
    }


    /**
     * Updates the selected message ids of this {@link MessagePresenter}.
     *
     * @param selected a list of {@link String} of message ids
     */
    private void updateSelectedMessageIDs(List<String> selected) {
        this.selectedMessageIDs.clear();
        selected.forEach(s -> this.selectedMessageIDs.add(s.substring(0, 36)));
    }

    /**
     * Sets the selected message ids of this {@link MessagePresenter}.
     *
     * @param selectedMessageIDs a list of {@link String} of message ids
     */
    public void setSelectedMessageIDs(List<String> selectedMessageIDs) {
        this.selectedMessageIDs = selectedMessageIDs;
    }
}