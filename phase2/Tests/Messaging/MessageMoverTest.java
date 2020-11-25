package Messaging;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MessageMoverTest {

    @Test
    public void moveToReadTest(){
        String username = "attendee1";
        MessageManager messageManager = new MessageManager();
        messageManager.sendMessage("attendee2", "Hi", username);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertTrue(read.isEmpty());
        assertTrue(unread.containsKey("attendee2"));
        messageMover.moveUnreadToRead("attendee2");
        assertFalse(read.isEmpty());
        assertTrue(messageManager.getReadInbox(username).containsKey("attendee2"));
        assertTrue(read.containsKey("attendee2"));
        assertFalse(unread.containsKey("attendee2"));
    }

    @Test
    public void moveToUnreadTest(){
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        messageManager.sendMessage(from, "Hi", username);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertTrue(unread.containsKey(from));
        messageMover.moveUnreadToRead(from);
        assertTrue(read.containsKey(from));
        assertFalse(unread.containsKey(from));
        messageMover.moveReadToUnread(from);
        assertTrue(unread.containsKey(from));
    }

    @Test
    public void moveToArchived(){
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        messageManager.sendMessage(from, "Hi", username);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        messageMover.moveUnreadToRead(from);
        assertTrue(read.containsKey(from));
        assertEquals(1, read.get(from).size());
        Message message = read.get(from).get(0);
        assertTrue(archived.isEmpty());
//        messageMover.moveToArchived(message);
//        assertTrue(archived.containsKey(from));
    }

    //TODO: rewrite moveToArchived Tests
    @Test
    public void moveArchivedToUnreadTest(){
        String username = "attendee1";
        String[] users = {"attendee1"};
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        messageManager.sendMessage(from, "Hi", username);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
//        archived.put(from, messages);
//        assertTrue(archived.containsKey(from));
//        assertEquals(message, archived.get(from).get(0));
//        messageMover.moveArchivedToUnread(from);
//        assertTrue(archived.containsKey(from));
//        assertTrue(archived.get(from).isEmpty());
//        assertTrue(unread.get(from).contains(message));
    }

    @Test
    public void deleteOneMessageFromReadTest(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        read.put(from, messages);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertEquals(message, read.get(from).get(0));
        messageMover.deleteOneMessage(username, message);
        assertTrue(read.get(from).isEmpty());
    }

    @Test
    public void deleteOneMessageFromArchived(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
//        archived.put(from, messages);
//        MessageMover messageMover = new MessageMover(read, unread, archived);
//        assertEquals(message, archived.get(from).get(0));
//        messageMover.deleteOneMessage(username, message);
//        assertTrue(archived.get(from).isEmpty());
    }

    //IDK ABOUT THIS ONE:
    @Test
    public void deleteOneMessageFromUnread(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        unread.put(from, messages);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertEquals(message, unread.get(from).get(0));
        messageMover.deleteOneMessage(username, message);
        assertTrue(unread.get(from).isEmpty());
    }

    @Test
    public void deleteConversationFromRead(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        read.put(from, messages);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertTrue(read.containsKey(from));
        messageMover.deleteConversation(username, from);
        assertFalse(read.containsKey(from));
    }

    @Test
    public void deleteConversationFromArchived(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
//        archived.put(from, messages);
//        MessageMover messageMover = new MessageMover(read, unread, archived);
//        assertTrue(archived.containsKey(from));
//        messageMover.deleteConversation(username, from);
//        assertFalse(archived.containsKey(from));
    }

    @Test
    public void deleteConversationFromUnread(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        unread.put(from, messages);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertTrue(unread.containsKey(from));
        messageMover.deleteConversation(username, from);
        assertFalse(unread.containsKey(from));
    }

    @Test
    public void clearOneInboxTest(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        unread.put(from, messages);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertTrue(unread.containsKey(from));
        messageMover.clearInbox(username, unread);
        assertFalse(unread.containsKey(from));
        assertTrue(unread.isEmpty());
    }

    @Test
    public void clearAllInboxes(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        List<Message> archived = messageManager.getArchivedInbox(username);
        unread.put(from, messages);
        read.put(from, messages);
//        archived.put(from, messages);
        MessageMover messageMover = new MessageMover(read, unread, archived);
        assertTrue(unread.containsKey(from));
        assertTrue(read.containsKey(from));
//        assertTrue(archived.containsKey(from));
        messageMover.clearInbox(username, read);
        messageMover.clearInbox(username, unread);
//        messageMover.clearInbox(username, archived);
        assertTrue(unread.isEmpty());
        assertTrue(read.isEmpty());
        assertTrue(archived.isEmpty());
    }
}
