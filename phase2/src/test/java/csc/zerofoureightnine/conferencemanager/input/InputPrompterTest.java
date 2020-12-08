package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeDataHolder;
import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.RuntimeData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.options.*;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.*;

public class InputPrompterTest {

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

    //Option input tests
    @Test
    public void testMenuCategoryOption(){
        String input = "7\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.CATEGORY_MENU);
        assertEquals("0", response);
    }

    @Test
    public void testEventViewOption(){
        String input = "3\n2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.VIEWING_EVENT_OPTIONS);
        List<Option> options = new EventViewOptions().generateOptions();
        int num = Integer.parseInt(response);
        assertEquals("2", response);
        assertEquals("View my events" , options.get(num).toString());
    }

    @Test
    public void testEventEnrollOption(){
        String input = "7\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        List<Option> options = new EventEnrollOption().generateOptions();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.CATEGORY_MENU);
        int num = Integer.parseInt(response);
        assertEquals("0", response);
        assertEquals("Enroll in event" , options.get(num).toString());
    }

    @Test
    public void testMessageAllOption(){
        String input = "7\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        List<Option> options = new MessageAllOption().generateOptions();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.SEND_ALL_OPTIONS);
        int num = Integer.parseInt(response);
        assertEquals("0", response);
        assertEquals("Message all attendees" , options.get(num).toString());
    }

    @Test
    public void testMessageEventOption(){
        String input = "7\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        List<Option> options = new MessageEventOption().generateOptions();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.MESSAGE_EVENT_OPTIONS);
        int num = Integer.parseInt(response);
        assertEquals("1", response);
        assertEquals("Message one of your events" , options.get(num).toString());
    }

    @Test
    public void testMessageMoveOption(){
        String input = "7\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        List<Option> options = new MessageMoveOption().generateOptions();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.MOVE_MESSAGE);
        int num = Integer.parseInt(response);
        assertEquals("1", response);
        assertEquals("Move to unread" , options.get(num).toString());
    }

    @Test
    public void testMessageViewOption(){
        String input = "7\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        List<Option> options = new MessageViewOption().generateOptions();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.VIEWING_MESSAGE_OPTIONS);
        int num = Integer.parseInt(response);
        assertEquals("3", response);
        assertEquals("View unread messages" , options.get(num).toString());
    }

    @Test
    public void testUserTemplateOption(){
        String input = "7\n3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        List<Option> options = new UserTemplateOption().generateOptions();
        String response = inputPrompter.getValidMenuResponse(InputStrategy.USER_TEMPLATE_OPTIONS);
        int num = Integer.parseInt(response);
        assertEquals("3", response);
        assertEquals(Template.ADMIN , options.get(num).getTemplateHeld());
    }

    //Prompt response tests
    @Test
    public void testLongTextValidator(){
        String input = "I'm not typing 5000 characters...";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidResponse(InputStrategy.LONG_TEXT);
        assertEquals("I'm not typing 5000 characters...", response);
    }

    @Test
    public void testShortTextValidator(){
        String input = "i can probably type 150 though...... hello hello hello hello hello hello hello " +
                "hello hello hello hello hello hello hello hello hello hello hello " +
                "hello hello hello hello hello hello \nhi.";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidResponse(InputStrategy.SHORT_TEXT);
        assertEquals("hi.", response);
    }

    @Test
    public void testCapacityValidator(){
        String input = "2 maybe\n   10000000   ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_CAPACITY);
        assertEquals("10000000", response);
    }

    @Test
    public void testEventDayValidator(){
        String input = "400\n3000\n000\n42\n12";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_DAY);
        assertEquals("12", response);
    }

    @Test
    public void testEventHourValidator(){
        String input = "2 maybe\n   10000000   \n  7\n   9   ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = generateInputPrompter();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_HOUR);
        assertEquals("9", response);
    }

    @Test
    public void testEventIdValidator(){
        String input = "2 maybe\nMeeting Room 12020-12-02T09:00:00Z";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = inputPrompterWithEvent();
        String response = inputPrompter.getValidResponse(InputStrategy.VALID_EVENT_ID);
        assertEquals("Meeting Room 12020-12-02T09:00:00Z", response);
    }

    @Test
    public void testExistingUserValidator(){
        String input = "2 maybe\ngeorge";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = inputPrompterWithEvent();
        String response = inputPrompter.getValidResponse(InputStrategy.VALID_USERNAME);
        assertEquals("george", response);
    }

    @Test
    public void testEventSpeakerValidatorParty(){
        String input = "2 maybe\nPARTY   ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = inputPrompterWithEvent();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_SPEAKER_SINGLE);
        assertEquals("PARTY", response);
    }

    @Test
    public void testEventSpeakerValidatorSingle(){
        String input = "2 maybe\nspeaker";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = inputPrompterWithEvent();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_SPEAKER_SINGLE);
        assertEquals("speaker", response);
    }

    @Test
    public void testEventSpeakerValidatorTwo(){
        String input = "2 maybe\nspeaker, speaker2   ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = inputPrompterWithEvent();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_SPEAKER_SINGLE);
        assertEquals("speaker, speaker2", response);
    }

    @Test
    public void testEventRoomValidator(){
        String input = "Meeting Room 1\nNew Room  ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        InputPrompter inputPrompter = inputPrompterWithEvent();
        String response = inputPrompter.getValidResponse(InputStrategy.EVENT_ROOM);
        assertEquals("New Room", response);
    }
    private InputPrompter generateInputPrompter(){
        UserManager userManager = generateUserManager();
        EventManager eventManager = generateEventManager();
        PermissionManager permissionManager = generatePermissionManager();
        Map<InputStrategy, String> inputHistory = generateCreateUserHistory();
        String username = "george";
        InputStrategyManager inputStrategyManager = new InputStrategyManager(userManager, eventManager,
                permissionManager, inputHistory, username);
        DummyPersistentMap<String, RuntimeData> dummyPersistentMap = new DummyPersistentMap<>();
        RuntimeDataHolder runtimeDataHolder = new RuntimeDataHolder(username, dummyPersistentMap);
        return new InputPrompter(inputStrategyManager, runtimeDataHolder);
    }

    private UserManager generateUserManager() {
        DummyPersistentMap<String, UserData> map = new DummyPersistentMap<>();
        UserData u1 = new UserData();
        u1.setId("george");
        u1.setPassword( "123");
        UserData u2 = new UserData();
        u2.setId("bill");
        u2.setPassword( "password");
        map.put(u1.getId(), u1);
        map.put(u2.getId(), u2);
        return new UserManager(map);
    }

    private EventManager generateEventManager(){
        DummyPersistentMap<String, EventData> map = new DummyPersistentMap<>();
        return new EventManager(map);
    }

    private PermissionManager generatePermissionManager(){
        DummyPersistentMap<String, UserData> map = new DummyPersistentMap<>();
        UserData u1 = new UserData();
        u1.setId("george");
        u1.setPassword( "123");
        UserData u2 = new UserData();
        u2.setId("bill");
        u2.setPassword( "password");
        map.put(u1.getId(), u1);
        map.put(u2.getId(), u2);
        UserData u3 = new UserData("speaker", "password", Template.SPEAKER.getPermissions());
        UserData u4 = new UserData("speaker2", "password", Template.SPEAKER.getPermissions());
        map.put(u3.getId(), u3);
        map.put(u4.getId(), u4);
        return new PermissionManager(map);
    }

    private Map<InputStrategy, String> generateCreateUserHistory(){
        Map<InputStrategy, String> inputHistory = new HashMap<>();
        inputHistory.put(InputStrategy.EVENT_DAY, "2");
        inputHistory.put(InputStrategy.EVENT_HOUR, "9");
        return inputHistory;
    }

    private InputPrompter inputPrompterWithEvent(){
        EventManager eventManager = generateEventManager();
        List<String> list = Arrays.asList("Bob", "John");
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        eventManager.createEvent(list, time,"Test Event", "Meeting Room 1",  2);
        UserManager userManager = generateUserManager();
        PermissionManager permissionManager = generatePermissionManager();
        Map<InputStrategy, String> inputHistory = generateCreateUserHistory();
        String username = "Bob";
        InputStrategyManager inputStrategyManager = new InputStrategyManager(userManager, eventManager,
                permissionManager, inputHistory, username);
        DummyPersistentMap<String, RuntimeData> dummyPersistentMap = new DummyPersistentMap<>();
        RuntimeDataHolder runtimeDataHolder = new RuntimeDataHolder(username, dummyPersistentMap);
        return new InputPrompter(inputStrategyManager, runtimeDataHolder);
    }
}