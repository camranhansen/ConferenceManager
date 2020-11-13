package Messaging;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//TODO: add a constructor for inboxes that already exist

public class MessageManager {
    private HashMap<String, HashMap<String, List<Message>>> inboxes;

    public MessageManager() {
        inboxes = new HashMap<>();
    }

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
        }
    }

    public HashMap<String, List<Message>> retrieveUserInbox(String user) {
        if (!inboxes.containsKey(user)) {
            HashMap<String, List<Message>> hashmap = new HashMap<String, List<Message>>();
            inboxes.put(user, hashmap);
        }
        return inboxes.get(user);
    }

    public List<Message> retrieveUserInboxFor(String user, String from) {
        return retrieveUserInbox(user).get(from);
    }

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

    public List<String[]> getInboxAsArray(String user) {
        HashMap<String, List<Message>> inbox = inboxes.get(user);

        List<String[]> res = new ArrayList<>();
        for (List<Message> fromUser : inbox.values()) {
            String[] messageData = new String[4];
            for (Message message : fromUser) {
                messageData[0] = message.getSender();
                messageData[1] = message.getTimeSent().toString();
                messageData[2] = message.getContent();
                messageData[3] = message.getRecipients().toString().replaceAll("[\\[\\]]", "");
            }
            res.add(messageData);
        }
        return res;
    }

    public void putMessageFromArray(String user, String[] row) {
        HashMap<String, List<Message>> inbox = retrieveUserInbox(user);
        String sender = row[0];
        if (!inbox.containsKey(sender)) inbox.put(sender, new ArrayList<>());
        List<Message> senderMessage = inbox.get(sender);
        Message curMessage = new Message(sender, row[3].split(", "), row[2]);
        curMessage.setTimeSent(Instant.parse(row[1]));
    }

    public Iterator<String> getAllUsersWithInboxes() {

        return inboxes.keySet().iterator();
    }
}
