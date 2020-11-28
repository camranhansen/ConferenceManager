package csc.zerofoureightnine.conferencemanager.events;

// import Users.User;
// import Users.UserManager;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;

import static java.time.Instant.MAX;
import static org.junit.Assert.*;

public class EventControllerTest {

    @Test
    public void enroll() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1", 2);
        control.enroll("Meeting Room 1" + time.toString(), "John Smith");
        assertTrue(eventManager.getParticipants("Meeting Room 1" + time.toString()).contains("John Smith"));
    }

    @Test
    public void drop() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        List<String> arr = new ArrayList<>();
        arr.add("John Smith");
        Instant time = MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1", 2);
        control.enroll("Meeting Room 1"+time.toString(), "John Smith");
        assertEquals(1, eventManager.getParticipants("Meeting Room 1"+time.toString()).size());
        control.drop("Meeting Room 1"+time.toString(), "John Smith");
        assertEquals(0, eventManager.getParticipants("Meeting Room 1"+time.toString()).size());
    }

    @Test
    public void viewAllEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = MAX;
        control.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1", 2);
        control.createEvent("Rob Willis", time, "Test Event 2", "Meeting Room 2", 2);
        control.createEvent("Jane Doe", time, "Test Event 3", "Meeting Room 3", 2);
        List<String> result = control.viewAllEvents();
        assertEquals(3, result.size());

    }

    @Test
    public void checkMyEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = Instant.MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event",  "Meeting Room 1",  2);
        eventManager.createEvent("Rob Willis", time, "Test Event 2", "Meeting Room 2", 2);
        control.enroll("Meeting Room 1"+time.toString(), "Daniel Tan");
        List<String> result = control.viewMyEvents("Daniel Tan");
        assertEquals(1, result.size());
        assertEquals(1, result.size());
    }

    @Test
    public void viewAvailableEvents() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Janet Haws", time, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent("Roger", time2, "Test Event 3", "Meeting Room 3",  2);
        List<String> result = control.viewAvailableEvent("Micheal");
        assertEquals(3, result.size());
    }

    @Test
    public void createEvent() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        String speakerName = "Olivia";
        Instant time = MAX;
        String eventName = "Java Basics";
        String room = "Institute of Technology";
        int capacity = 2;
        control.createEvent(speakerName, time, eventName, room, capacity);
        assertEquals("Olivia", eventManager.getEventSpeakerName(room + time.toString()));
    }

    @Test
    public void deleteEvent() {
        EventManager eventManager = new EventManager();
        EventController control = new EventController(eventManager);
        Instant time =MAX;
        eventManager.createEvent("Bob Smithers", time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent("Roberto", time, "Test Event 2", "Meeting Room 2",  2);
        assertEquals(2, eventManager.getEventList().size());
        control.deleteEvent("Meeting Room 2" + time.toString());
        assertEquals(1, eventManager.getEventList().size());
    }

    @Test
    public void testViewHostingEvent() {
        EventManager eventManager = new EventManager();
        EventController eventController = new EventController(eventManager);
        eventController.createEvent("John", Instant.ofEpochSecond(1000), "Debugggggg", "BAH", 4);

        List<String> actual = eventController.viewHostingEvent("John");
        assertEquals(eventManager.getFormattedEvent(eventManager.getSpkEvents("John").get(0)), actual.get(0));
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