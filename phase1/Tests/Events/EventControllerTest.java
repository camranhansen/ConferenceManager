package Events;

import Users.User;
import Users.UserManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventControllerTest {

    @org.junit.jupiter.api.Test
    void viewAllEvents() {
        HashMap<String, User> users = new HashMap<>();
        UserManager userManager = new UserManager(users);
        EventManager eventManager = new EventManager();
        EventController control = new EventController(userManager, eventManager);
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", "Title", 2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", arr, "Meeting Room 2", "Title1", 2);
        eventManager.createEvent("Jane Doe", time, "Test Event 3", arr, "Meeting Room 3", "Title2", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        Event e2 = eventManager.getEventByName("Test Event 2");
        Event e3 = eventManager.getEventByName("Test Event 3");
        List<Event> eventList = new ArrayList<>();
        eventList.add(e1);
        eventList.add(e2);
        eventList.add(e3);
        assertEquals(control.viewAllEvents(), eventList);
    }

    @org.junit.jupiter.api.Test
    void checkMyEvents() {
        HashMap<String, User> users = new HashMap<>();
        UserManager userManager = new UserManager(users);
        EventManager eventManager = new EventManager();
        EventController control = new EventController(userManager, eventManager);
        List<String> arr1 = new ArrayList<>();
        arr1.add("Daniel Tan");
        arr1.add("Cameron Blom");
        List<String> arr2 = new ArrayList<>();
        arr2.add("Cameron Blom");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1", "Title", 2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", arr2, "Meeting Room 2", "Title1", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        List<Event> eventList = new ArrayList<>();
        eventList.add(e1);
        assertEquals(control.checkMyEvents("Daniel Tan"), eventList);
    }

    @org.junit.jupiter.api.Test
    void enroll() {
        HashMap<String, User> users = new HashMap<>();
        UserManager userManager = new UserManager(users);
        EventManager eventManager = new EventManager();
        EventController control = new EventController(userManager, eventManager);
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", "Title", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        control.enroll(e1.getId(), "John Smith");
        assertTrue(control.checkMyEvents("John Smith").size() > 0);
    }

    @org.junit.jupiter.api.Test
    void drop() {
        HashMap<String, User> users = new HashMap<>();
        UserManager userManager = new UserManager(users);
        EventManager eventManager = new EventManager();
        EventController control = new EventController(userManager, eventManager);
        List<String> arr = new ArrayList<>();
        arr.add("John Smith");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", "Title", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        control.drop(e1.getId(), "John Smith");
        assertEquals(control.checkMyEvents("John Smith").size(), 0);
    }
}