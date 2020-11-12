package Messaging;

import java.util.ArrayList;
import java.util.HashMap;
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
                userInbox.put(from, new ArrayList<>());
            }
            List<Message> messagesFrom = userInbox.get(from);
            messagesFrom.add(msg);
        }
    }

    public HashMap<String, List<Message>> retrieveUserInbox(String user) {
        if (!inboxes.containsKey(user)) {
            inboxes.put(user, new HashMap<>());
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

    public String wholeInboxToString(String username){
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
}
