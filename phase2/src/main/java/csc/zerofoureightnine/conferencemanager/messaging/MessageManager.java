package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;
import java.util.*;

//TODO: add a constructor for inboxes that already exist

public class MessageManager {


    private HashMap<String, HashMap<String, List<Message>>> inboxes;
//    private HashMap<String, HashMap<String, List<Message>>> readInboxes;
//    private HashMap<String, HashMap<String, List<Message>>> unreadInboxes;
//    private HashMap<String, List<Message>> archivedMessages;
    private PersistentMap<String, MessageData> messageData;

//    /**
//     * Instantiates the messageManager that stores four hashmaps
//     * {@code inboxes} stores a hashmap that maps recipients' usernames to a hashmap that maps the senders' usernames
//     * to a list of Message sent by the sender to the recipient.
//     * {@code readInboxes} stores a hashmap that maps recipients' usernames to a hashmap that maps the senders'
//     * usernames to a list of read Message from the sender to the recipient.
//     * {@code unreadInboxes} stores a hashmap that maps recipients' usernames to a hashmap that maps the senders'
//     * usernames to a list of unread Message from the sender to the recipient.
//     * {@code archivedMessages} stores a hashmap that maps the recipients' usernames to a list of their archived
//     * Messages.
//     */

    /**
     * Instantiates the messageManager
     * @param messageData hashmap storing ids as keys and their associating messageDatas as values
     */
    public MessageManager(PersistentMap<String, MessageData> messageData) {
        inboxes = new HashMap<>();
//        readInboxes = new HashMap<>();
//        unreadInboxes = new HashMap<>();
//        archivedMessages = new HashMap<>();
        this.messageData = messageData;
    }

//
//    public List<MessageData> getMessageDataOf(String username){
//        this.messageData.beginInteraction();
//        return this.messageData.loadInCollection("recipients", username);
//    }

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
        //Message msg = new Message(from, to, content);
        MessageData md = new MessageData();
        md.setSender(from);
        md.setContent(content);
        md.addRecipients(to);
        String id = UUID.randomUUID().toString();
        this.messageData.save(id, md);
        return this.messageData.load(id);
//        for (int i = 0; i < to.length; i++) {
//            HashMap<String, List<Message>> userInbox = retrieveUserInbox(to[i]);
//            HashMap<String, List<Message>> unreadUserInbox = getUnreadInbox(to[i]);
//
//            if (!userInbox.containsKey(from)) {
//                List<Message> message = new ArrayList<>();
//                userInbox.put(from, message);
//            }
//            if(!unreadUserInbox.containsKey(from)){
//                List<Message> m = new ArrayList<>();
//                unreadUserInbox.put(from, m);
//            }
//            List<Message> messagesFrom = userInbox.get(from);
//            messagesFrom.add(msg);
//            unreadUserInbox.get(from).add(msg);
//
//        }
    }


    /**
     * Returns the inbox of the given user.
     * @param user username of the user whose inbox will be retrieved
     * @return a hashmap that maps the username of sender to a list of Message sent to the given user
     */
    public HashMap<String, List<Message>> retrieveUserInbox(String user) {
//        if (!inboxes.containsKey(user)) {
//            HashMap<String, List<Message>> hashmap = new HashMap<>();
//            inboxes.put(user, hashmap);
//        }

        HashMap<String, List<Message>> inbox = new HashMap<>();
        List<MessageData> md = this.messageData.loadInCollection("recipients", user);
        List<String> senders = new ArrayList<>();
        for (MessageData message: md){
            if(!senders.contains(message.getSender())){
                senders.add(message.getSender());
            }
        }
        for (String sender: senders){
            List<Message> messages = this.retrieveUserInboxFor(user, sender);
            inbox.put(sender, messages);
        }
        return inbox;
    }


    /**
     * Returns the read inbox of the given user.
     * @param username username of the user whose read inbox will be retrieved
     * @return a hashmap that maps the username of the sender to a list of read Message sent by the sender to the
     * given user
     */

    public HashMap<String, List<Message>> getReadInbox(String username) {
//        if (!readInboxes.containsKey(username)) {
//            HashMap<String, List<Message>> hashmap = new HashMap<>();
//            readInboxes.put(username, hashmap);
//        }
//        return readInboxes.get(username);
//    }
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

    public List<Message> getReadInboxFrom(String username, String from) {
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

    /**
     * Returns the unread inbox of the given user.
     * @param username username of the user whose unread inbox will be retrieved
     * @return a hashmap that maps the username of the sender to a list of unread Message sent by the sender to the
     * given user
     */

    public HashMap<String, List<Message>> getUnreadInbox(String username){
//        if(!unreadInboxes.containsKey(username)){
//            HashMap<String, List<Message>> hashmap = new HashMap<>();
//            unreadInboxes.put(username, hashmap);
//        }
//        return unreadInboxes.get(username);
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


    /**
     * Returns the unread inbox of the user from a specific sender.
     * @param username username of the user
     * @param from username of the sender
     * @return a list of unread Message that the sender has sent to the user
     */
    public List<Message> getUnreadFrom(String username, String from){
    //    return getUnreadInbox(username).get(from);
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

    //ADDED:
    /**
     * Returns the archived inbox of the given user.
     * @param username username of the user
     * @return a list of archived Message of the given user
     */
    public List<Message> getArchivedInbox(String username) {
//        if(!archivedMessages.containsKey(username)){
//            List<Message> messages = new ArrayList<>();
//            archivedMessages.put(username, messages);
//        }
//        return archivedMessages.get(username);
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

    /**
     * Returns the unread inbox of the user from a specific sender.
     * @param user Username of the recipient.
     * @param from Username of the sender.
     * @return A list of Message that the sender has sent to the user.
     */
    // only retrieveUserInboxFor move read message to unread
    public List<Message> retrieveUserInboxFor(String user, String from) {
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
        HashMap<String, List<Message>> inboxes = retrieveUserInbox(username);
        if (!inboxes.containsKey(from)){
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
                if ((j > i)) {
                    if (messages.get(i).getTimeSent().compareTo(messages.get(j).getTimeSent()) > 0) {
                        Collections.swap(messages, i, j);
                    }
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
        HashMap<String, List<Message>> inbox = retrieveUserInbox(username);
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
        HashMap<String, List<Message>> unread = getUnreadInbox(username);
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
        HashMap<String, List<Message>> unread = getUnreadInbox(username);
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
     * Returns a list of the information of all message sent to the user including sender, sent time, content, and all
     * recipients.
     * @param user Username of the recipient.
     * @return A list of String[] that stores all information of all the messages sent to the given user. Each String[]
     * contains the sender's username at index 0, sent time at index 1, content at index 2, and String[] of recipients'
     * usernames at index 3, of a single message sent to the given user.
     */
    public List<String[]> getInboxAsArray(String user) {
        HashMap<String, List<Message>> inbox = inboxes.get(user);

        List<String[]> res = new ArrayList<>();
        for (List<Message> fromUser : inbox.values()) {
            String[] messageData = new String[4];
            for (Message message : fromUser) {
                messageData[0] = message.getSender();
                messageData[1] = message.getTimeSent().toString();
                messageData[2] = message.getContent();
                messageData[3] = Arrays.toString(message.getRecipients()).replaceAll("[\\[\\]]", "");
            }
            res.add(messageData);
        }
        return res;
    }

    /**
     * Create a new Message sent to the given user from a row storing all information about that Message. Put the
     * message into the user inbox.
     * @param user Username of the recipient.
     * @param row A String[] with sender's username at index 0, sent time at index 1, message content at index 2, and
     * String[] of recipients' usernames at index 3.
     */
    public void putMessageFromArray(String user, String[] row) {
        HashMap<String, List<Message>> inbox = retrieveUserInbox(user);
        String sender = row[0];
        Message curMessage = new Message(sender, row[3].split(", "), row[2]);
        curMessage.setTimeSent(Instant.parse(row[1]));
        if (!inbox.containsKey(sender)) {
            List<Message> list = new ArrayList<>();
            list.add(curMessage);
            inbox.put(sender, list);
        } else{
            List<Message> senderMessage = inbox.get(sender);
            senderMessage.add(curMessage);
        }
    }

    /**
     * Returns an iterator over all csc.zerofoureightnine.conferencemanager.users of the inbox.
     * @return An iterator that iterates through the keys, which represent the username of the recipients, in a hashmap,
     * which is the inboxes.
     */
    public Iterator<String> getAllUsersWithInboxes() {

        return inboxes.keySet().iterator();
    }

}
