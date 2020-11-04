package Messaging;

import org.junit.Test;

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
}

