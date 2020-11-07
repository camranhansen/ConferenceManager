package Events;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;


class EventTest {

    @org.junit.jupiter.api.Test
    void getSpeakername() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getSpeakername(), "Bob Smithers");
    }

    @org.junit.jupiter.api.Test
    void getEventTime() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        Instant time = e1.getEventTime();
    }

    @org.junit.jupiter.api.Test
    void getEventName() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getEventName(), "Test Event");
    }

    @org.junit.jupiter.api.Test
    void getParticipants() {
        List<String> arr = new ArrayList<>();
        arr.add("Kobe Bryant");
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getParticipants().size(),1);
    }

    @org.junit.jupiter.api.Test
    void getRoom() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getRoom(), "Meeting Room 1");
    }

    @org.junit.jupiter.api.Test
    void getCapacity() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getCapacity(), 2);
    }

    @org.junit.jupiter.api.Test
    void getId() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        Event e2 = new Event("Bob Smithers",time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getId(), 0);
        assertEquals(e2.getId(), 1);
    }
}