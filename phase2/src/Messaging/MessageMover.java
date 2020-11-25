package Messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageMover {
    private HashMap<String, List<Message>> inbox;
    private HashMap<String, List<Message>> readInbox;
    private List<Message> archivedInbox;

    public MessageMover(HashMap<String, List<Message>> readInbox,
                        HashMap<String, List<Message>> inbox,
                        List<Message> archivedInbox){
        this.inbox = inbox;
        this.readInbox = readInbox;
        this.archivedInbox = archivedInbox;
    }

    public void moveReadToUnread(Message message){
        String from = message.getSender();
        readInbox.get(from).remove(message);
        if(!inbox.containsKey(from)) {
            List<Message> m = new ArrayList<>();
            inbox.put(from, m);
        } else{
            List<Message> m = inbox.get(from);
            m.add(message);
        }

    }

    public void moveUnreadToRead(String from){
        List<Message> messages = this.inbox.remove(from);
        this.readInbox.put(from, messages);
    }

    public void moveArchivedToUnread(String from){
        //think about if the user is both in unread and archived
    }

    public void moveToArchived(Message message) {
        String from = message.getSender();
        if (inbox.containsKey(from)) {
            if (inbox.get(from).contains(message)) {
                this.moveUnreadToRead(message);
            }
        }
        if (!archivedInbox.contains(message)){
            archivedInbox.add(message);
        }
    }
    public void deleteOneMessage(String username, Message message){}


    public void deleteConversation(String username, String from){}

    public void clearAllInboxes(){
        inbox.clear();
        readInbox.clear();
        archivedInbox.clear();
    }
}
