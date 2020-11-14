package Events;

// import Users.User;
// import Users.UserManager;

import Menus.SubController;
import Users.Permission;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
// import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class EventControllerTest {

    @Test
    public void viewAllEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1", 2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", "Meeting Room 2", 2);
        eventManager.createEvent("Jane Doe", time, "Test Event 3", "Meeting Room 3", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        Event e2 = eventManager.getEventByName("Test Event 2");
        Event e3 = eventManager.getEventByName("Test Event 3");
        List<Event> eventList = new ArrayList<>();
        eventList.add(e1);
        eventList.add(e2);
        eventList.add(e3);
        assertEquals(control.viewAllEvents(), eventList);
        EventPresenter ep = new EventPresenter();

    }

    @Test
    public void checkMyEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr1 = new ArrayList<>();
        arr1.add("Daniel Tan");
        arr1.add("Cameron Blom");
        List<String> arr2 = new ArrayList<>();
        arr2.add("Cameron Blom");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event",  "Meeting Room 1",  2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", "Meeting Room 2", 2);
        control.enroll("Meeting Room 1"+time.toString(), "Daniel Tan");
        Event e1 = eventManager.getEventByName("Test Event");
        List<Event> eventList = new ArrayList<>();
        eventList.add(e1);
        assertEquals(control.checkMyEvents("Daniel Tan"), eventList);
    }

    @Test
    public void enroll() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        control.enroll(e1.getId(), "John Smith");
        assertTrue(control.checkMyEvents("John Smith").size() > 0);
    }

    @Test
    public void drop() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        arr.add("John Smith");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        control.drop(e1.getId(), "John Smith");
        assertEquals(control.checkMyEvents("John Smith").size(), 0);
    }

    @Test
    public void viewAvailableEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = Instant.now();
        Instant time2 = time.plus(1, ChronoUnit.HOURS);
        List<String> arr1 = new ArrayList<>();
        List<String> arr2 = new ArrayList<>();
        List<String> arr3 = new ArrayList<>();
        arr3.add("Micheal");
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Janet Haws", time, "Test Event 2", arr2, "Meeting Room 2",  2);
        Event e3 = new Event("Roger", time2, "Test Event 3", arr3, "Meeting Room 3",  2);
        eventManager.addEventToHash(e1);
        eventManager.addEventToHash(e2);
        eventManager.addEventToHash(e3);
        List<Event> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        assertEquals(control.viewAvailableEvent("Michael"), list);
    }

    @Test
    public void addEvent() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        String speakerName = "Olivia";
        Instant time = Instant.now();
        String eventName = "Java Basics";
        String room = "Institute of Technology";
        int capacity = 2;
        control.addEvent(speakerName, time, eventName, room, capacity);
        assertTrue(eventManager.getEvents().size() > 0);
    }

    // @org.junit.jupiter.api.Test
    // void enrollSelf() {
    // }

    @Test
    public void deleteEvent() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = Instant.now();
        List<String> arr1 = new ArrayList<>();
        Event e1 = new Event("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        Event e2 = new Event("Roberto", time, "Test Event 2", arr1, "Meeting Room 2",  2);
        eventManager.addEventToHash(e1);
        eventManager.addEventToHash(e2);
        HashMap<String, Event> list = new HashMap<>();
        list.put(e1.getId(), e1);
        control.deleteEvent(e2.getId());
        assertEquals(eventManager.getEvents(), list);
    }

    /*
    @org.junit.jupiter.api.Test
    void getSpeakerNameInput() {
    }

    @org.junit.jupiter.api.Test
    void getEventNameInput() {
    }

    @org.junit.jupiter.api.Test
    void getRoomInput() {
    }

    @org.junit.jupiter.api.Test
    void getCapacityInput() {
    }

    @org.junit.jupiter.api.Test
    void editSpeakerName() {
    }

    @org.junit.jupiter.api.Test
    void editEventName() {
    }

    @org.junit.jupiter.api.Test
    void editRoom() {
    }

    @org.junit.jupiter.api.Test
    void editCapacity() {
    }

    @org.junit.jupiter.api.Test
    void performSelectedActionSelfEnroll() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);

    }

     */
}