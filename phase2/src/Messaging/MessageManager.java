package Messaging;

import java.time.Instant;
import java.util.*;

//TODO: add a constructor for inboxes that already exist

public class MessageManager {
    /**
     * inboxes stores a hashmap that maps recipients' usernames to a hashmap that maps the senders' usernames to a list of
     * Message sent by the sender to the recipient.
     */
    private HashMap<String, HashMap<String, List<Message>>> inboxes;
    private HashMap<String, HashMap<String, List<Message>>> readInboxes;
    private HashMap<String, HashMap<String, List<Message>>> unreadInboxes;
    private HashMap<String, List<Message>> archivedMessages;

    /**
     * Instantiates messageManager
     */
    public MessageManager() {
        inboxes = new HashMap<>();
        readInboxes = new HashMap<>();
        unreadInboxes = new HashMap<>();
        archivedMessages = new HashMap<>();
    }

    /**
     * User sends a message to one or more users. This message is stored in inboxes.
     * @param from Username of the sender of this message
     * @param content Content of the message
     * @param to Usernames of one or a list of recipients to this message
     */
    public void sendMessage(String from, String content, String... to) {
        Message msg = new Message(from, to, content);
        for (int i = 0; i < to.length; i++) {
            HashMap<String, List<Message>> userInbox = retrieveUserInbox(to[i]);
            if (!userInbox.containsKey(from)) {
                ArrayList<Message> message = new ArrayList<>();
                userInbox.put(from, message);
            }
            List<Message> messagesFrom = userInbox.get(from);
            messagesFrom.add(msg);
            this.moveToUnread(to[i], from, msg);
        }
    }

    /**
     * Returns the inbox of the given user.
     * @param user Username of the user whose inbox will be retrieved.
     * @return A hashmap that maps the username of sender to a list of Message sent to the given user.
     */
    public HashMap<String, List<Message>> retrieveUserInbox(String user) {
        if (!inboxes.containsKey(user)) {
            HashMap<String, List<Message>> hashmap = new HashMap<>();
            inboxes.put(user, hashmap);
        }
        return inboxes.get(user);
    }

    /**
     * Returns the messages sent from one user to another user.
     * @param user Username of the recipient.
     * @param from Username of the sender.
     * @return A list of Message.
     */
    // only retrieveUserInboxFor move read message to unread
    public List<Message> retrieveUserInboxFor(String user, String from) {
        List<Message> messages = retrieveUserInbox(user).get(from);
        for(Message m: messages){
            this.moveToRead(user, from, m);
        }
        return retrieveUserInbox(user).get(from);
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
        StringBuilder inbox = new StringBuilder(from);
        inbox.append(": ");
        for (Message message: inboxFrom){
            inbox.append(message.getContent());
            inbox.append(", ");
        }
        return inbox.toString();
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
            allMessages.append("\n");
        }
        return allMessages.toString();
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
            ArrayList<Message> list = new ArrayList<>();
            list.add(curMessage);
            inbox.put(sender, list);
        } else{
            List<Message> senderMessage = inbox.get(sender);
            senderMessage.add(curMessage);
        }
    }

    /**
     * Returns an iterator over all users of the inbox.
     * @return An iterator that iterates through the keys, which represent the username of the recipients, in a hashmap,
     * which is the inboxes.
     */
    public Iterator<String> getAllUsersWithInboxes() {

        return inboxes.keySet().iterator();
    }

    public void moveToArchived(String username, Message message){
        if(!archivedMessages.containsKey(username)){
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            archivedMessages.put(username, messages);
        }else{
            List<Message> messages = archivedMessages.get(username);
            messages.add(message);
        }
    }

    public List<Message> viewArchivedMessages(String username){
        if(!archivedMessages.containsKey(username)){
            return new ArrayList<Message>();
        }else{
            return archivedMessages.get(username);
        }
    }

    public void deleteEntireInbox(String username){
        inboxes.remove(username);
        unreadInboxes.remove(username);
        readInboxes.remove(username);
        archivedMessages.remove(username);
    }

    public void deleteEntireConvo(String username, String from){
        if(inboxes.containsKey(username)){
            inboxes.get(username).remove(from);
        }
        if(unreadInboxes.containsKey(username)){
            unreadInboxes.get(username).remove(from);
        }
        if(readInboxes.containsKey(username)){
            readInboxes.get(username).remove(from);
        }
        if(archivedMessages.containsKey(username)){
            List<Message> message = archivedMessages.get(username);
            message.removeIf(m -> m.getSender().equals(from));
        }

    }

    public void deleteOneMessage(String username, String from, Message message){
        if(inboxes.containsKey(username)){
            HashMap<String, List<Message>> hashmap = inboxes.get(username);
            if(hashmap.containsKey(from)){
                hashmap.get(from).remove(message);
            }
        }
        if(unreadInboxes.containsKey(username)){
            HashMap<String, List<Message>> hashmap = unreadInboxes.get(username);
            if(hashmap.containsKey((from))){
                hashmap.get(from).remove(message);
            }
        }
        if(readInboxes.containsKey(username)){
            HashMap<String, List<Message>> hashmap = readInboxes.get(username);
            if(hashmap.containsKey((from))){
                hashmap.get(from).remove(message);
            }
        }
        if(archivedMessages.containsKey(username)){
            archivedMessages.get(username).remove(message);
        }
    }

    public void moveToRead(String username, String from, Message message){
        if(!readInboxes.containsKey(username)){
            HashMap<String, List<Message>> m = new HashMap<>();
            readInboxes.put(username, m);
        }
        HashMap<String, List<Message>> m1 = readInboxes.get(username);
        if (!m1.containsKey(from)){
            List<Message> m3 = new ArrayList<>();
            m3.add(message);
            m1.put(from, m3);
        }else{
            m1.get(from).add(message);
        }
        if (unreadInboxes.containsKey(username)){
            HashMap<String, List<Message>> hashmap = unreadInboxes.get(username);
            if (hashmap.containsKey(from)) {
                hashmap.get(from).remove(message);
            }
        }
    }


    public void moveToUnread(String username, String from, Message message){
        if(!unreadInboxes.containsKey(username)){
            HashMap<String, List<Message>> m = new HashMap<>();
            unreadInboxes.put(username, m);
        }
        HashMap<String, List<Message>> m1 = unreadInboxes.get(username);
        if (!m1.containsKey(from)){
            List<Message> m3 = new ArrayList<>();
            m3.add(message);
            m1.put(from, m3);
        }else{
            m1.get(from).add(message);
        }
        if (readInboxes.containsKey(username)){
            HashMap<String, List<Message>> hashmap = readInboxes.get(username);
            if (hashmap.containsKey(from)) {
                hashmap.get(from).remove(message);
            }
        }
    }

    public List<Message> viewUnreadFrom(String username, String from){
        return unreadInboxes.get(username).get(from);
    }

    public HashMap<String, List<Message>> viewUnread(String username){
        return unreadInboxes.get(username);
    }
}
