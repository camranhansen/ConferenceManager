package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
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
import java.util.List;

import static org.junit.Assert.*;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;



public class MessageControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private static PersistentMap<String, EventData> pMap;
    private static PersistentMap<String, MessageData> mMap;
    private static SQLConfiguration config;



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
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
    }

    @Test
    public void performSelectedMessageAllAttTest() {
        String input = "hello" + System.lineSeparator() + "0" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageController.performSelectedAction("org1", Permission.MESSAGE_ALL_USERS);
        String messages = messageManager.wholeInboxToString("u1");
        String message = messageManager.singleInboxToString("u1", "org1");
        assertTrue(messages.contains("hello"));
        assertTrue(message.contains("hello") && message.contains("org1"));
    }

    @Test
    public void performSelectedMessageAllSpkTest() {
        String input = "hello" + System.lineSeparator() + "1" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
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

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageController.performSelectedAction("u1", Permission.MESSAGE_SINGLE_USER);
        String messages = messageManager.wholeInboxToString("u2");
        assertTrue(messages.contains("hello") && messages.contains("u1"));
    }

    @Test
    public void performSelectedMessageAllEventTest() {
        String input = "hello" + System.lineSeparator() + "0" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.now();
        List<String> spk = new ArrayList<>();
        spk.add("apk1");
        //eventManager.createEvent(spk, time, "talk1", "23", 2, EventType.SINGLE);
        //String eventId = "23" + time;
        //eventManager.enrollUser(eventId, "u2");
        //HashMap<String, User> users = generateUserHash();
        //UserManager userManager = new UserManager(users);
        //MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        //messageController.performSelectedAction("spk1", Permission.MESSAGE_EVENT_USERS);
        //String messages = messageManager.wholeInboxToString("u2");
        //assertTrue(messages.contains("hello") && messages.contains("spk1"));
        // TODO: fix this test.
    }

    @Test
    public void performSelectedMessageOneEventTest() {
        Instant time = Instant.now();
        String eventId = "23" + time;
        String input = "hello" + System.lineSeparator() + "1" + System.lineSeparator() + eventId + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        List<String> spk = new ArrayList<>();
        spk.add("spk1");
        //eventManager.createEvent(spk, time, "talk1", "23", 2, EventType.SINGLE);
        //eventManager.enrollUser(eventId, "u2");
        //HashMap<String, User> users = generateUserHash();
        //UserManager userManager = new UserManager(users);
        //MessageController messageController = new MessageController(messageManager, userManager, eventManager);
        //messageController.performSelectedAction("spk1", Permission.MESSAGE_EVENT_USERS);
        //String messages = messageManager.wholeInboxToString("u2");
        //assertTrue(messages.contains("hello") && messages.contains("spk1"));
    }

    @Test
    public void performSelectedViewAllMessagesTest() {
        String input = "0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageManager.sendMessage("u2", "hi", "u1");
        messageManager.sendMessage("u2", "how are you?", "u1");
        messageManager.sendMessage("spk1", "hello", "u1");
        messageController.performSelectedAction("u1", Permission.VIEW_SELF_MESSAGES);
        String out = "0. View all your messages\n" +
                "1. View messages from one user\n" + "2. View archived messages\n" + "3. View unread messages\n" +
                "spk1: hello, \n"+"u2: hi, how are you?, \n\n";
        //assertEquals(out, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void performSelectedViewFromMessagesTest() {
        String input = "1" + System.lineSeparator() + "u2" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageManager.sendMessage("u2", "hi", "u1");
        messageManager.sendMessage("u2", "how are you?", "u1");
        messageManager.sendMessage("spk1", "hello", "u1");
        messageController.performSelectedAction("u1", Permission.VIEW_SELF_MESSAGES);
        String out = "0. View all your messages\n" +
                "1. View messages from one user\n" + "2. View archived messages\n" + "3. View unread messages\n" +
                "Enter other username messages you'd like to see: \n"+
                "u2: hi, how are you?, \n";
        //assertEquals(out, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void performSelectedViewOtherMessageTest(){
        String input = "u1"+ System.lineSeparator()+"0" + System.lineSeparator() + "u2" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageManager.sendMessage("u2", "hi", "u1");
        messageManager.sendMessage("u2", "how are you?", "u1");
        messageManager.sendMessage("spk1", "hello", "u1");
        messageController.performSelectedAction("u1", Permission.VIEW_OTHER_MESSAGES);
        String out = "Enter username's messages you'd like to see: \n" + "0. View all your messages\n" +
                "1. View messages from one user\n"+ "2. View archived messages\n" + "3. View unread messages\n" +
                "spk1: hello, \n" + "u2: hi, how are you?, \n\n";
        //assertEquals(out, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void writeMessageTest() {
        String input = "u1" + System.lineSeparator() + "hello" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);

        messageController.writeMessage("u2");
        String message = messageManager.singleInboxToString("u1", "u2");
        assertTrue(message.contains("hello"));
    }

    @Test
    public void sendMessageTestToAllSpk() {
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageController.orgSendToAllSpeakers("u2", "hello");
        assertEquals(messageManager.retrieveUserInboxFor("spk1", "u2").get(0).getContent(), "hello");
    }

    @Test
    public void testSendMessageToAllAtt(){
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(mMap);
        EventManager em = new EventManager(pMap);
        UserManager um = new UserManager(new DummyPersistentMap<>());
        PermissionManager pm = new PermissionManager(new DummyPersistentMap<>());
        MessageController mc = new MessageController(mm, um, pm, em);
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
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageManager.sendMessage("u1", "hello", "u2");
        String messages = messageController.viewAllMessages("u2");
        System.out.println(messages);
    }

    @Test
    public void viewFromTest() {
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager messageManager = new MessageManager(mMap);
        EventManager eventManager = new EventManager(pMap);
        PersistentMap<String, UserData> users = generateUserMap();
        UserManager userManager = new UserManager(users);
        PermissionManager permissionManager = new PermissionManager((users));
        MessageController messageController = new MessageController(messageManager, userManager, permissionManager, eventManager);
        messageManager.sendMessage("u1", "hello", "u2");
        messageManager.sendMessage("u1", "how are you?", "u2");
        String messages = messageController.viewMessageFrom("u2", "u1");
        System.out.println(messages);
    }

    public PersistentMap<String, UserData> generateUserMap() {
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        UserData u1 = new UserData("u1", "pass1", Template.ATTENDEE.getPermissions());
        UserData u2 = new UserData("u2", "pass2", Template.ATTENDEE.getPermissions());
        UserData spk1 = new UserData("spk1", "pass1", Template.SPEAKER.getPermissions());
        UserData spk2 = new UserData("spk2", "pass2", Template.SPEAKER.getPermissions());
        PersistentMap<String, UserData> users = new DummyPersistentMap<>();
        users.put(u1.getId(), u1);
        users.put(u2.getId(), u2);
        users.put(spk1.getId(), spk1);
        users.put(spk2.getId(), spk2);
        return users;
    }


    @Test
    public void testWriteMessage(){
        String input = "user2" + System.lineSeparator() + "hello" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(mMap);
        EventManager em = new EventManager(pMap);
        UserManager um = new UserManager(new DummyPersistentMap<>());
        PermissionManager pm = new PermissionManager((new DummyPersistentMap<>()));
        MessageController mc = new MessageController(mm, um, pm, em);
        um.createUser("user", "123", Template.ATTENDEE.getPermissions());
        um.createUser("user2", "123", Template.ATTENDEE.getPermissions());
        mc.writeMessage("user");
        assertEquals(mm.retrieveUserInboxFor("user2","user").get(0).getContent(), "hello");
        assertEquals(Arrays.toString(mm.retrieveUserInboxFor("user2", "user").get(0).getRecipients()),
                "[user2]");
    }

    @Test
    public void testWriteEvents(){
        config = new SQLConfiguration("testfiles/db/data");
        mMap = new SQLMap<>(config, MessageData.class);
        MessageManager mm = new MessageManager(mMap);
        EventManager em = new EventManager(pMap);
        UserManager um = new UserManager(new DummyPersistentMap<>());
        PermissionManager pm = new PermissionManager((new DummyPersistentMap<>()));
        MessageController mc = new MessageController(mm, um, pm, em);
        ArrayList<String> participants = new ArrayList<>();
        Instant time = Instant.now();
        participants.add("user1");
        participants.add("user2");

        // TODO: implement changes.
        /*
        Event e1 = new Event("spk1", time, "Event", participants, "Meeting Room 1",  3);
        em.addEventToHash(e1);
        ArrayList<String> list = new ArrayList<>();
        list.add(e1.getEventName());
        */
    }
}
