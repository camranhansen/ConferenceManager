package csc.zerofoureightnine.conferencemanager.events;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import org.junit.BeforeClass;
import org.junit.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.*;
import static org.junit.Assert.*;

public class EventManagerTest {
    private static SQLConfiguration config;
    private static  PersistentMap<String, EventData> pMap;

    @BeforeClass
    public static void setup() {
        config = new SQLConfiguration("testfiles/db/data");
        pMap = new SQLMap<>(config, EventData.class);
    }

    @Test
    public void getEventList() {
        Instant time = MAX;
        EventManager eventManager = new EventManager(pMap);
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        List<String> aList = new ArrayList<>();
        aList.add("Meeting Room 1" + time.toString());
        // assertEquals(aList, eventManager.getEventList()); TODO: implement changes.
    }

    @Test
    public void getSpkEvents() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        //TODO: implement changes.
        /*
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Bob Smithers", time2, "Test Event 2", "Meeting Room 1",  2);
        eventManager.createEvent("Josh Smith", time2, "Test Event 3", "Meeting Room 2",  2);
        */
        List<String> aList = new ArrayList<>();
        aList.add("Meeting Room 1" + time2.toString());
        aList.add("Meeting Room 1" + time.toString());
        assertEquals(aList, eventManager.getSpkEvents("Bob Smithers"));
    }

    @Test
    public void createEvent() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        // eventManager.createEvent("Bob Smithers", time,"Test Event", "Meeting Room 1",  2); TODO: implement changes.
        // assertEquals(1, eventManager.getEventList().size()); TODO: implement changes.
    }

    @Test
    public void deleteEvent() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        // eventManager.createEvent("Roberto", time, "Test Event 2", "Meeting Room 2",  2); TODO: implement changes.
        // assertEquals(2, eventManager.getEventList().size()); TODO: implement changes.
        eventManager.deleteEvent("Meeting Room 2" + time.toString());
        // assertEquals(1,eventManager.getEventList().size()); TODO: implement changes.
    }

    @Test
    public void getParticipants() {
        EventManager eventManager = new EventManager(pMap);
        List<String> aList = new ArrayList<>();
        aList.add("Daniel Tan");
        aList.add("Cameron Blom");
        Instant time = MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.enrollUser("Meeting Room 1" + time.toString(),"Daniel Tan" );
        eventManager.enrollUser("Meeting Room 1" + time.toString(),"Cameron Blom" );
        eventManager.getParticipants("Meeting Room 1" + time.toString());
        assertEquals(aList, eventManager.getParticipants("Meeting Room 1" + time.toString()));

    }

    @Test
    public void enrollUser() {
        EventManager eventManager = new EventManager(pMap);
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        arr.add(name);
        Instant time = MAX;
        // eventManager.createEvent( "Bob Smithers", time,"Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(arr, eventManager.getParticipants("Meeting Room 1" + time.toString()));
    }

    @Test
    public void dropUser() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        // eventManager.createEvent( "Bob Smithers", time,"Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(1, eventManager.getParticipants("Meeting Room 1" + time.toString()).size());
        eventManager.dropUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(0, eventManager.getParticipants("Meeting Room 1" + time.toString()).size());
    }


    @Test
    public void getAvailableEvents() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        // TODO: implement changes.
        /*
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Janet Haws", time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent("Roger", time2, "Test Event 3", "Meeting Room 3",  2);
        */
        eventManager.enrollUser("Meeting Room 2" + time2.toString(), "Micheal");
        eventManager.enrollUser("Meeting Room 3" + time2.toString(), "Micheal");
        assertEquals(1, eventManager.getAvailableEvents("Micheal").size());
    }

    @Test
    public void isEventFull() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Sam");
        assertFalse(eventManager.isEventFull("Meeting Room 1" + time.toString()));
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Camran");
        assertTrue(eventManager.isEventFull("Meeting Room 1" + time.toString()));
    }

    @Test
    public void getUserEvents() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        Instant time3 = time2.minus(1, ChronoUnit.HOURS);
        // TODO: implement changes.
        /*
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Janet Haws", time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent("Roger", time3, "Test Event 3", "Meeting Room 3",  2);
        */
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Michael");
        eventManager.enrollUser("Meeting Room 2" + time2.toString(), "Michael");
        assertEquals(2, eventManager.getUserEvents("Michael").size());
        eventManager.enrollUser("Meeting Room 3" + time3.toString(), "Michael");
        assertEquals(3, eventManager.getUserEvents("Michael").size());
    }


    @Test
    public void editTime() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.editTime("Meeting Room 1" + time.toString(), time2);
        assertEquals(time2, eventManager.getEventTime("Meeting Room 1" + time2.toString()));
    }

    @Test
    public void editSpeakerName() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        // eventManager.editSpeakerName("Meeting Room 1" + time.toString(), "Roberto"); TODO: implement changes.
        assertEquals("Roberto", eventManager.getEventSpeakerName("Meeting Room 1" + time.toString()));
    }

    @Test
    public void editEventName() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.editEventName("Meeting Room 1" + time.toString(), "Clean Architecture");
        assertEquals("Clean Architecture", eventManager.getEventName("Meeting Room 1" + time.toString()));
    }

    @Test
    public void editRoom() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.editRoom("Meeting Room 1" + time.toString(), "BH 101");
        assertEquals("BH 101", eventManager.getRoom("BH 101" + time.toString()));
    }

    @Test
    public void editCapacity() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        // eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        eventManager.editCapacity("Meeting Room 1" + time.toString(), 5);
        assertEquals(5, eventManager.getCapacity("Meeting Room 1" + time.toString()));
    }

}