package csc.zerofoureightnine.conferencemanager.messaging;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;


public class MessageManager {


    private PersistentMap<UUID, MessageData> messageData;

    /**
     * Instantiates the messageManager
     * @param messageData @see PersistentMap storing ids as keys and their associating messageDatas as values
     */
    public MessageManager(PersistentMap<UUID, MessageData> messageData) {
        this.messageData = messageData;
    }

    /**
     * Returns a sqlMap that maps the message ids to the messageData objects.
     * @return hashmap storing ids as keys and their associating messageDatas as values
     */
    public PersistentMap<UUID, MessageData> getMessageData(){
        return this.messageData;
    }

    /**
     * User sends a message to one or more csc.zerofoureightnine.conferencemanager.users.
     * @param from username of the sender of this message
     * @param content content of the message
     * @param to usernames of one or a list of recipients to this message
     */
    public MessageData sendMessage(String from, String content, String... to) {
        return sendMessage(from, content, Arrays.asList(to));
    }

    /**
     * User sends a message to one or more csc.zerofoureightnine.conferencemanager.users.
     * @param from username of the sender of this message
     * @param content content of the message
     * @param to usernames of one or a list of recipients to this message
     */
    public MessageData sendMessage(String from, String content, Collection<String> to) {
        MessageData md = new MessageData();
        md.setSender(from);
        md.setContent(content);
        md.addRecipients(to);
        md.setTimeSent(Instant.now());
        UUID id = UUID.randomUUID();
        this.messageData.save(id, md);
        return this.messageData.load(id);
    }

    /**
     * Return the number of messages the user has.
     * @param name username of the user whose inbox will be retrieved
     * @return a integer that shows the size of retrieve inbox.
     */
    public int getInboxSize(String name) {
        return messageData.loadInCollection("recipients", name).size();
    }

    /**
     * Return the size of the unread inbox.
     * @param name username of the user whose inbox will be retrieved
     * @return a integer that shows the size of unread inbox.
     */
    public int getUnreadInboxSize(String name){
        return getUnreadInbox(name).size();
    }

    /**
     * Return the size of the read inbox.
     * @param name username of the user whose inbox will be retrieved
     * @return a integer that shows the size of the read inbox.
     */
    public int getReadInboxSize(String name){
        List<MessageData> messages = messageData.loadInCollection("recipients", name);
        int read = 0;
        for (MessageData messageData : messages) {
            if (messageData.getRead().contains(name)) {
                read++;
            }
        }
        return read;
    }

    
    private List<MessageData> getUnreadFrom(String username, String from){
        List<MessageData> messages = getUnreadInbox(username);
        List<MessageData> res = new ArrayList<>();
        for (MessageData messageData : messages) {
            if (messageData.getSender().equals(from)) {
                res.add(messageData);
            }
        }
        return messages;
    }


    private List<MessageData> getUnreadInbox(String username) {
        List<MessageData> messages = messageData.loadInCollection("recipients", username);
        List<MessageData> res = new ArrayList<>();
        for (MessageData messageData : messages) {
            if (!(messageData.getRead().contains(username))) {
                res.add(messageData);
            }
        }
        return res;
    }

    private List<MessageData> getReadInbox(String username){
        List<MessageData> messages = messageData.loadInCollection("recipients", username);
        List<MessageData> res = new ArrayList<>();
        for (MessageData messageData : messages) {
            if (messageData.getRead().contains(username)) {
                res.add(messageData);
            }
        }
        return res;
    }

    private List<MessageData> getArchivedInbox(String username) {
        List<MessageData> archived = new ArrayList<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", username);
        for (MessageData m: md){
            if(m.getArchived().contains(username)){
                archived.add(m);
            }
        }
        return archived;
    }

    private List<MessageData> retrieveUserInboxFor(String user, String from) {
        List<MessageData> messages = new ArrayList<>();
        this.messageData.beginInteraction();
        List<MessageData> md = this.messageData.loadInCollection("recipients", user);

        for (MessageData message : md) {
            if (message.getSender().equals(from)){
                messages.add(message);

                if(!message.getRead().contains(user)){
                    message.addToRead(user);
                }
            }
        }
        this.messageData.endInteraction();
        return messages;
    }

     /**
     * Returns a list of strings representing all the messages sent from one user to another user.
     * @param username Username of the recipient.
     * @param from Username of the sender.
     * @return A {@link List} containing messages sent by {@code from} to {@code username}. The list is sorted by the time they were received.
     */
    public List<String> singleInboxToString(String username, String from){
        List<MessageData> fromUser = messageData.loadInCollection("recipients", username);
        List<MessageData> single = new ArrayList<>();
        for (MessageData messageData : fromUser) {
            if (messageData.getSender().equals(from)) {
                single.add(messageData);
            }
        }
        sortByTime(single);
        List<String> res = new ArrayList<>();
        single.forEach(m -> res.add(m.toString()));
        return res;
    }

    private void sortByTime(List<MessageData> messages) {
        messages.sort((a, b) -> Math.toIntExact(a.getTimeSent().toEpochMilli() - b.getTimeSent().toEpochMilli()));
    }

    /**
     * Returns a list of strings representing of all the message sent to the user.
     * @param username Username of the user whose inbox will be retrieved.
     * @return A {@link List} containing all messages received by this user. The list is sorted by the time they were received.
     */
    public List<String> wholeInboxToString(String username) {
        List<MessageData> inbox = messageData.loadInCollection("recipients", username);
        sortByTime(inbox);
        List<String> res = new ArrayList<>();
        inbox.forEach(m -> res.add(m.toString()));
        return res;
    }

    public List<String> readInboxToString(String username){
        List<MessageData> inbox = getReadInbox(username);
        List<String> res = new ArrayList<>();
        inbox.forEach(m -> res.add(m.toString()));
        return res;
    
    }

    /**
     * Returns a list of archived Message of the given user. If the given user has no archived Message, returns "You have no
     * archived messages".
     * @param username username of the user
     * @return A {@link List} of archived messages received by this user, or "You have no archived messages" if this user's archived Message inbox
     * is empty. The list is sorted by the time they were received.
     */
    public List<String> archivedMessagesToString(String username) {
        List<MessageData> message = getArchivedInbox(username);
        sortByTime(message);
        List<String> res = new ArrayList<>();
        message.forEach(m -> res.add(m.toString()));
        return res;
    }

    /**
     * Returns all unread Message of the given user. If this user has no Message in unreadInbox, returns
     * "You have no unread messages".
     * @param username username of the user
     * @return a string representation of all the unread Message of the give user, including information about the
     * content and the username of the sender, or "You have no unread messages" if this user's unreadInbox in empty.
     */
    public List<String> unreadInboxToString(String username){
        List<MessageData> unread = getUnreadInbox(username);
        sortByTime(unread);
        List<String> res = new ArrayList<>();
        unread.forEach(m -> res.add(m.toString()));
        return res;
    }

    /**
     * Returns all unread Message of the given user from the given sender. If this user has no unread Message from the
     * given sender, returns "You have no unread messages from sender".
     * @param username username of the user
     * @param from username of the sender
     * @return a string representation of all unread Message of the user from the sender, including the sender's
     * username and message contents, or "You have no unread messages from sender" if this user's unreadInbox from the
     * sender is empty.
     */
    public List<String> singleUnreadInboxToString(String username, String from){
        List<MessageData> fromUser = messageData.loadInCollection("recipients", username);
        List<MessageData> single = new ArrayList<>();
        for (MessageData messageData : fromUser) {
            if (messageData.getSender().equals(from) && !messageData.getRead().contains(username)) {
                single.add(messageData);
            }
        }
        sortByTime(single);
        List<String> res = new ArrayList<>();
        single.forEach(messageData -> res.add(messageData.toString()));
        return res;
    }

//    /**
//     * Returns true if the content, sender and time sent was been a message this username has received.
//     *
//     * @param username the current user
//     * @param from the sender of a message
//     * @param time the time a message was sent
//     * @param content the content in a message
//     * @return a boolean stating whether or a message exists
//     */
//    public boolean messageExists(String username, String from, String time, String content) {
//        List<MessageData> md = getMessageData().loadInCollection("recipients", username);
//        for (MessageData m : md) {
//            if (from.equals(m.getSender()) && content.equals(m.getContent()) && time.equals(m.getTimeSent().toString())) {
//                return true;
//            }
//        }
//        return false;
//    }


    /**
     * Gets the total number of messages
     *
     * @return the total number of messages
     */
    public Integer getTotalMessages() {
        return messageData.size();
    }
}
