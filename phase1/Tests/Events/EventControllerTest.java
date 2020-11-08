package Events;

import Users.User;
import Users.UserManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class EventControllerTest {

    @org.junit.jupiter.api.Test
    void viewAllEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", 2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", arr, "Meeting Room 2", 2);
        eventManager.createEvent("Jane Doe", time, "Test Event 3", arr, "Meeting Room 3", 2);
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
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr1 = new ArrayList<>();
        arr1.add("Daniel Tan");
        arr1.add("Cameron Blom");
        List<String> arr2 = new ArrayList<>();
        arr2.add("Cameron Blom");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1",  2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", arr2, "Meeting Room 2", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        List<Event> eventList = new ArrayList<>();
        eventList.add(e1);
        assertEquals(control.checkMyEvents("Daniel Tan"), eventList);
    }

    @org.junit.jupiter.api.Test
    void enroll() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        control.enroll(e1.getId(), "John Smith");
        assertTrue(control.checkMyEvents("John Smith").size() > 0);
    }

    @org.junit.jupiter.api.Test
    void drop() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        arr.add("John Smith");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        control.drop(e1.getId(), "John Smith");
        assertEquals(control.checkMyEvents("John Smith").size(), 0);
    }

    @org.junit.jupiter.api.Test
    void getEnrollEventInput() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Scanner input = new Scanner("Clean Architecture");
        String answer = control.getEnrollEventInput(input);
        assertEquals(answer, "Clean Architecture");
    }

    @org.junit.jupiter.api.Test
    void getDropEventInput() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Scanner input = new Scanner("Clean Architecture");
        String answer = control.getEnrollEventInput(input);
        assertEquals(answer, "Clean Architecture");
    }

    @org.junit.jupiter.api.Test
    void getViewEventListInputYes() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr, "Meeting Room 1", 2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", arr, "Meeting Room 2", 2);
        eventManager.createEvent("Jane Doe", time, "Test Event 3", arr, "Meeting Room 3", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        Event e2 = eventManager.getEventByName("Test Event 2");
        Event e3 = eventManager.getEventByName("Test Event 3");
        List<Event> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        list.add(e3);
        Scanner input = new Scanner("Yes");
        List<Event> answer = control.getViewEventListInput(input);
        assertEquals(answer, list);
    }

    @org.junit.jupiter.api.Test
    void getViewEventListInputNo() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Scanner input = new Scanner("No");
        List<Event> answer = control.getViewEventListInput(input);
        assertNull(answer);
    }

    @org.junit.jupiter.api.Test
    void getViewMyListInput() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr1 = new ArrayList<>();
        List<String> arr2 = new ArrayList<>();
        arr1.add("Jonathan Doe");
        arr2.add("Brianne Taylor");
        Instant time = Instant.now();
        eventManager.createEvent("Bob Smithers", time, "Test Event", arr1, "Meeting Room 1", 2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", arr2, "Meeting Room 2", 2);
        Event e1 = eventManager.getEventByName("Test Event");
        List<Event> list = new ArrayList<>();
        list.add(e1);
        Scanner username = new Scanner("Jonathan Doe");
        assertEquals(control.getViewMyListInput(username), list);
    }
}