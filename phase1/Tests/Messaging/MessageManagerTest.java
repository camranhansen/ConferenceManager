package Messaging;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageManagerTest {
    @Test(timeout = 50)
    public void messageManagerConstructTest(){
        MessageManager messageManager = new MessageManager();
    }

    @Test(timeout = 50)
    public void sendMessagesTest(){
        MessageManager messageManager = new MessageManager();
        messageManager.sendMessage("potter", "stupefy", "snape");
        messageManager.sendMessage("snape", "detention", "potter", "weasley", "granger");
    }

    @Test(timeout = 50)
    public void retrieveMessagesTest(){
        MessageManager messageManager = new MessageManager();
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

    @Test//Multiple recipients
    public void testRetrieveMessage(){
        MessageManager mm = new MessageManager();
        Message m = new Message("sender1", new String[]{"recipient1", "recipient2"}, "message1");
        HashMap<String, List<Message>> hashmap = new HashMap<>();
        List<Message> mList = new ArrayList<>();
        mList.add(m);
        hashmap.put("recipient1", mList);
        hashmap.put("recipient2", mList);
        mm.sendMessage("sender1", "message1", "recipient1", "recipient2");
        assertEquals(mm.retrieveUserInbox("sender1"), hashmap);
    }

    @Test//No message
    public void testRetrieveMessage1(){
        MessageManager mm = new MessageManager();
        assertEquals(mm.retrieveUserInbox("sender"), new HashMap<>());

    }

    @Test//Multiple messages
    public void testRetrieveMessage2() {
        MessageManager mm = new MessageManager();
        Message m1 = new Message("sender1", new String[]{"recipient1"}, "message1");
        Message m2 = new Message("sender1", new String[]{"recipient1"}, "message2");
        HashMap<String, List<Message>> hashmap = new HashMap<>();
        List<Message> mList = new ArrayList<>();
        mList.add(m1);
        mList.add(m2);
        hashmap.put("recipient1", mList);
        mm.sendMessage("sender1", "message1", "recipient1");
        mm.sendMessage("sender1", "message2", "recipient1");
        assertEquals(mm.retrieveUserInbox("sender1"), hashmap);
    }
}

