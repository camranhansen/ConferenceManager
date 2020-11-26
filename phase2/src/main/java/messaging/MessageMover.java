package messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageMover {
    private HashMap<String, List<Message>> inbox;
    private HashMap<String, List<Message>> readInbox;
    private List<Message> archivedInbox;

    public MessageMover(MessageManager messageManager, String username){
        this.inbox = messageManager.getUnreadInbox(username);
        this.readInbox = messageManager.getReadInbox(username);
        this.archivedInbox = messageManager.getArchivedInbox(username);
    }

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
        if(!inbox.containsKey(from)) {
            List<Message> m = new ArrayList<>();
            inbox.put(from, m);
        } else{
            List<Message> m = inbox.get(from);
            m.add(message);
        }
    }

    public void moveUnreadToRead(Message message){
        String from = message.getSender();
        List<Message> messages1 = new ArrayList<>();
        for(Message m: inbox.get(from)){
            if (m.getContent().equals(message.getContent()) && m.getTimeSent().toString().substring(0,19).equals(message.getTimeSent().toString().substring(0,19))){
                messages1.add(m);
            }
        }
        for (Message m: messages1){
            inbox.get(from).remove(m);
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

    public void removeFromArchived(Message message){
        archivedInbox.remove(message);
    }

    public void deleteOneMessage(String from, Message message){
        if (inbox.containsKey((from))){
            inbox.get(from).remove(message);
        }
        if (readInbox.containsKey(from)){
            readInbox.get(from).remove(message);
        }
        archivedInbox.remove(message);
    }


    public void deleteConversation(String from){
        inbox.remove(from);
        readInbox.remove(from);
        archivedInbox.removeIf(message -> message.getSender().equals(from));
    }

    public void clearAllInboxes(){
        inbox.clear();
        readInbox.clear();
        archivedInbox.clear();
    }
}
