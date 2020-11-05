package Events;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @org.junit.jupiter.api.Test
    void getSpeakername() {
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getSpeakername(), "Bob Smithers");
    }

    @org.junit.jupiter.api.Test
    void getEventTime() {
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        Instant time = e1.getEventTime();
    }

    @org.junit.jupiter.api.Test
    void getEventName() {
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getEventName(), "Test Event");
    }

    @org.junit.jupiter.api.Test
    void getParticipants() {
        List<String> arr = new ArrayList<>();
        arr.add("Kobe Bryant");
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getParticipants().size(),1);
    }

    @org.junit.jupiter.api.Test
    void getRoom() {
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getRoom(), "Meeting Room 1");
    }

    @org.junit.jupiter.api.Test
    void getCapacity() {
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(e1.getCapacity(), 2);
    }

    @org.junit.jupiter.api.Test
    void getId() {
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", "Test Event", arr, "Meeting Room 1",  2);
        Event e2 = new Event("Kobe Bryant", "Test Event2", arr, "Meeting Room 2",  2);
        assertEquals(e1.id, 0);
        assertEquals(e2.id, 1);
    }
}