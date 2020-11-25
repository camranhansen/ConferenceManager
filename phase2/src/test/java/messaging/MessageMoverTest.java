package messaging;

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
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertTrue(messageManager.getReadInbox(username).isEmpty());
        assertTrue(messageManager.retrieveUserInbox(username).containsKey("attendee2"));
        messageMover.moveUnreadToRead(messageManager.retrieveUserInbox(username).get("attendee2").get(0));
        assertFalse(messageManager.retrieveUserInbox(username).isEmpty());
        assertTrue(messageManager.getReadInbox(username).containsKey("attendee2"));
    }

    @Test
    public void moveToUnreadTest(){
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        messageManager.sendMessage(from, "Hi", username);
        HashMap<String, List<Message>> unread = messageManager.retrieveUserInbox(username);
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertTrue(unread.containsKey(from));
        Message message = unread.get(from).get(0);
        messageMover.moveUnreadToRead(message);
        assertTrue(read.containsKey(from));
        assertTrue(read.get(from).contains(message));
        assertFalse(unread.get(from).contains(message));
        messageMover.moveReadToUnread(message);
        assertTrue(unread.containsKey(from));
        assertTrue(unread.get(from).contains(message));
        assertTrue(messageManager.retrieveUserInbox(username).get(from).contains(message));
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
        MessageMover messageMover = new MessageMover(messageManager, username);
        Message message = unread.get(from).get(0);
        messageMover.moveUnreadToRead(message);
        assertEquals(1, read.get(from).size());
        assertTrue(archived.isEmpty());
        messageMover.moveToArchived(message);
        assertTrue(archived.contains(message));
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
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        read.put(from, messages);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertEquals(message, read.get(from).get(0));
        messageMover.deleteOneMessage(from, message);
        assertTrue(read.get(from).isEmpty());
    }

    @Test
    public void deleteOneMessageFromArchived(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        List<Message> archived = messageManager.getArchivedInbox(username);
        archived.add(message);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertEquals(message, archived.get(0));
        messageMover.deleteOneMessage(username, message);
        assertTrue(archived.isEmpty());
    }

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
        unread.put(from, messages);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertEquals(message, unread.get(from).get(0));
        messageMover.deleteOneMessage(from, message);
        assertTrue(unread.get(from).isEmpty());
        assertTrue(messageManager.retrieveUserInbox(username).get(from).isEmpty());
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
        HashMap<String, List<Message>> read = messageManager.getReadInbox(username);
        read.put(from, messages);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertTrue(read.containsKey(from));
        messageMover.deleteConversation(from);
        assertFalse(read.containsKey(from));
        assertFalse(messageManager.getReadInbox(username).containsKey(from));
    }

    @Test
    public void deleteConversationFromArchived(){
        String[] users = {"attendee1"};
        String username = "attendee1";
        String from = "attendee2";
        MessageManager messageManager = new MessageManager();
        Message message = new Message(from, users, "hello");
        List<Message> archived = messageManager.getArchivedInbox(username);
        archived.add(message);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertTrue(archived.contains(message));
        messageMover.deleteConversation(from);
        assertFalse(archived.contains(message));
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
        List<Message> archived = messageManager.getArchivedInbox(username);
        unread.put(from, messages);
        MessageMover messageMover = new MessageMover(messageManager, username);
        messageMover.moveToArchived(message);
        assertTrue(unread.containsKey(from));
        assertTrue(archived.contains(message));
        messageMover.deleteConversation(from);
        assertFalse(unread.containsKey(from));
        assertFalse(archived.contains(message));
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
        archived.add(message);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertTrue(unread.containsKey(from));
        assertTrue(read.containsKey(from));
        assertTrue(archived.contains(message));
        messageMover.clearAllInboxes();
        assertTrue(unread.isEmpty());
        assertTrue(read.isEmpty());
        assertTrue(archived.isEmpty());
    }
}