package Messaging;

import Events.Event;
import Events.EventManager;
import Users.Template;
import Users.User;
import Users.UserManager;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;


import static org.junit.Assert.assertEquals;


public class MessageControllerTest {

    @Test(timeout = 50)
    public void controllerConstructTest(){
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
    }


    @Test(timeout = 50)
    public void viewAllTest(){
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageManager.sendMessage("u1", "hello", "u2");
        String messages = messageController.viewAllMessages("u2");
        System.out.println(messages);
    }

    @Test(timeout = 50)
    public void viewFromTest(){
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageManager.sendMessage("u1", "hello", "u2");
        messageManager.sendMessage("u1", "how are you?", "u2");
        String messages = messageController.viewMessageFrom("u2", "u1");
        System.out.println(messages);
    }

    @Test(timeout = 50)
    public void sendToAllAttTest(){
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.orgSendToAllAtt("org1", "events rock");
        System.out.println(messageController.viewMessageFrom("u1", "org1"));
    }

    @Test(timeout = 50)
    public void sendMessageTestToAllSpk(){
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.orgSendToAllSpeakers("u2", "hello");
    }

    public HashMap<String, User> generateUserHash(){
        User u1 = new User("u1", "pass1", Template.ATTENDEE.getPermissions());
        User u2= new User("u2", "pass2", Template.ATTENDEE.getPermissions());
        HashMap<String, User> users = new HashMap<>();
        users.put(u1.getUsername(), u1);
        users.put(u2.getUsername(), u2);
        return users;
    }


    //    @Test
//    public void testWriteMessage(){
//        MessageManager mm = new MessageManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager(new HashMap<>());
//        MessageController mc = new MessageController(mm, um, em);
//        mc.writeMessage("sender", "recipient", "message");
//        assertEquals(mm.retrieveUserInboxFor("recipient", "sender").get(0).getContent(), "message");
//        assertEquals(mm.retrieveUserInboxFor("recipient", "sender").get(0).getRecipients(), new String[]{"recipient"});
//        assertEquals(mm.retrieveUserInbox("recipient").get("sender").get(0).getSender(),"sender");
//
//    }

//    @Test
//    public void testViewMessage(){
//        MessageManager mm = new MessageManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager(new HashMap<>());
//        MessageController mc = new MessageController(mm, um, em);
//        mc.writeMessage("sender", "recipient", "message");
//        HashMap<String, List<Message>> hashmap1 = mc.viewAllMessages("recipient");
//        assertEquals(hashmap1.get("sender").get(0).getContent(),"message");
//
//
//    }
//    @Test
//    public void testViewMessageMultipleMessages(){
//        MessageManager mm = new MessageManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager(new HashMap<>());
//        MessageController mc = new MessageController(mm, um, em);
//        mc.writeMessage("sender", "recipient1", "message1");
//        mc.writeMessage("sender", "recipient1", "message2");
//        mc.writeMessage("sender", "recipient2", "message2");
//        mc.writeMessage("sender2", "recipient1", "message3");
//        HashMap<String, List<Message>> hashmap1 = mc.viewAllMessages("recipient1");
//        HashMap<String, List<Message>> hashmap2 = mc.viewAllMessages("recipient2");
//        assertEquals(hashmap1.get("sender").get(0).getContent(),"message1");
//        assertEquals(hashmap1.get("sender2").get(0).getContent(),"message3");
//        assertEquals(hashmap1.get("sender").get(1).getContent(),"message2");
//        assertEquals(hashmap2.get("sender").get(0).getContent(),"message2");
//    }
//
//    @Test
//    public void writeToEvents(){
//        MessageManager mm = new MessageManager();
//        EventManager em = new EventManager();
//        UserManager um = new UserManager(new HashMap<>());
//        MessageController mc = new MessageController(mm, um, em);
//        List<String> participants = new ArrayList<>();
//        Instant time = Instant.now();
//        participants.add("user1");
//        participants.add("user2");
//        Event e1 = new Event("speaker", time, "Test Event", participants, "Meeting Room 1",  3);
//        em.addEventToHash(e1);
//        mc.writeToEvents("speaker", "announcement", 0);
//        HashMap<String, List<Message>> hashmap1 = mc.viewAllMessages("user1");
//        HashMap<String, List<Message>> hashmap2 = mc.viewAllMessages("user2");
//        assertEquals(em.getParticipants(0), participants);
//        assertEquals(hashmap1.get("speaker").get(0).getContent(), "announcement");
//        assertEquals(hashmap2.get("speaker").get(0).getContent(), "announcement");
//    }
}
