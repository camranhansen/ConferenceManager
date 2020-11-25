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

    public void moveReadToUnread(String from){
        List<Message> messages = this.readInbox.remove(from);
        this.inbox.put(from, messages);
    }

    public void moveUnreadToRead(String from){
        List<Message> messages = this.inbox.remove(from);
        this.readInbox.put(from, messages);
    }

    public void moveArchivedToUnread(String from){
        //think about if the user is both in unread and archived
    }

//    public void moveToArchived(Message message){
//        //Check if message is in unread?? Or restrict to only moving read to archived
//        String from = message.getSender();
//        List<Message> messages = readInbox.get(from);
//        messages.remove(message);
//        if (!archivedInbox.containsKey(from)){
//            List<Message> newMessages = new ArrayList<>();
//            newMessages.add(message);
//            archivedInbox.put(from, newMessages);
//        }
//        else{
//            archivedInbox.get(from).add(message);
//        }
//    }

    public void deleteOneMessage(String username, Message message){}

    public void deleteConversation(String username, String from){}

    public void clearInbox(String username, HashMap<String, List<Message>> inbox){}
}
