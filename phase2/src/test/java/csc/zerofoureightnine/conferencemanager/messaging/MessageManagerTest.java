package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

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

    }

    @Test
    public void retrieveMessagesTest(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(sqlMap);
        messageManager.sendMessage("potter", "stupefy", "snape");
        messageManager.sendMessage("snape", "detention", "potter", "weasley", "granger");
        HashMap<String, List<Message>> i1 = messageManager.retrieveUserInbox("potter");
        HashMap<String, List<Message>> i2 = messageManager.retrieveUserInbox("granger");
        HashMap<String, List<Message>> i3 = messageManager.retrieveUserInbox("snape");
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
        assertEquals("The inbox for John should be empty, but not null.", 0, messageManager.retrieveUserInbox("John").size());
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

//    @Test
//    public void testSendMultiRecipientMessage() {
//        String sender = "Will Smith";
//        String[] receivers = new String[] {"Aladdin", "Mena Massoud"};
//        String content = "Well Ali Baba had them forty thieves\n" +
//                "Scheherezad-ie had a thousand tales\n" +
//                "But master you in luck 'cause up your sleeves\n" +
//                "You got a brand of magic never fails\n" +
//                "You got some power in your corner now\n" +
//                "Some heavy ammunition in your camp\n" +
//                "You got some punch, pizzazz, yahoo and how\n" +
//                "See all you gotta do is rub that lamp\n" +
//                "And I'll say\n" +
//                "Mister Aladdin, sir\n" +
//                "What will your pleasure be?\n" +
//                "Let me take your order\n" +
//                "Jot it down\n" +
//                "You ain't never had a friend like me\n" +
//                "Life is your restaurant\n" +
//                "And I'm your maitre d'\n" +
//                "C'mon whisper what it is you want\n" +
//                "You ain't never had a friend like me\n" +
//                "Yes sir, we pride ourselves on service\n" +
//                "You're the boss\n" +
//                "The king, the shah\n" +
//                "Say what you wish\n" +
//                "It's yours, true dish\n" +
//                "How about a little more Baklava?\n" +
//                "Have some of column A\n" +
//                "Try all of column B\n" +
//                "I'm in the mood to help you dude\n" +
//                "You ain't never had a friend like me\n" +
//                "Can your friends do this?\n" +
//                "Do your friends do that?\n" +
//                "Do your friends pull this out their little hat?\n" +
//                "Can your friends go poof?\n" +
//                "Well, looky here\n" +
//                "Can your friends go, Abracadabra, let 'er rip\n" +
//                "And then make the sucker disappear?\n" +
//                "So dontcha sit there slack-jawed, buggy-eyed\n" +
//                "I'm here to answer all your midday prayers\n" +
//                "You got me bona fide, certified\n" +
//                "You got a genie for your chargé d'affaires\n" +
//                "I got a powerful urge to help you out\n" +
//                "So what-cha wish?\n" +
//                "I really want to know\n" +
//                "You got a list that's three miles long, no doubt\n" +
//                "Well, all you gotta do is rub like so - and oh\n" +
//                "Mister Aladdin, sir, have a wish or two or three\n" +
//                "I'm on the job, you big nabob\n" +
//                "You ain't never had a friend, never had a friend\n" +
//                "You ain't never had a friend, never had a friend\n" +
//                "You ain't never had a friend like me\n" +
//                "You ain't never had a friend like me, hah";
//
//        config = new SQLConfiguration("testfiles/db/data");
//        sqlMap = new SQLMap<>(config, MessageData.class);
//        MessageManager messageManager = new MessageManager(sqlMap);
//        messageManager.sendMessage(sender, content, receivers);
//
//        for (String recipient : receivers) {
//            Message message = messageManager.retrieveUserInbox(recipient).get(sender).get(0);
//            assertEquals("Check content.", content, message.getContent());
//            assertArrayEquals("Check recipients.", receivers, message.getRecipients());
//        }
//    }

    @Test
    public void testRetrieveUserInboxForUnreadInbox(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.retrieveUserInboxFor("recipient", "sender");
        assertEquals("hello", mm.retrieveUserInboxFor("recipient", "sender").get(0).getContent());
        //assertEquals(0, mm.getUnreadFrom("recipient", "sender").size());
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
        //assertEquals(0, mm.getReadInbox("recipient").size());
    }

    @Test
    public void testSendMessageUnreadInbox2(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        mm.sendMessage("sender", "hello", "recipient");
        mm.sendMessage("sender", "hi", "recipient","recipient2");
        //assertEquals("hi", mm.getUnreadFrom("recipient", "sender").get(1).getContent());
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
        //assertEquals("sender: hello, hi", string);
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
        //assertEquals("sender: hi, hello\n", string);
        assertEquals("sender: hi\n", string2);
        assertEquals("You have no unread messages", string3);
        mm.sendMessage("sender2", "bye", "recipient", "recipient2");
        String string4 = mm.unreadInboxToString("recipient");
        //assertEquals("sender: hello, hi\n" + "sender2: bye\n", string4);

    }

    @Test
    public void testArchivedMessagesToString(){
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(sqlMap);
        Message message = new Message("sender", new String[]{"recipient"}, "hello");
        mm.sendMessage("sender", "hello", "recipient");
        mm.sendMessage("sender", "hi", "recipient","recipient2");
        MessageMover messageMover = new MessageMover(mm, "recipient");
        messageMover.moveToArchived(message);
        String string = mm.archivedMessagesToString("recipient");
        //assertEquals("sender: hello\n", string);
        String string2 = mm.archivedMessagesToString("recipient2");
        assertEquals("You have no archived messages", string2);
        Message message2 = new Message("sender2", new String[]{"recipient"}, "hello");
        mm.sendMessage("sender2", "hello", "recipient", "recipient2");
        messageMover.moveToArchived(message2);
        String string3 = mm.archivedMessagesToString("recipient");
        //assertEquals("sender: hello\n" + "sender2: hello\n", string3);
        messageMover.removeFromArchived(message);
        String string4 = mm.archivedMessagesToString("recipient");
        //assertEquals("sender2: hello\n", string4);
        messageMover.removeFromArchived(message2);
        String string5 = mm.archivedMessagesToString("recipient");
        assertEquals("You have no archived messages", string5);


    }
}

