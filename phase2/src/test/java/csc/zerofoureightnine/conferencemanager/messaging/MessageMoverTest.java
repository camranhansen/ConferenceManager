package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class MessageMoverTest {
    private static SQLConfiguration config;
    private static PersistentMap<String, MessageData> sqlMap;

    @Test
    public void moveToReadTest(){
        String username = "attendee1";
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        MessageData md = messageManager.sendMessage("attendee2", "Hi", username);
        MessageMover messageMover = new MessageMover(messageManager, username);
        assertTrue(messageManager.getReadInbox(username).isEmpty());
        assertTrue(messageManager.retrieveUserInbox(username).containsKey("attendee2"));
        messageMover.moveUnreadToRead(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertFalse(messageManager.retrieveUserInbox(username).isEmpty());
        assertTrue(messageManager.getReadInbox(username).containsKey("attendee2"));
    }


    @Test
    public void deleteOneMessageFromReadTest(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        assertFalse(mm.getUnreadInbox("recipient").isEmpty());
        assertEquals("hello", mm.getUnreadInbox("recipient").get("sender").get(0).getContent());
        messageMover.moveUnreadToRead(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertFalse(mm.getReadInbox("recipient").isEmpty());
        assertTrue(mm.getUnreadInbox("recipient").isEmpty());
        messageMover.deleteOneMessage(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertTrue(mm.getReadInbox("recipient").isEmpty());
    }

    @Test
    public void deleteOneMessageFromArchived(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        assertFalse(mm.getUnreadInbox("recipient").isEmpty());
        assertEquals("hello", mm.getUnreadInbox("recipient").get("sender").get(0).getContent());
        messageMover.moveToArchived(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertFalse(mm.getArchivedInbox("recipient").isEmpty());
        messageMover.deleteOneMessage(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertTrue(mm.getArchivedInbox("recipient").isEmpty());
    }

    @Test
    public void deleteOneMessageFromUnread(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        assertFalse(mm.getUnreadInbox("recipient").isEmpty());
        assertEquals("hello", mm.getUnreadInbox("recipient").get("sender").get(0).getContent());
        assertTrue(mm.getUnreadInbox("recipient").containsKey("sender"));
        assertEquals("sender",  mm.getUnreadInbox("recipient").get("sender").get(0).getSender());
        messageMover.deleteOneMessage(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertTrue(mm.getUnreadInbox("recipient").isEmpty());
    }

    @Test
    public void deleteConversationFromRead(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        mm.retrieveUserInbox("recipient");
        assertEquals("hello", mm.getReadInbox("recipient").get("sender").get(0).getContent());
        assertEquals("sender",  mm.getReadInbox("recipient").get("sender").get(0).getSender());
        messageMover.deleteConversation("sender");
        assertTrue(mm.getReadInbox("recipient").isEmpty());
        assertTrue(mm.retrieveUserInbox("recipient").isEmpty());
    }

    @Test
    public void deleteConversationFromArchived(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        List<Message> archived = mm.getArchivedInbox("recipient");
        assertTrue(archived.isEmpty());
        MessageMover messageMover = new MessageMover(mm, "recipient");
        messageMover.moveToArchived(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertEquals("hello", mm.getArchivedInbox("recipient").get(0).getContent());
        messageMover.deleteConversation("sender");
        assertEquals(0, mm.getArchivedInbox("recipient").size());
        assertTrue(mm.getArchivedInbox("recipient").isEmpty());
    }

    @Test
    public void deleteConversationFromUnread() {
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        assertFalse(mm.getUnreadInbox("recipient").isEmpty());
        assertEquals("hello", mm.getUnreadInbox("recipient").get("sender").get(0).getContent());
        assertTrue(mm.getUnreadInbox("recipient").containsKey("sender"));
        assertEquals("sender",  mm.getUnreadInbox("recipient").get("sender").get(0).getSender());
        messageMover.deleteConversation("sender");
        assertTrue(mm.getUnreadInbox("recipient").isEmpty());

    }

    @Test
    public void clearAllInboxes() {
        String username = "Johny";
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        MessageMover messageMover = new MessageMover(messageManager, username);

        MessageData messageData = new MessageData();
        messageData.setSender("Timmy");
        messageData.addRecipients(username, "John");
        messageData.addToRead(username);
        messageData.addToArchived(username);

        String key = UUID.randomUUID().toString();
        sqlMap.put(key, messageData);

        assertTrue(sqlMap.loadInCollection("recipients", username).get(0).equals(messageData));
        messageMover.clearAllInboxes();
        assertEquals(0, sqlMap.loadInCollection("recipients", username).size());
        assertTrue(messageManager.getArchivedInbox(username).isEmpty());
        assertTrue(messageManager.getReadInbox(username).isEmpty());
        assertTrue(messageManager.getUnreadFrom(username, "Timmy").isEmpty());
    }

    @Test
    public void testMoveToReadUnread(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        mm.retrieveUserInboxFor("recipient", "sender");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        messageMover.moveReadToUnread(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertEquals("hello", mm.getUnreadInbox("recipient").get("sender").get(0).getContent());
        assertTrue(mm.getReadInbox("recipient").isEmpty());
        messageMover.moveUnreadToRead(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertEquals("hello", mm.getReadInbox("recipient").get("sender").get(0).getContent());
        assertTrue(mm.getUnreadInbox("recipient").isEmpty());

    }


    @Test
    public void testMoveRemoveMessageFromArchived(){
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        messageMover.moveToArchived(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertEquals("hello", mm.getArchivedInbox("recipient").get(0).getContent());
        messageMover.removeFromArchived(md.getSender(), md.getContent(), md.getTimeSent().toString());
        assertEquals(0, mm.getArchivedInbox("recipient").size());
    }
}