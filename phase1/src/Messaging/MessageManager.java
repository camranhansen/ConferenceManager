package Messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public List<Message> retrieveUserInboxFor(String user, String from){
        return retrieveUserInbox(user).get(from);
    }
}
