package Messaging;

import Events.EventManager;
import Users.UserManager;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageControllerTest {
    @Test
    public void testWriteMessage(){
        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager(new HashMap<>());
        MessageController mc = new MessageController(mm, um, em);
        mc.writeMessage("sender", "recipient", "message");
        assertEquals(mm.retrieveUserInboxFor("recipient", "sender").get(0).getContent(), "message");
        assertEquals(mm.retrieveUserInboxFor("recipient", "sender").get(0).getRecipients(), new String[]{"recipient"});
        assertEquals(mm.retrieveUserInbox("recipient").get("sender").get(0).getSender(),"sender");

    }

    @Test
    public void testViewMessage(){
        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager(new HashMap<>());
        MessageController mc = new MessageController(mm, um, em);
        mc.writeMessage("sender", "recipient", "message");
        HashMap<String, List<Message>> hashmap1 = mc.viewSentMessage("recipient");
        assertEquals(hashmap1.get("sender").get(0).getContent(),"message");


    }
    @Test
    public void testViewMessageMultipleMessages(){
        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager(new HashMap<>());
        MessageController mc = new MessageController(mm, um, em);
        mc.writeMessage("sender", "recipient1", "message1");
        mc.writeMessage("sender", "recipient1", "message2");
        mc.writeMessage("sender", "recipient2", "message2");
        mc.writeMessage("sender2", "recipient1", "message3");
        HashMap<String, List<Message>> hashmap1 = mc.viewSentMessage("recipient1");
        HashMap<String, List<Message>> hashmap2 = mc.viewSentMessage("recipient2");
        assertEquals(hashmap1.get("sender").get(0).getContent(),"message1");
        assertEquals(hashmap1.get("sender2").get(0).getContent(),"message3");
        assertEquals(hashmap1.get("sender").get(1).getContent(),"message2");
        assertEquals(hashmap2.get("sender").get(0).getContent(),"message2");


    }
    //@Test
    //public void writeToEvents(){
        //MessageManager mm = new MessageManager();
        //EventManager em = new EventManager();
        //UserManager um = new UserManager(new HashMap<>());
        //MessageController mc = new MessageController(mm, um, em);
        //List<String> participants = new ArrayList<>();
        //participants.add("user1");
        //participants.add("user2");
        //em.createEvent("speaker1", Instant.now(), "event1", participants, "room1", 6);
        //mc.writeToEvents("speaker1", "announcement", 0);
        //HashMap<String, List<Message>> hashmap1 = mc.viewSentMessage("user1");
        //HashMap<String, List<Message>> hashmap2 = mc.viewSentMessage("user2");
        //assertEquals(em.getParticipants(0), participants);
        //assertEquals(hashmap1.get("speaker1").get(0).getContent(), "announcement");
        //assertEquals(hashmap2.get("speaker1").get(0).getContent(), "announcement");


  //  }
}
