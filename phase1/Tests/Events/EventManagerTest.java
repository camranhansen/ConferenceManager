package Events;
import org.junit.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventManagerTest {

    @Test
    public void getEventsList() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        EventManager event = new EventManager();
        event.addEventToHash(e1);
        List<Event> events = new ArrayList<>();
        events.add(e1);
        assertEquals(event.getEventsList(), events);
    }

    @Test
    public void getEventByName() {
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        Event e2 = new Event("Rob Willis", time, "Test Event 2", "Meeting Room 2",  2);
        Event e3 = new Event("Jane Doe", time, "Test Event 3", "Meeting Room 3",  2);
        EventManager event = new EventManager();
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        assertEquals(event.getEventByName("Test Event 2"), e2);
    }

    @Test
    public void getEventBySpeakerName() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        Event e2 = new Event("Bob Smithers", time2, "Test Event 2", "Meeting Room 1",  2);
        Event e3 = new Event("Josh Smith", time2, "Test Event 3", "Meeting Room 2",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        List<Event> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        assertEquals(event.getEventBySpeakerName("Bob Smithers").size(), 2);
    }

    @Test
    public void createEvent() {
        List<String> arr = new ArrayList<>();
        EventManager event = new EventManager();
        Instant time = Instant.now();
        event.createEvent("Bob Smithers", time,"Test Event", "Meeting Room 1",  2);
        assertTrue(event.getEventsList().size() > 0);
    }

    @Test
    public void deleteEvent() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Roberto", time, "Test Event 2", arr1, "Meeting Room 2",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        HashMap<String, Event> list = new HashMap<>();
        list.put(e1.getId(), e1);
        event.deleteEvent(e2.getId());
        assertEquals(event.getEvents(), list);
    }

    @Test
    public void getParticipants() {
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

    @Test
    public void enrollUser() {
        EventManager event = new EventManager();
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        Event e1 = new Event("Bob Smithers", time,"Test Event", arr, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.enrollUser(e1.getId(), name);
        assertEquals(e1.getParticipants().size(),1);
    }

    @Test
    public void dropUser() {
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

    @Test
    public void getInfo() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.getEvents().put(e1.getId(), e1);
        assertEquals(event.getInfo(e1.getId()), e1);
    }

    @Test
    public void getAvailableEvents() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        Instant time3 = time.plus(2, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        arr1.add("Micheal");
        List<String> arr2 = new ArrayList<>();
        List<String> arr3 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event",arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Janet Haws", time3, "Test Event 2", "Meeting Room 2",  2);
        Event e3 = new Event("Roger", time2, "Test Event 3", "Meeting Room 3",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        List<Event> list = new ArrayList<>();
        list.add(e3);
        assertEquals(event.getAvailableEvents("Micheal").size(), 2);
    }

    @Test
    public void addEventToHash() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        HashMap<String, Event> events = new HashMap<>();
        events.put(e1.getId(), e1);
        event.addEventToHash(e1);
        assertEquals(event.getEvents(), events);
    }

    @Test
    public void checkCapacity() {
        List<String> lst = new ArrayList<>();
        lst.add("John Stewart");
        EventManager event = new EventManager();
        assertTrue(event.checkCapacity(lst, 2));
    }

    @Test
    public void getUserEvents() {
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
        list.add(e2);
        list.add(e1);

        assertEquals(event.getUserEvents("Michael"), list);
    }

    @Test
    public void getSpkEvents() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        Instant time3 = time2.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        List<String> arr2 = new ArrayList<>();
        arr1.add("Michael");
        Event e1 = new Event("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        Event e2 = new Event("Bob Smithers", time2, "Test Event 2", "Meeting Room 2",  2);
        Event e3 = new Event("Roger", time3, "Test Event 3", "Meeting Room 3",  2);
        event.addEventToHash(e1);
        event.addEventToHash(e2);
        event.addEventToHash(e3);
        List<String> array = new ArrayList();
        array.add(e1.getId());
        array.add(e2.getId());
        assertEquals(event.getSpkEvents("Bob Smithers").size(), 2);
    }

    @Test
    public void editTime() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        event.editTime(e1.getId(), time2);
        assertEquals(e1.getEventTime(), time2);
    }

    @Test
    public void editSpeakerName() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editSpeakerName(e1.getId(), "Roberto");
        assertEquals(e1.getSpeakerName(), "Roberto");
    }

    @Test
    public void editEventName() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editEventName(e1.getId(), "Clean Architecture");
        assertEquals(e1.getEventName(), "Clean Architecture");
    }

    @Test
    public void editRoom() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        event.editRoom(e1.getId(), "BH 101");
        assertEquals(e1.getRoom(), "BH 101");
    }

    @Test
    public void editCapacity() {
        EventManager event = new EventManager();
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        event.addEventToHash(e1);
        System.out.println(event.getEvents());
        event.editCapacity(e1.getId(), 5);
        System.out.println(event.getEvents());
        assertEquals(5,e1.getCapacity());
    }

}