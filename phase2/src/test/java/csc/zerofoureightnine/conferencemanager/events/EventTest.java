package csc.zerofoureightnine.conferencemanager.events;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import org.junit.Test;
import static org.junit.Assert.*;


public class EventTest {

    @Test
    public void getSpeakername() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        arr.add("Bob Smithers");
        Event e1 = new Event(arr,time, "Test Event", "Meeting Room 1",  2, EventType.SINGLE);
        assertEquals(arr, e1.getSpeakerName());
    }

    @Test
    public void getEventName() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        arr.add("Bob Smithers");
        Event e1 = new Event(arr,time, "Test Event", "Meeting Room 1",  2, EventType.SINGLE);
        assertEquals("Test Event", e1.getEventName() );
    }

    @Test
    public void getParticipants() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        arr.add("Bob Smithers");
        Event e1 = new Event(arr,time, "Test Event", "Meeting Room 1",  2, EventType.SINGLE);
        assertEquals(0,e1.getParticipants().size());
    }

    @Test
    public void getRoom() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        arr.add("Bob Smithers");
        Event e1 = new Event(arr,time, "Test Event", "Meeting Room 1",  2, EventType.SINGLE);
        assertEquals("Meeting Room 1", e1.getRoom());
    }

    @Test
    public void getCapacity() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        arr.add("Bob Smithers");
        Event e1 = new Event(arr,time, "Test Event", "Meeting Room 1",  2, EventType.SINGLE);
        assertEquals(2, e1.getCapacity());
    }

    @Test
    public void getId() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        arr.add("Bob Smithers");
        Event e1 = new Event(arr,time, "Test Event1", "Meeting Room 1",  2, EventType.SINGLE);
        Event e2 = new Event(arr,time, "Test Event2", "Meeting Room 2",  2, EventType.SINGLE);
        Event e3 = new Event(arr,time, "Test Event3", "Meeting Room 3",  2, EventType.SINGLE);
        Event e4 = new Event(arr,time, "Test Event4", "Meeting Room 4",  2, EventType.SINGLE);
        assertNotEquals(e1.getId(), e2.getId());
        assertNotEquals(e3.getId(), e4.getId());
        assertNotEquals(e1.getId(), e4.getId());

    }
}