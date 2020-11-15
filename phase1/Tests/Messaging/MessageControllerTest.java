package Messaging;

import Events.Event;
import Events.EventManager;
import Users.Permission;
import Users.Template;
import Users.User;
import Users.UserManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MessageControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void controllerConstructTest() {
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
    }

    @Test
    public void performSelectedMessageAllAttTest() {
        String input = "hello" + System.lineSeparator() + "1" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.performSelectedAction("org1", Permission.MESSAGE_ALL_USERS);
        String messages = messageManager.wholeInboxToString("u1");
        String message = messageManager.singleInboxToString("u1", "org1");
        assertTrue(messages.contains("hello"));
        assertTrue(message.contains("hello") && message.contains("org1"));
    }

    @Test
    public void performSelectedMessageAllSpkTest() {
        String input = "hello" + System.lineSeparator() + "2" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.performSelectedAction("org1", Permission.MESSAGE_ALL_USERS);
        String messages = messageManager.wholeInboxToString("spk1");
        String message = messageManager.singleInboxToString("spk1", "org1");
        String attendee = messageManager.wholeInboxToString("u1");
        assertTrue(messages.contains("hello"));
        assertTrue(message.contains("hello") && message.contains("org1"));
        assertFalse(attendee.contains("hello"));
    }

    @Test
    public void performSelectedMessageSingleTest() {
        String input = "u2" + System.lineSeparator() + "hello" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.performSelectedAction("u1", Permission.MESSAGE_SINGLE_USER);
        String messages = messageManager.wholeInboxToString("u2");
        assertTrue(messages.contains("hello") && messages.contains("u1"));
    }

    @Test
    public void performSelectedMessageAllEventTest() {
        String input = "hello" + System.lineSeparator() + "1" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        Instant time = Instant.now();
        eventManager.createEvent("spk1", time, "talk1", "23", 2);
        String eventId = "23" + time;
        eventManager.enrollUser(eventId, "u2");
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.performSelectedAction("spk1", Permission.MESSAGE_EVENT_USERS);
        String messages = messageManager.wholeInboxToString("u2");
        assertTrue(messages.contains("hello") && messages.contains("spk1"));
    }

    @Test
    public void performSelectedMessageOneEventTest() {
        Instant time = Instant.now();
        String eventId = "23" + time;
        String input = "hello" + System.lineSeparator() + "2" + System.lineSeparator() + eventId + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        eventManager.createEvent("spk1", time, "talk1", "23", 2);
        eventManager.enrollUser(eventId, "u2");
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.performSelectedAction("spk1", Permission.MESSAGE_EVENT_USERS);
        String messages = messageManager.wholeInboxToString("u2");
        assertTrue(messages.contains("hello") && messages.contains("spk1"));
    }

    @Test
    public void performSelectedViewAllMessagesTest() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageManager.sendMessage("u2", "hi", "u1");
        messageManager.sendMessage("u2", "how are you?", "u1");
        messageManager.sendMessage("spk1", "hello", "u1");
        messageController.performSelectedAction("u1", Permission.VIEW_SELF_MESSAGES);
        String out = "0. Exit\n" + "1. View all your messages\n" +
                "2. View messages from one user\n" + "spk1: hello, \n"+"u2: hi, how are you?, \n\n";

        assertEquals(out, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void performSelectedViewFromMessagesTest() {
        String input = "2" + System.lineSeparator() + "u2" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageManager.sendMessage("u2", "hi", "u1");
        messageManager.sendMessage("u2", "how are you?", "u1");
        messageManager.sendMessage("spk1", "hello", "u1");
        messageController.performSelectedAction("u1", Permission.VIEW_SELF_MESSAGES);
        String out = "0. Exit\n" + "1. View all your messages\n" +
                "2. View messages from one user\n" + "Enter other username messages you'd like to see: \n"+
                "u2: hi, how are you?, \n";

        assertEquals(out, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void performSelectedViewOtherMessageTest(){
        String input = "u1"+ System.lineSeparator()+"1" + System.lineSeparator() + "u2" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageManager.sendMessage("u2", "hi", "u1");
        messageManager.sendMessage("u2", "how are you?", "u1");
        messageManager.sendMessage("spk1", "hello", "u1");
        messageController.performSelectedAction("u1", Permission.VIEW_OTHER_MESSAGES);
        String out = "Enter username's messages you'd like to see: \n"+"0. Exit\n" + "1. View all your messages\n" +
                "2. View messages from one user\n"+ "spk1: hello, \n" + "u2: hi, how are you?, \n\n";
        assertEquals(out, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void writeMessageTest() {
        String input = "u1" + System.lineSeparator() + "hello" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);

        messageController.writeMessage("u2");
        String message = messageManager.singleInboxToString("u1", "u2");
        assertTrue(message.contains("hello"));
    }

    @Test
    public void sendMessageTestToAllSpk() {
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageController.orgSendToAllSpeakers("u2", "hello");
        assertEquals(messageManager.retrieveUserInboxFor("spk1", "u2").get(0).getContent(), "hello");
    }

    @Test
    public void testSendMessageToAllAtt(){
        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager(new HashMap<>());
        MessageController mc = new MessageController(mm, um, em);
        um.createUser("user", "123", Template.ORGANIZER.getPermissions());
        um.createUser("user2", "123", Template.ATTENDEE.getPermissions());
        um.createUser("user3", "123", Template.ATTENDEE.getPermissions());
        mc.orgSendToAllAtt("user", "hello");
        assertEquals(mm.retrieveUserInboxFor("user2", "user").get(0).getContent(), "hello");
        assertEquals("[user2, user3]",
                Arrays.deepToString(mm.retrieveUserInboxFor("user3", "user").get(0).getRecipients()));
    }

    @Test()
    public void viewAllTest() {
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        messageManager.sendMessage("u1", "hello", "u2");
        String messages = messageController.viewAllMessages("u2");
        System.out.println(messages);
    }

    @Test
    public void viewFromTest() {
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

    public HashMap<String, User> generateUserHash() {
        User u1 = new User("u1", "pass1", Template.ATTENDEE.getPermissions());
        User u2 = new User("u2", "pass2", Template.ATTENDEE.getPermissions());
        User spk1 = new User("spk1", "pass1", Template.SPEAKER.getPermissions());
        User spk2 = new User("spk2", "pass2", Template.SPEAKER.getPermissions());
        HashMap<String, User> users = new HashMap<>();
        users.put(u1.getUsername(), u1);
        users.put(u2.getUsername(), u2);
        users.put(spk1.getUsername(), spk1);
        users.put(spk2.getUsername(), spk2);
        return users;
    }


    @Test
    public void testWriteMessage(){
        String input = "user2" + System.lineSeparator() + "hello" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager(new HashMap<>());
        MessageController mc = new MessageController(mm, um, em);
        um.createUser("user", "123", Template.ATTENDEE.getPermissions());
        um.createUser("user2", "123", Template.ATTENDEE.getPermissions());
        mc.writeMessage("user");
        assertEquals(mm.retrieveUserInboxFor("user2","user").get(0).getContent(), "hello");
        assertEquals(Arrays.toString(mm.retrieveUserInboxFor("user2", "user").get(0).getRecipients()), "[user2]");
    }

    @Test
    public void testWriteEvents(){
        MessageManager mm = new MessageManager();
        EventManager em = new EventManager();
        UserManager um = new UserManager(new HashMap<>());
        MessageController mc = new MessageController(mm, um, em);
        ArrayList<String> participants = new ArrayList<>();
        Instant time = Instant.now();
        participants.add("user1");
        participants.add("user2");
        Event e1 = new Event("spk1", time, "Event", participants, "Meeting Room 1",  3);
        em.addEventToHash(e1);
        ArrayList<String> list = new ArrayList<>();
        list.add(e1.getEventName());
        //mc.writeToEvents("spk1", "hello", list);
    }

}





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
