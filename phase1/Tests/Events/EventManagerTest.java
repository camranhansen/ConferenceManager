package Events;
import org.junit.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.*;
import static org.junit.Assert.*;

public class EventManagerTest {

    @Test
    public void getEventList() {
        Instant time = MAX;
        EventManager eventManager = new EventManager();
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        List<String> aList = new ArrayList<>();
        aList.add("Meeting Room 1" + time.toString());
        assertEquals(eventManager.getEventList(), aList);
    }

    @Test
    public void getSpkEvents() {
        EventManager eventManager = new EventManager();
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Bob Smithers", time2, "Test Event 2", "Meeting Room 1",  2);
        eventManager.createEvent("Josh Smith", time2, "Test Event 3", "Meeting Room 2",  2);
        List<String> aList = new ArrayList<>();
        aList.add("Meeting Room 1" + time2.toString());
        aList.add("Meeting Room 1" + time.toString());
        assertEquals(eventManager.getSpkEvents("Bob Smithers"), aList);
    }

    @Test
    public void createEvent() {
        EventManager eventManager = new EventManager();
        Instant time = MAX;
        eventManager.createEvent("Bob Smithers", time,"Test Event", "Meeting Room 1",  2);
        assertTrue(eventManager.getEventList().size()==1);
    }

    @Test
    public void deleteEvent() {
        EventManager eventManager = new EventManager();
        Instant time = MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Roberto", time, "Test Event 2", "Meeting Room 2",  2);
        assertTrue(eventManager.getEventList().size()==2);
        eventManager.deleteEvent("Meeting Room 2" + time.toString());
        assertTrue(eventManager.getEventList().size()==1);
    }

    @Test
    public void getParticipants() {
        EventManager eventManager = new EventManager();
        List<String> aList = new ArrayList<>();
        aList.add("Daniel Tan");
        aList.add("Cameron Blom");
        Instant time = MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(),"Daniel Tan" );
        eventManager.enrollUser("Meeting Room 1" + time.toString(),"Cameron Blom" );
        eventManager.getParticipants("Meeting Room 1" + time.toString());
        assertEquals(eventManager.getParticipants("Meeting Room 1" + time.toString()), aList);

    }

    @Test
    public void enrollUser() {
        EventManager eventManager = new EventManager();
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        arr.add(name);
        Instant time = MAX;
        eventManager.createEvent( "Bob Smithers", time,"Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(eventManager.getParticipants("Meeting Room 1" + time.toString()), arr);
    }

    @Test
    public void dropUser() {
        EventManager eventManager = new EventManager();
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        arr.add(name);
        Instant time = MAX;
        eventManager.createEvent( "Bob Smithers", time,"Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(eventManager.getParticipants("Meeting Room 1" + time.toString()).size(), 1);
        eventManager.dropUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(eventManager.getParticipants("Meeting Room 1" + time.toString()).size(), 0);
    }


    @Test
    public void getAvailableEvents() {
        EventManager eventManager = new EventManager();
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Janet Haws", time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent("Roger", time2, "Test Event 3", "Meeting Room 3",  2);
        eventManager.enrollUser("Meeting Room 2" + time2.toString(), "Micheal");
        eventManager.enrollUser("Meeting Room 3" + time2.toString(), "Micheal");
        assertEquals(eventManager.getAvailableEvents("Micheal").size(), 1);
    }

    @Test
    public void checkCapacity() {
        EventManager eventManager = new EventManager();
        Instant time = MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Sam");
        assertTrue(eventManager.checkCapacity("Meeting Room 1" + time.toString()));
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Camran");
        assertFalse(eventManager.checkCapacity("Meeting Room 1" + time.toString()));
    }

    @Test
    public void getUserEvents() {
        EventManager eventManager = new EventManager();
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        Instant time3 = time2.minus(1, ChronoUnit.HOURS);
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Janet Haws", time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent("Roger", time3, "Test Event 3", "Meeting Room 3",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Michael");
        eventManager.enrollUser("Meeting Room 2" + time2.toString(), "Michael");
        assertEquals(eventManager.getUserEvents("Michael").size(), 2);
        eventManager.enrollUser("Meeting Room 3" + time3.toString(), "Michael");
        assertEquals(eventManager.getUserEvents("Michael").size(), 3);
    }


    @Test
    public void editTime() {
        EventManager eventManager = new EventManager();
        Instant time = Instant.MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.editTime("Meeting Room 1" + time.toString(), time2);
        assertEquals(eventManager.getEventTime("Meeting Room 1" + time2.toString()), time2);
    }

    @Test
    public void editSpeakerName() {
        EventManager eventManager = new EventManager();
        Instant time = Instant.MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.editSpeakerName("Meeting Room 1" + time.toString(), "Roberto");
        assertEquals(eventManager.getEventSpeakerName("Meeting Room 1" + time.toString()), "Roberto");
    }

    @Test
    public void editEventName() {
        EventManager eventManager = new EventManager();
        Instant time = Instant.MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.editEventName("Meeting Room 1" + time.toString(), "Clean Architecture");
        assertEquals(eventManager.getEventName("Meeting Room 1" + time.toString()), "Clean Architecture");
    }

    @Test
    public void editRoom() {
        EventManager eventManager = new EventManager();
        Instant time = Instant.MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.editRoom("Meeting Room 1" + time.toString(), "BH 101");
        assertEquals(eventManager.getRoom("BH 101" + time.toString()),"BH 101");
    }

    @Test
    public void editCapacity() {
        EventManager eventManager = new EventManager();
        Instant time = Instant.MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.editCapacity("Meeting Room 1" + time.toString(), 5);
        assertEquals(eventManager.getCapacity("Meeting Room 1" + time.toString()), 5);
    }

}