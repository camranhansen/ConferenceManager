package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageMover {
    private HashMap<String, List<Message>> unreadInbox;
    private HashMap<String, List<Message>> readInbox;
    private List<Message> archivedInbox;
    private HashMap<String, List<Message>> inbox;


    /**
     * Instantiates MessageMover
     * @param messageManager MessageManager class
     * @param username username of the user accessing MessageMover
     */
    public MessageMover(MessageManager messageManager, String username){
        this.unreadInbox = messageManager.getUnreadInbox(username);
        this.readInbox = messageManager.getReadInbox(username);
        this.archivedInbox = messageManager.getArchivedInbox(username);
        this.inbox = messageManager.retrieveUserInbox(username);
    }


    /**
     * Check if the message is in user's read inbox, if so, move the given message from user's read inbox to unread
     * inbox.
     * @param message Message object
     */
    public void moveReadToUnread(Message message){
        String from = message.getSender();
        List<Message> messages = new ArrayList<>();
        for(Message m: readInbox.get(from)){
            if (m.getContent().equals(message.getContent()) && m.getTimeSent().toString().substring(0,19).equals(message.getTimeSent().toString().substring(0,19))){
                messages.add(m);
            }
        }
        for (Message m: messages){
            readInbox.get(from).remove(m);
        }
        if(!unreadInbox.containsKey(from)) {
            List<Message> m = new ArrayList<>();
            unreadInbox.put(from, m);
        } else{
            List<Message> m = unreadInbox.get(from);
            m.add(message);
        }
    }


    /**
     * Check if the message is in user's unread inbox, if so, move the given message from user's unread inbox to read
     * inbox.
     * @param message Message object
     */
    public void moveUnreadToRead(Message message){
        String from = message.getSender();
        List<Message> messages1 = new ArrayList<>();
        for(Message m: unreadInbox.get(from)){
            if (m.getContent().equals(message.getContent()) && m.getTimeSent().toString().substring(0,19).equals(message.getTimeSent().toString().substring(0,19))){
                messages1.add(m);
            }
        }
        for (Message m: messages1){
            unreadInbox.get(from).remove(m);
        }
        if (!readInbox.containsKey(from)){
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            readInbox.put(from, messages);
        }
        else{
            readInbox.get(from).add(message);
        }
    }


    /**
     * Check if this message is in user's unread inbox, if not, add the given message to the this user's archived inbox.
     * @param message Message object
     */
    public void moveToArchived(Message message) {
        String from = message.getSender();
        if (unreadInbox.containsKey(from)) {
            if (unreadInbox.get(from).contains(message)) {
                this.moveUnreadToRead(message);
            }
        }
        if (!archivedInbox.contains(message)){
            archivedInbox.add(message);
        }
    }


    /**
     * Remove the given message from user's archived inbox
     * @param message Message object
     */
    public void removeFromArchived(Message message){
        archivedInbox.remove(message);
    }


    /**
     * Delete the given message from user's inbox, unread inbox, read inbox and archived inbox.
     * @param from username of the sender of the message
     * @param message Message object
     */
    public void deleteOneMessage(String from, Message message){
        if (unreadInbox.containsKey((from))){
            unreadInbox.get(from).remove(message);
        }
        if (readInbox.containsKey(from)){
            readInbox.get(from).remove(message);
        }
        if (inbox.containsKey(from)){
            inbox.get(from).remove(message);
        }
        archivedInbox.remove(message);
    }


    /**
     * Delete all conversations between the user and the given sender from user's inbox, unread inbox,read inbox and
     * archived inbox.
     * @param from username of the sender
     */
    public void deleteConversation(String from){
        unreadInbox.remove(from);
        readInbox.remove(from);
        inbox.remove(from);
        archivedInbox.removeIf(message -> message.getSender().equals(from));
    }


    /**
     * Clear this user's inbox, unread inbox, read inbox and archived inbox.
     */
    public void clearAllInboxes(){
        unreadInbox.clear();
        readInbox.clear();
        inbox.clear();
        archivedInbox.clear();
    }
}
