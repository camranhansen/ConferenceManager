package Events;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        event.addEventToHash(e3);
        assertEquals(event.getEventByName("Test Event 2"), e2);
    }

    @org.junit.jupiter.api.Test
    void getEventBySpeakerName() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Bob Smithers", time2, "Test Event 2", arr1, "Meeting Room 1",  2);
        Event e3 = new Event("Josh Smith", time2, "Test Event 3", arr1, "Meeting Room 2",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        List<Event> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        assertEquals(event.getEventBySpeakerName("Bob Smithers"), list);
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
    void deleteEvent() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Roberto", time, "Test Event 2", arr1, "Meeting Room 2",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        HashMap<Integer, Event> list = new HashMap<>();
        list.put(e1.getId(), e1);
        event.deleteEvent(e2.getId());
        assertEquals(event.events, list);
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
        event.addEventToHash(e1);
        assertEquals(event.getParticipants(e1.getId()), arr1);
    }

    @org.junit.jupiter.api.Test
    void enrollUser() {
        EventManager event = new EventManager();
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time,"Test Event", arr, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.enrollUser(e1.getId(), name);
        assertEquals(e1.getParticipants().size(),1);
    }

    @org.junit.jupiter.api.Test
    void dropUser() {
        String name = "Micheal";
        EventManager event = new EventManager();
        List<String> arr = new ArrayList<>();
        arr.add("Micheal");
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.dropUser(e1.getId(), name);
        assertEquals(e1.getParticipants().size(), 0);
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.events.put(e1.getId(), e1);
        assertEquals(event.getInfo(e1.getId()), e1);
    }

    @org.junit.jupiter.api.Test
    void getAvailableEvents() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        arr1.add("Micheal");
        List<String> arr2 = new ArrayList<>();
        List<String> arr3 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Janet Haws", time, "Test Event 2", arr2, "Meeting Room 2",  2);
        Event e3 = new Event("Roger", time2, "Test Event 3", arr3, "Meeting Room 3",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        List<Event> list = new ArrayList<>();
        list.add(e3);
        assertEquals(event.getAvailableEvents("Michael"), list);
    }

    @org.junit.jupiter.api.Test
    void addEventToHash() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        HashMap<Integer, Event> events = new HashMap<>();
        events.put(e1.getId(), e1);
        event.addEventToHash(e1);
        assertEquals(event.events, events);
    }

    @org.junit.jupiter.api.Test
    void checkCapacity() {
        List<String> lst = new ArrayList<>();
        lst.add("John Stewart");
        EventManager event = new EventManager();
        assertTrue(event.checkCapacity(lst, 2));
    }

    @org.junit.jupiter.api.Test
    void getUserEvents() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        Instant time3 = time2.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        List<String> arr2 = new ArrayList<>();
        arr1.add("Michael");
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Janet Haws", time2, "Test Event 2", arr1, "Meeting Room 2",  2);
        Event e3 = new Event("Roger", time3, "Test Event 3", arr2, "Meeting Room 3",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        List<Event> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        assertEquals(event.getUserEvents("Michael"), list);
    }

    @org.junit.jupiter.api.Test
    void getSpkEvents() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        Instant time3 = time2.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        List<String> arr2 = new ArrayList<>();
        arr1.add("Michael");
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Bob Smithers", time2, "Test Event 2", arr1, "Meeting Room 2",  2);
        Event e3 = new Event("Roger", time3, "Test Event 3", arr2, "Meeting Room 3",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        Integer[] array = new Integer[2];
        array[0] = e1.getId();
        array[1] = e2.getId();
        assertEquals(event.getSpkEvents("Bob Smithers"), array);
    }

    @org.junit.jupiter.api.Test
    void editTime() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        event.editTime(e1.getId(), time2);
        assertEquals(e1.getEventTime(), time2);
    }

    @org.junit.jupiter.api.Test
    void editSpeakerName() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editSpeakerName(e1.getId(), "Roberto");
        assertEquals(e1.getSpeakerName(), "Roberto");
    }

    @org.junit.jupiter.api.Test
    void editEventName() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editEventName(e1.getId(), "Clean Architecture");
        assertEquals(e1.getEventName(), "Clean Architecture");
    }

    @org.junit.jupiter.api.Test
    void editRoom() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editRoom(e1.getId(), "BH 101");
        assertEquals(e1.getRoom(), "BH 101");
    }

    @org.junit.jupiter.api.Test
    void editCapacity() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editCapacity(e1.getId(), 5);
        assertEquals(e1.getCapacity(), 5);
    }

}