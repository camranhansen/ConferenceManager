package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;
import java.util.*;


public class MessageManager {


    private PersistentMap<String, MessageData> messageData;

    /**
     * Instantiates the messageManager
     * @param messageData @see PersistentMap storing ids as keys and their associating messageDatas as values
     */
    public MessageManager(PersistentMap<String, MessageData> messageData) {
        this.messageData = messageData;
    }

    /**
     * Returns a sqlMap that maps the message ids to the messageData objects.
     * @return hashmap storing ids as keys and their associating messageDatas as values
     */
    public PersistentMap<String, MessageData> getMessageData(){
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
        String id = UUID.randomUUID().toString();
        this.messageData.save(id, md);
        return this.messageData.load(id);
    }

    private Map<String, List<Message>> retrieveUserInbox(String user) {

        HashMap<String, List<Message>> inbox = new HashMap<>();
        messageData.beginInteraction();
        List<MessageData> md = this.messageData.loadInCollection("recipients", user);
        List<String> senders = new ArrayList<>();
        for (MessageData message: md) {
            if(!senders.contains(message.getSender())){
                senders.add(message.getSender());
            }
            message.getRead().add(user);
        }
        messageData.endInteraction();
        for (String sender: senders){
            List<Message> messages = this.retrieveUserInboxFor(user, sender);
            inbox.put(sender, messages);
        }
        return inbox;
    }

    /**
     * Return the size of the retrieve inbox.
     * @param name username of the user whose inbox will be retrieved
     * @return a integer that shows the size of retrieve inbox.
     */
    public int getRetrieveInboxSize(String name){
        int num = 0;
        for(String key : this.retrieveUserInbox(name).keySet()){
            num += this.retrieveUserInbox(name).get(key).size();
        }
        return num;
    }

    /**
     * Return the size of the unread inbox.
     * @param name username of the user whose inbox will be retrieved
     * @return a integer that shows the size of unread inbox.
     */
    public int getUnreadInboxSize(String name){
        int num = 0;
        for(String key : this.getUnreadInbox(name).keySet()){
            num += this.getUnreadInbox(name).get(key).size();
        }
        return num;
    }

    public int getReadInboxSize(String name){
        int num = 0;
        for(String key : this.getReadInbox(name).keySet()){
            num += this.getReadInbox(name).get(key).size();
        }
        return num;
    }

    private Map<String, List<Message>> getReadInbox(String username) {
        HashMap<String, List<Message>> read = new HashMap<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", username);
        List<String> senders = new ArrayList<>();
        for (MessageData message: md){
            if(!senders.contains(message.getSender())){
                senders.add(message.getSender());
            }
        }
        for (String sender: senders) {
            List<Message> messages = this.getReadInboxFrom(username, sender);
            if (!messages.isEmpty()) {
                read.put(sender, messages);
            }
        }
        return read;
    }

    private List<Message> getReadInboxFrom(String username, String from) {
        List<Message> messages = new ArrayList<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", username);
        for (MessageData m : md) {
            if (m.getSender().equals(from) && m.getRead().contains(username)) {
                String content = m.getContent();
                String sender = m.getSender();
                Instant time = m.getTimeSent();
                Set<String> recipients = m.getRecipients();
                String[] r = recipients.toArray(new String[0]);
                Message message = new Message(sender, r, content);
                m.setTimeSent(time);
                messages.add(message);
            }
        }
        return messages;
    }

    private Map<String, List<Message>> getUnreadInbox(String username){
        HashMap<String, List<Message>> unread = new HashMap<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", username);
        List<String> senders = new ArrayList<>();
        for (MessageData message: md){
            if(!senders.contains(message.getSender())){
                senders.add(message.getSender());
            }
        }
        for (String sender: senders){
            List<Message> messages = this.getUnreadFrom(username, sender);
            if (!messages.isEmpty()) {
                unread.put(sender, messages);
            }
        }
        return unread;
    }

    private List<Message> getUnreadFrom(String username, String from){
        List<Message> messages = new ArrayList<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", username);
        for (MessageData m : md) {
            if (m.getSender().equals(from) && !m.getRead().contains(username)) {
                String content = m.getContent();
                String sender = m.getSender();
                Instant time = m.getTimeSent();
                Set<String> recipients = m.getRecipients();
                String[] r = recipients.toArray(new String[0]);
                Message message = new Message(sender, r, content);
                m.setTimeSent(time);
                messages.add(message);
            }
        }
        return messages;
    }

    private List<Message> getArchivedInbox(String username) {
        List<Message> archived = new ArrayList<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", username);
        for (MessageData m: md){
            if(m.getArchived().contains(username)){
                String content = m.getContent();
                String sender = m.getSender();
                Instant time = m.getTimeSent();
                Set<String> recipients = m.getRecipients();
                String[] r = new String[]{String.valueOf(recipients)};
                Message message = new Message(sender, r, content);
                m.setTimeSent(time);
                archived.add(message);
            }
        }
        return archived;
    }

    private List<Message> retrieveUserInboxFor(String user, String from) {
        List<Message> messages = new ArrayList<>();
        this.messageData.beginInteraction();
        List<MessageData> md = this.messageData.loadInCollection("recipients", user);

        for (MessageData message : md) {
            if (message.getSender().equals(from)){
                String content = message.getContent();
                String sender = message.getSender();
                Instant time = message.getTimeSent();
                Set<String> recipients = message.getRecipients();
                String[] r = recipients.toArray(new String[0]);
                Message m = new Message(sender, r, content);
                m.setTimeSent(time);
                messages.add(m);

                if(!message.getRead().contains(user)){
                    message.addToRead(user);
                }

            }
        }
        this.messageData.endInteraction();
        return messages;
    }

        /**
         * Returns the String representation of all the messages sent from one user to another user. If the given sender
         * bas never sent to this user before, returns "You have no messages from this username.".
         * @param username Username of the recipient.
         * @param from Username of the sender.
         * @return A string of message content sent from one user to another user, or
         * "You have no messages from this username." if this user's inbox doesn't contain the sender's username as a key.
         */
    public String singleInboxToString(String username, String from){
        Map<String, List<Message>> usersInbox = retrieveUserInbox(username);
        if (!usersInbox.containsKey(from)){
            return "You have no messages from this username.";
        }
        List<Message> inboxFrom = retrieveUserInboxFor(username, from);
        List<Message> inboxFromSortByTime = this.sortByTime(inboxFrom);
        StringBuilder inbox = new StringBuilder();
        for (Message message: inboxFromSortByTime){
            inbox.append(from);
            inbox.append(": ");
            inbox.append(message.getContent());
            inbox.append(", ");
            inbox.append(message.getTimeSent().toString());
            inbox.append("\n");
        }
        return inbox.toString();
    }

    private List<Message> sortByTime(List<Message> messages) {
        for (int i = 0; i < messages.size(); i++) {
            for (int j = 0; j < messages.size(); j++) {
                if ((j > i) && messages.get(i).getTimeSent().compareTo(messages.get(j).getTimeSent()) > 0) {
                    Collections.swap(messages, i, j);
                }
            }
        }
        return messages;
    }

    /**
     * Returns the string representation of all the message sent to the user. If no one has sent to the given user before
     * , returns "You have no messages".
     * @param username Username of the user whose inbox will be retrieved.
     * @return A string of all the message content sent to the user sort by the username of the sender, or
     * "You have no messages" if this user's inbox is empty.
     */
    public String wholeInboxToString(String username) {
        StringBuilder allMessages = new StringBuilder();
        Map<String, List<Message>> inbox = retrieveUserInbox(username);
        if (inbox.isEmpty()){
            return "You have no messages";
        }
        String[] from = inbox.keySet().toArray(new String[0]);
        for (String other:from){
            allMessages.append(singleInboxToString(username, other));
        }
        return allMessages.toString();
    }

    /**
     * Returns all archived Message of the given user. If the given user has no archived Message, returns "You have no
     * archived messages".
     * @param username username of the user
     * @return A string representation of all the archived Message of the given user, including information about the
     * content and the username of the sender, or "You have no archived messages" if this user's archived Message inbox
     * is empty.
     */
    public String archivedMessagesToString(String username) {
        if(getArchivedInbox(username).isEmpty()){
            return "You have no archived messages";
        }
        List<Message> message = getArchivedInbox(username);
        List<Message> messages = this.sortByTime(message);
        StringBuilder string = new StringBuilder();
        for (Message m: messages){
            string.append(m.getSender());
            string.append(": ");
            string.append(m.getContent());
            string.append(", ");
            string.append(m.getTimeSent().toString());
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * Returns all unread Message of the given user. If this user has no Message in unreadInbox, returns
     * "You have no unread messages".
     * @param username username of the user
     * @return a string representation of all the unread Message of the give user, including information about the
     * content and the username of the sender, or "You have no unread messages" if this user's unreadInbox in empty.
     */
    public String unreadInboxToString(String username){
        if (getUnreadInbox(username).isEmpty()){
            return "You have no unread messages";
        }
        Map<String, List<Message>> unread = getUnreadInbox(username);
        StringBuilder string = new StringBuilder();
        for (String from: unread.keySet()){
            string.append(singleUnreadInboxToString(username, from));
            string.append("\n");
        }
        return string.toString();
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
    public String singleUnreadInboxToString(String username, String from){
        Map<String, List<Message>> unread = getUnreadInbox(username);
        if (!unread.containsKey(from)){
            return "You have no unread messages from "+from;
        }
        List<Message> unreadFrom1 = getUnreadFrom(username, from);
        List<Message> unreadFrom = this.sortByTime(unreadFrom1);
        StringBuilder inbox = new StringBuilder();
        for (Message message: unreadFrom){
            inbox.append(from);
            inbox.append(": ");
            inbox.append(message.getContent());
            inbox.append(", ");
            inbox.append(message.getTimeSent().toString());
            inbox.append("\n");
        }
        return inbox.toString();
    }

    /**
     * Returns true if the content, sender and time sent was been a message this username has received.
     *
     * @param username the current user
     * @param from the sender of a message
     * @param time the time a message was sent
     * @param content the content in a message
     * @return a boolean stating whether or a message exists
     */
    public boolean messageExists(String username, String from, String time, String content) {
        List<MessageData> md = getMessageData().loadInCollection("recipients", username);
        for (MessageData m : md) {
            if (from.equals(m.getSender()) && content.equals(m.getContent()) && time.equals(m.getTimeSent().toString())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets the total number of messages
     *
     * @return the total number of messages
     */
    public Integer getTotalMessages() {
        return messageData.size();
    }
}
