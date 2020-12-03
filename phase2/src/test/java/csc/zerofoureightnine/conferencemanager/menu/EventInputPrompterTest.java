package csc.zerofoureightnine.conferencemanager.menu;
import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.events.EventType;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import csc.zerofoureightnine.conferencemanager.users.User;
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
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class EventInputPrompterTest {
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
    public void canEnrollInCorrectInputTest(){
        String input = "Main Room2020-12-02T09:00:00Z\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String username = "attendee1";
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        createEvent(eventManager, userManager);
        String yes = eventInputPrompter.canEnrollIn(username);
        assertEquals("Main Room2020-12-02T09:00:00Z", yes);
    }

    @Test
    public void canEnrollInInvalidInputTest(){
        String input = "30Dogs\nMain Room2020-12-02T09:00:00Z\nMain Room2020-12-04T09:00:00Z";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String username = "attendee1";
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        createEvent(eventManager, userManager);
        eventManager.enrollUser("Main Room2020-12-02T09:00:00Z", username);
        createEvent2(eventManager, userManager);
        String yes = eventInputPrompter.canEnrollIn(username);
        assertEquals("Main Room2020-12-04T09:00:00Z", yes);
    }

    @Test
    public void canEnrollInFullTest(){
        String input = "Main Room2020-12-05T09:00:00Z\nMain Room2020-12-04T09:00:00Z";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String username = "attendee1";
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        createEvent3(eventManager, userManager);
        eventManager.enrollUser("Main Room2020-12-05T09:00:00Z", username);
        createEvent2(eventManager, userManager);
        String yes = eventInputPrompter.canEnrollIn("attendee2");
        eventManager.enrollUser(yes, "attendee2");
        assertEquals("Main Room2020-12-04T09:00:00Z", yes);
        assertTrue(eventManager.getParticipants("Main Room2020-12-04T09:00:00Z").contains("attendee2"));
    }

    @Test
    public void canEnrollInAtSameTimeTest(){
        String input = "Pool2020-12-05T09:00:00Z\nMain Room2020-12-04T09:00:00Z";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String username = "attendee1";
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        createEvent4(eventManager, userManager);
        createEvent3(eventManager, userManager);
        eventManager.enrollUser("Main Room2020-12-05T09:00:00Z", username);
        createEvent2(eventManager, userManager);
        String yes = eventInputPrompter.canEnrollIn(username);
        eventManager.enrollUser(yes, "attendee2");
        assertEquals("Main Room2020-12-04T09:00:00Z", yes);
        assertTrue(eventManager.getParticipants("Main Room2020-12-04T09:00:00Z").contains("attendee2"));
    }


    @Test
    public void pickSingleSpeakerCorrectInputTest(){
        String input = "spk1" + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        assertEquals("spk1", speaker);
    }

    @Test
    public void pickSingleSpeakerDoesNotExistTest(){
        String input = "spk3\nspk1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        assertEquals("spk1", speaker);
    }

    @Test
    public void pickSingleSpeakerNotSpeakerTest(){
        String input = "attendee1\nspk1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        assertEquals("spk1", speaker);
    }

    @Test
    public void pickSingleSpeakerConflictTest(){
        String input = "spk1\nspk2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        createEvent(eventManager, userManager);
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        assertEquals("spk2", speaker);
    }

    @Test
    public void pickEventTimeCorrectInputTest(){
        String input = "2\n9\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = eventInputPrompter.pickEventTime();
        assertEquals(Instant.parse("2020-12-02T09:00:00Z"), time);
    }

    @Test
    public void pickEventTimeInvalidInputTest(){
        String input = "1000\n2\n9000\n9";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = eventInputPrompter.pickEventTime();
        assertEquals(Instant.parse("2020-12-02T09:00:00Z"), time);
    }

    @Test
    public void pickEventNameTest(){
        String input = "Beginner Knitting";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        String name = eventInputPrompter.pickEventName();
        assertEquals("Beginner Knitting", name);
    }

    @Test
    public void pickUnbookedRoomTest(){
        String input = "Main Room";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        String room = eventInputPrompter.pickEventRoom(time);
        assertEquals("Main Room", room);
    }

    @Test
    public void pickBookedRoomTest(){
        String input = "Main Room\nSecond Room";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        createEvent(eventManager, userManager);
        Instant time = Instant.parse("2020-12-02T09:00:00Z");
        String room = eventInputPrompter.pickEventRoom(time);
        assertEquals("Second Room", room);
    }

    @Test
    public void pickValidCapacityTest(){
        String input = "4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        int capacity = eventInputPrompter.pickEventCapacity();
        assertEquals(4, capacity);
    }

    @Test
    public void pickInvalidCapacityTest(){
        String input = "1000\n500000\nMonkey\n2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventManager eventManager = new EventManager();
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        int capacity = eventInputPrompter.pickEventCapacity();
        assertEquals(2, capacity);
    }

    private HashMap<String, User> generateUserHash() {
        User u1 = new User("attendee1", "pass1", Template.ATTENDEE.getPermissions());
        User u2 = new User("attendee2", "pass2", Template.ATTENDEE.getPermissions());
        User spk1 = new User("spk1", "pass1", Template.SPEAKER.getPermissions());
        User spk2 = new User("spk2", "pass2", Template.SPEAKER.getPermissions());
        HashMap<String, User> users = new HashMap<>();
        users.put(u1.getUsername(), u1);
        users.put(u2.getUsername(), u2);
        users.put(spk1.getUsername(), spk1);
        users.put(spk2.getUsername(), spk2);
        return users;
    }

    private void createEvent(EventManager eventManager, UserManager userManager){
        String input = "2\n9\nBeginner Knitting\nspk1\nMain Room\n5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = eventInputPrompter.pickEventTime();
        String name = eventInputPrompter.pickEventName();
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        String room = eventInputPrompter.pickEventRoom(time);
        int capacity = eventInputPrompter.pickEventCapacity();
        List<String> speakers = new ArrayList<>();
        speakers.add(speaker);
        EventType eventType = EventType.SINGLE;
        eventManager.createEvent(speakers, time, name, room, capacity, eventType);
    }

    private void createEvent2(EventManager eventManager, UserManager userManager){
        String input = "4\n9\nBeginner Knitting\nspk1\nMain Room\n5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = eventInputPrompter.pickEventTime();
        String name = eventInputPrompter.pickEventName();
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        String room = eventInputPrompter.pickEventRoom(time);
        int capacity = eventInputPrompter.pickEventCapacity();
        List<String> speakers = new ArrayList<>();
        speakers.add(speaker);
        EventType eventType = EventType.SINGLE;
        eventManager.createEvent(speakers, time, name, room, capacity, eventType);
    }

    private void createEvent3(EventManager eventManager, UserManager userManager){
        String input = "5\n9\nPrivate Knitting\nspk1\nMain Room\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = eventInputPrompter.pickEventTime();
        String name = eventInputPrompter.pickEventName();
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        String room = eventInputPrompter.pickEventRoom(time);
        int capacity = eventInputPrompter.pickEventCapacity();
        List<String> speakers = new ArrayList<>();
        speakers.add(speaker);
        EventType eventType = EventType.SINGLE;
        eventManager.createEvent(speakers, time, name, room, capacity, eventType);
    }

    private void createEvent4(EventManager eventManager, UserManager userManager){
        String input = "5\n9\nPool Party\nspk2\nPool\n2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        EventInputPrompter eventInputPrompter = new EventInputPrompter(eventManager);
        Instant time = eventInputPrompter.pickEventTime();
        String name = eventInputPrompter.pickEventName();
        String speaker = eventInputPrompter.pickSingleSpeaker(userManager, time);
        String room = eventInputPrompter.pickEventRoom(time);
        int capacity = eventInputPrompter.pickEventCapacity();
        List<String> speakers = new ArrayList<>();
        speakers.add(speaker);
        EventType eventType = EventType.SINGLE;
        eventManager.createEvent(speakers, time, name, room, capacity, eventType);
    }
}
