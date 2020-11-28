package csc.zerofoureightnine.conferencemanager.messaging;

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
     * User sends a message to one or more csc.zerofoureightnine.conferencemanager.users. This message is stored in inboxes.
     * @param from Username of the sender of this message
     * @param content Content of the message
     * @param to Usernames of one or a list of recipients to this message
     */
    public void sendMessage(String from, String content, String... to) {
        Message msg = new Message(from, to, content);
        for (int i = 0; i < to.length; i++) {
            HashMap<String, List<Message>> userInbox = retrieveUserInbox(to[i]);
            HashMap<String, List<Message>> unreadUserInbox = getUnreadInbox(to[i]);
            if (!userInbox.containsKey(from)) {
                ArrayList<Message> message = new ArrayList<>();
                userInbox.put(from, message);
            }
            if(!unreadUserInbox.containsKey(from)){
                unreadUserInbox.put(from, new ArrayList<Message>());
            }
            List<Message> messagesFrom = userInbox.get(from);
            messagesFrom.add(msg);
            unreadUserInbox.get(from).add(msg);

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

    //ADDED:
    public HashMap<String, List<Message>> getReadInbox(String username) {
        if (!readInboxes.containsKey(username)) {
            HashMap<String, List<Message>> hashmap = new HashMap<>();
            readInboxes.put(username, hashmap);
        }
        return readInboxes.get(username);
    }

    public HashMap<String, List<Message>> getUnreadInbox(String username){
        if(!unreadInboxes.containsKey(username)){
            HashMap<String, List<Message>> hashmap = new HashMap<>();
            unreadInboxes.put(username, hashmap);
        }
        return unreadInboxes.get(username);
    }

    public List<Message> getUnreadFrom(String username, String from){
        return getUnreadInbox(username).get(from);
    }

    //ADDED:
    public List<Message> getArchivedInbox(String username) {
        if(!archivedMessages.containsKey(username)){
            List<Message> messages = new ArrayList<>();
            archivedMessages.put(username, messages);
        }
        return archivedMessages.get(username);
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
        HashMap<String, List<Message>> readInbox = getReadInbox(user);
        HashMap<String, List<Message>> unreadInbox = getUnreadInbox(user);
        if (readInbox.containsKey(from)){
            for(Message m: messages) {
                if(!readInbox.get(from).contains(m)){
                    readInbox.get(from).add(m);
                }
            }
        }else{
            readInbox.put(from, messages);
        }
        if(unreadInbox.containsKey(from)){
            for (Message m: messages){
                unreadInbox.get(from).remove(m);
            }
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


    public String archivedMessagesToString(String username) {
        if(getArchivedInbox(username).isEmpty()){
            return "You have no archived messages";
        }
        List<Message> messages = getArchivedInbox(username);
        StringBuilder string = new StringBuilder();
        for (Message m: messages){
            string.append(m.getSender());
            string.append(": ");
            string.append(m.getContent());
            string.append("\n");
        }
        return string.toString();
    }


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


    public String singleUnreadInboxToString(String username, String from){
        HashMap<String, List<Message>> unread = getUnreadInbox(username);
        if (!unread.containsKey(from)){
            return "You have no unread messages from "+from;
        }
        List<Message> unreadFrom = getUnreadFrom(username, from);
        StringBuilder string = new StringBuilder(from);
        string.append(": ");
        for (Message message: unreadFrom){
            string.append(message.getContent());
            string.append(", ");
        }
        return string.substring(0, string.length()-2);
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
     * Returns an iterator over all csc.zerofoureightnine.conferencemanager.users of the inbox.
     * @return An iterator that iterates through the keys, which represent the username of the recipients, in a hashmap,
     * which is the inboxes.
     */
    public Iterator<String> getAllUsersWithInboxes() {

        return inboxes.keySet().iterator();
    }

}
