package csc.zerofoureightnine.conferencemanager.events;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import org.junit.Test;


public class EventTest {

    @Test
    public void getSpeakername() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // Event e1 = new Event("Bob Smithers",time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        // assertEquals("Bob Smithers", e1.getSpeakerName());
    }

    @Test
    public void getEventTime() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // Event e1 = new Event("Bob Smithers",time, "Test Event", "Meeting Room 1",  2); TODO: implement changes.
        // Instant time1 = e1.getEventTime();
    }

    @Test
    public void getEventName() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // Event e1 = new Event("Bob Smithers",time, "Test Event", "Meeting Room 1",  2);
        // assertEquals("Test Event", e1.getEventName() );
    }

    @Test
    public void getParticipants() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // Event e1 = new Event("Bob Smithers",time, "Test Event", "Meeting Room 1",  2);
        // assertEquals(0,e1.getParticipants().size());
    }

    @Test
    public void getRoom() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // Event e1 = new Event("Bob Smithers",time, "Test Event", "Meeting Room 1",  2);
        // assertEquals("Meeting Room 1", e1.getRoom());
    }

    @Test
    public void getCapacity() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // Event e1 = new Event("Bob Smithers",time, "Test Event", "Meeting Room 1",  2);
        // assertEquals(2, e1.getCapacity());
    }

    @Test
    public void getId() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        // TODO: implement changes.
        /*
        Event e1 = new Event("Bob Smithers1",time, "Test Event1", "Meeting Room 1",  2);
        Event e2 = new Event("Bob Smithers2",time, "Test Event2", "Meeting Room 2",  2);
        Event e3 = new Event("Bob Smithers3",time, "Test Event3", "Meeting Room 3",  2);
        Event e4 = new Event("Bob Smithers4",time, "Test Event4", "Meeting Room 4",  2);
        assertNotEquals(e1.getId(), e2.getId());
        assertNotEquals(e3.getId(), e4.getId());
        assertNotEquals(e1.getId(), e4.getId());
        */

    }
}