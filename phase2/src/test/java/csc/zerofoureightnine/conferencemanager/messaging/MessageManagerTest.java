package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageManagerTest {
    private static SQLConfiguration config;
    private static PersistentMap<String, MessageData> sqlMap;

    @Test
    public void messageManagerConstructTest(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
    }

    @Test
    public void sendMessagesTest(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        messageManager.sendMessage("potter", "stupefy", "snape");
        messageManager.sendMessage("snape", "detention", "potter", "weasley", "granger");
        assertEquals("detention", messageManager.retrieveUserInbox("potter").get("snape").get(0).getContent());

    }

    @Test
    public void retrieveMessagesTest(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        messageManager.sendMessage("potter", "stupefy", "snape");
        messageManager.sendMessage("snape", "detention", "potter", "weasley", "granger");
        Map<String, List<Message>> i1 = messageManager.retrieveUserInbox("potter");
        Map<String, List<Message>> i2 = messageManager.retrieveUserInbox("granger");
        Map<String, List<Message>> i3 = messageManager.retrieveUserInbox("snape");
        assertTrue(i1.containsKey("snape"));
        assertTrue(i3.containsKey("potter"));
        String i2Content = i2.get("snape").get(0).getContent();
        assertEquals("detention", i2Content);
    }

    @Test
    public void testRetrieveMessage1(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        assertEquals(mm.retrieveUserInbox("sender"), new HashMap<>());

    }

    @Test
    public void testSendMessageEmpty() {
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        assertEquals("The inbox for John should be empty, but not null.", 0,
                messageManager.retrieveUserInbox("John").size());
    }

    @Test
    public void testSendSingleRecipientMessage() {
        String sender = "John";
        String receiver = "Chief";
        String content = "Hey chief.";
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        messageManager.sendMessage(sender, content, receiver);
        Message message = messageManager.retrieveUserInbox(receiver).get(sender).get(0);
        assertEquals("Check if Chief has John's message.", content, message.getContent());
        assertEquals("Check if the recipient has one person", 1, message.getRecipients().length);
        assertEquals("Check if the recipients is correct.", receiver, message.getRecipients()[0]);
    }

    @Test
    public void testSendMultiRecipientMessage() {
        String sender = "Will Smith";
        String[] receivers = new String[] {"Aladdin", "Mena Massoud"};
        String content = "Well Ali Baba had them forty thieves\n" +
                "Scheherezad-ie had a thousand tales\n";

        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        messageManager.sendMessage(sender, content, receivers);

        for (String recipient : receivers) {
            Message message = messageManager.retrieveUserInbox(recipient).get(sender).get(0);
            assertEquals("Check content.", content, message.getContent());
            assertArrayEquals("Check recipients.", receivers, message.getRecipients());
        }
    }

    @Test
    public void testRetrieveUserInboxForUnreadInbox(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.retrieveUserInboxFor("recipient", "sender");
        assertEquals("hello", mm.retrieveUserInboxFor("recipient", "sender").get(0).getContent());
        assertTrue(mm.getUnreadFrom("recipient", "sender").isEmpty());
    }

    @Test
    public void testRetrieveUserInboxForReadInbox(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.retrieveUserInboxFor("recipient", "sender");
        assertEquals("hello", mm.retrieveUserInboxFor("recipient", "sender").get(0).getContent());
        assertEquals("hello", mm.getReadInbox("recipient").get("sender").get(0).getContent());
    }

    @Test
    public void testSendMessageUnreadInbox(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        assertEquals("hello", mm.getUnreadFrom("recipient", "sender").get(0).getContent());
        assertEquals(0, mm.getReadInbox("recipient").size());
    }

    @Test
    public void testSendMessageUnreadInbox2(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.sendMessage("sender", "hi", "recipient","recipient2");
        //assertTrue(mm.getUnreadFrom("recipient", "sender").contains("hello"));
        assertEquals("hi", mm.getUnreadFrom("recipient2", "sender").get(0).getContent());
    }

    @Test
    public void testSingleUnreadInboxToString(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.sendMessage("sender", "hi", "recipient","recipient2");
        String string = mm.singleUnreadInboxToString("recipient", "sender");
        String string1 = mm.singleUnreadInboxToString("recipient", "sender1");
        assertTrue(string.contains("sender:"));
        assertTrue(string.contains("hi"));
        assertTrue(string.contains("hello"));
        assertEquals("You have no unread messages from sender1", string1);
    }

    @Test
    public void testUnreadInboxToString(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.sendMessage("sender", "hi", "recipient","recipient2");
        String string = mm.unreadInboxToString("recipient");
        String string2 = mm.unreadInboxToString("recipient2");
        String string3 = mm.unreadInboxToString("recipient3");
        assertTrue(string.contains("sender:"));
        assertTrue(string.contains("hi"));
        assertTrue(string.contains("hello"));
        //assertEquals("sender: hi, 2020-12-07T05:55:32.668Z\n", string2);
        assertEquals("You have no unread messages", string3);
        mm.sendMessage("sender2", "bye", "recipient", "recipient2");
        String string4 = mm.unreadInboxToString("recipient");
        assertTrue(string4.contains("sender:"));
        assertTrue(string4.contains("hi"));
        assertTrue(string4.contains("hello"));
        assertTrue(string4.contains("bye"));
        assertTrue(string4.contains("sender2"));



    }

    @Test
    public void testArchivedMessagesToString(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        MessageData md = mm.sendMessage("sender", "hello", "recipient");
        MessageData md2 = mm.sendMessage("sender", "hi", "recipient","recipient2");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        messageMover.moveToArchived("sender", "hello", md.getTimeSent().toString());
        String string = mm.archivedMessagesToString("recipient");
        //assertEquals("sender: hello, "+md.getTimeSent().toString()+"\n", string);
        String string2 = mm.archivedMessagesToString("recipient2");
        assertEquals("You have no archived messages", string2);
        Message message2 = new Message("sender2", new String[]{"recipient"}, "hello");
        mm.sendMessage("sender2", "hello", "recipient", "recipient2");
        messageMover.moveToArchived(md2.getSender(), md2.getContent(), md2.getTimeSent().toString());
        String string3 = mm.archivedMessagesToString("recipient");
        //assertEquals("sender: hello\n" + "sender2: hello\n", string3);
        messageMover.removeFromArchived(md.getSender(), md.getContent(), md.getTimeSent().toString());
        String string4 = mm.archivedMessagesToString("recipient");
        //assertEquals("sender2: hello\n", string4);
        messageMover.removeFromArchived(md2.getSender(), md2.getContent(), md2.getTimeSent().toString());
        String string5 = mm.archivedMessagesToString("recipient");
        assertEquals("You have no archived messages", string5);


    }
}

