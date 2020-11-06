package Events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerTest {

    @org.junit.jupiter.api.Test
    void getEventsList() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        EventManager event = new EventManager();
        event.addEventToHash(e1);
        List<Event> events = new ArrayList<>();
        events.add(e1);
        assertEquals(event.getEventsList(), events);
    }

    @org.junit.jupiter.api.Test
    void getEventByName() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        Event e2 = new Event("Rob Willis", time, "Test Event 2", arr, "Meeting Room 2",  2);
        Event e3 = new Event("Jane Doe", time, "Test Event 3", arr, "Meeting Room 3",  2);
        EventManager event = new EventManager();
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        assertEquals(event.getEventByName("Test Event 2"), e2);
    }

    @org.junit.jupiter.api.Test
    void createEvent() {
        List<String> arr = new ArrayList<>();
        EventManager event = new EventManager();
        Instant time = Instant.now();
        event.createEvent("Bob Smithers", time,"Test Event", arr, "Meeting Room 1",  2);
        assertTrue(event.getEventsList().size() > 0);
    }

    @org.junit.jupiter.api.Test
    void getParticipants() {
        List<String> arr = new ArrayList<>();
        List<String> arr1 = new ArrayList<>();
        arr.add("Daniel Tan");
        arr.add("Cameron Blom");
        arr1.add("Daniel Tan");
        arr1.add("Cameron Blom");
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        assertEquals(event.getParticipants(e1.getId()), arr1);
    }

    @org.junit.jupiter.api.Test
    void enrollUser() {
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time,"Test Event", arr, "Meeting Room 1",  2);
        EventManager event = new EventManager();
        event.enrollUser(e1.id, name);
        assertEquals(e1.participants.size(),1);
    }

    @org.junit.jupiter.api.Test
    void dropUser() {
        String name = "Micheal";
        EventManager event = new EventManager();
        List<String> arr = new ArrayList<>();
        arr.add("Micheal");
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        event.dropUser(e1.id, name);
        assertEquals(e1.participants.size(), 0);
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
    }

    @org.junit.jupiter.api.Test
    void getAvaliableevent() {
    }

    @org.junit.jupiter.api.Test
    void checkCapacity() {
        List<String> lst = new ArrayList<>();
        lst.add("John Stewart");
        EventManager event = new EventManager();
        assertTrue(event.checkCapacity(lst, 2));
    }

    @org.junit.jupiter.api.Test
    void checkConflict() {
        List<String> arr = new ArrayList<>();
        EventManager event = new EventManager();
        Instant time = Instant.now();
        event.createEvent("Bob Smithers", time,"Test Event", arr, "Meeting Room 1", 2);
        event.createEvent("Bob Smithers", time,"Test Event2", arr, "Meeting Room 2", 2);
        Event e1 = event.getEventByName("Test Event");
        assertTrue(event.checkConflict(e1));
    }
}