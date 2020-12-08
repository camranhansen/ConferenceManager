package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.Instant.MAX;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class EventManagerTest {
    private static DummyPersistentMap<String, EventData> pMap = new DummyPersistentMap<>();

    @Test
    public void createEventNoType() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time,"Test Event", "Meeting Room 1",  2);
        assertEquals(1, eventManager.getAllEventIds().size());

        Event newEvent = new Event(list, time, "Test Event", "Meeting Room 1", 2,
                eventManager.getEventTypeForCapacity(2));
        EventData expectedData = eventManager.convertEventToEventData(newEvent);
        EventData actual = eventManager.getDataById("Meeting Room 1"+time.toString());
        assertEquals(expectedData, actual);
    }

    @Test
    public void createEventWithType() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time,"Test Event", "Meeting Room 1",  2, EventType.MULTI);
        eventManager.createEvent(list, time,"Test Event", "Meeting Room 2",  2, EventType.MULTI);

        assertEquals(2, eventManager.getAllEventIds().size());

        Event newEvent = new Event(list, time, "Test Event", "Meeting Room 1", 2,
                EventType.MULTI);
        EventData expectedData = eventManager.convertEventToEventData(newEvent);

        EventData actual = eventManager.getDataById("Meeting Room 1"+time.toString());
        assertEquals(expectedData, actual);
    }

    @Test
    public void createEditedEvent() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        List<String> speakerList = Arrays.asList("Bob", "John");
        List<String> participantsList = Arrays.asList("Cool", "Mike");
        eventManager.createEditedEvent(speakerList, time,"Test Event", participantsList,
                "Meeting Room 1", 2, EventType.MULTI);

        Event newEvent = new Event(speakerList, participantsList, time, "Test Event", "Meeting Room 1", 2,
                EventType.MULTI);
        EventData expectedData = eventManager.convertEventToEventData(newEvent);
        expectedData.addParticipants(participantsList);
        EventData actual = eventManager.getDataById("Meeting Room 1"+time.toString());
        assertEquals(expectedData, actual);
    }

    @Test
    public void deleteEvent() {
        EventManager eventManager = new EventManager(pMap);
        eventManager.clear();
        Instant time1 = MAX;
        Instant time2 = Instant.ofEpochMilli(1024);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Roger");
        eventManager.createEvent(list, time1, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);

        Event newEvent1 = new Event(list, time1, "Test Event", "Meeting Room 1",  2,
                eventManager.getEventTypeForCapacity(2));
        EventData expectedData = eventManager.convertEventToEventData(newEvent1);
        assertEquals(2, eventManager.getAllEventIds().size());

        eventManager.deleteEvent("Meeting Room 2" + time2.toString());
        assertEquals(1, eventManager.getAllEventIds().size());
        assertEquals(1, eventManager.size());
        assertEquals(null, eventManager.getDataById("Meeting Room 2" + time2.toString()));
    }


    @Test
    public void getAllEventIds() { // Don't know if this will be a problem, but two lists are not equal if items are ordered differently
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        List<String> list3 = Arrays.asList("Roger");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent(list3, time2, "Test Event 3", "Meeting Room 3",  2);
        String e1id = "Meeting Room 1" + time.toString();
        String e2id = "Meeting Room 2" + time2.toString();
        String e3id = "Meeting Room 3" + time2.toString();
        List<String> idList = Arrays.asList(e1id, e2id, e3id);
        Set<String> l1 = new HashSet<String>(idList);
        Set<String> l2 = new HashSet<String>(eventManager.getAllEventIds());
        assertEquals(l1, l2);
    }

    @Test
    public void getParticipants() {
        EventManager eventManager = new EventManager(pMap);
        List<String> aList = new ArrayList<>();
        aList.add("Daniel Tan");
        aList.add("Cameron Blom");
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(),"Daniel Tan" );
        eventManager.enrollUser("Meeting Room 1" + time.toString(),"Cameron Blom" );
        eventManager.getParticipants("Meeting Room 1" + time.toString());
        assertEquals(aList, eventManager.getParticipants("Meeting Room 1" + time.toString()));

    }

    @Test
    public void enrollUser() {
        EventManager eventManager = new EventManager(pMap);
        String name = "Micheal";
        List<String> arr = new ArrayList<>();
        arr.add(name);
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time,"Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(arr, eventManager.getParticipants("Meeting Room 1" + time.toString()));

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertTrue(ed.getParticipants().contains(arr.get(0)));
    }

    @Test
    public void dropUser() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent( list, time,"Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Micheal");
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "David");
        assertEquals(2, eventManager.getParticipants("Meeting Room 1" + time.toString()).size());
        eventManager.dropUser("Meeting Room 1" + time.toString(), "Micheal");
        assertEquals(1, eventManager.getParticipants("Meeting Room 1" + time.toString()).size());

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertFalse(ed.getParticipants().contains("Micheal"));
        assertTrue(ed.getParticipants().contains("David"));
    }


    @Test
    public void getAvailableEvents() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        List<String> list3 = Arrays.asList("Roger");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent(list3, time2, "Test Event 3", "Meeting Room 3",  2);
        eventManager.enrollUser("Meeting Room 2" + time2.toString(), "Micheal");
        eventManager.enrollUser("Meeting Room 3" + time2.toString(), "Micheal");
        assertEquals(1, eventManager.getAvailableEvents("Micheal").size());
    }

    @Test
    public void eventExists() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent( list, time,"Test Event", "Meeting Room 1",  2);
        assertTrue(eventManager.eventExists("Meeting Room 1" + time.toString()));
    }

    @Test
    public void isEventFull() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Sam");
        assertFalse(eventManager.isEventFull("Meeting Room 1" + time.toString()));
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Camran");
        assertTrue(eventManager.isEventFull("Meeting Room 1" + time.toString()));
    }

    @Test
    public void getUserEvents() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        Instant time3 = time2.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        List<String> list3 = Arrays.asList("Roger");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent(list3, time3, "Test Event 3", "Meeting Room 3",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "Michael");
        eventManager.enrollUser("Meeting Room 2" + time2.toString(), "Michael");
        assertEquals(2, eventManager.getUserEvents("Michael").size());
        eventManager.enrollUser("Meeting Room 3" + time3.toString(), "Michael");
        assertEquals(3, eventManager.getUserEvents("Michael").size());
    }


    @Test
    public void editTime() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.editTime("Meeting Room 1" + time.toString(), time2);
        assertEquals(time2, eventManager.getEventTime("Meeting Room 1" + time2.toString()));

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertEquals(time2, ed.getTime());
    }

    @Test
    public void editSpeakerName() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        List<String> list2 = Arrays.asList("Roberto");
        List<String> list3 = Arrays.asList("Roberto");
        eventManager.editSpeakerName("Meeting Room 1" + time.toString(), list2);
        assertEquals(list3, eventManager.getEventSpeakerName("Meeting Room 1" + time.toString()));

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertTrue(ed.getSpeakers().contains(list3.get(0)));
    }

    @Test
    public void editEventName() {
        pMap = new DummyPersistentMap<>();
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.editEventName("Meeting Room 1" + time.toString(), "Clean Architecture");
        assertEquals("Clean Architecture", eventManager.getEventName("Meeting Room 1" + time.toString()));

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertEquals("Clean Architecture", ed.getEventName());
    }

    @Test
    public void editRoom() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.editRoom("Meeting Room 1" + time.toString(), "BH 101");
        assertEquals("BH 101", eventManager.getRoom("BH 101" + time.toString()));

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertEquals("BH 101", ed.getRoom());
    }

    @Test
    public void editCapacity() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        List<String> list = Arrays.asList("Bob", "John");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.editCapacity("Meeting Room 1" + time.toString(), 5);
        assertEquals(5, eventManager.getCapacity("Meeting Room 1" + time.toString()));

        EventData ed = eventManager.getDataById("Meeting Room 1" + time.toString());
        assertEquals(5, ed.getCapacity());
    }

    @Test
    public void convertEventToEventData() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = Instant.MAX;
        List<String> list = Arrays.asList("Bob", "John");
        Event e1 = new Event(list, time, "Test Event", "Meeting Room 1", 2, EventType.MULTI);
        EventData e = new EventData();
        e.addSpeakers(list);
        e.setCapacity(2);
        e.setEventName("Test Event");
        e.setId("Meeting Room 1" + time.toString());
        e.setRoom("Meeting Room 1");
        e.setTime(time);
        e.setType(EventType.MULTI);
        assertEquals(e, eventManager.convertEventToEventData(e1));
    }

    @Test
    public void getHostingEvents() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        Instant time3 = time2.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        List<String> list3 = Arrays.asList("Roger", "Janet Haws");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.createEvent(list3, time3, "Test Event 3", "Meeting Room 3",  2);
        String e2id = "Meeting Room 2" + time2.toString();
        String e3id = "Meeting Room 3" + time3.toString();
        List<String> eventList = Arrays.asList(e2id, e3id);
        assertEquals(eventList, eventManager.getHostingEvents("Janet Haws"));
    }

    @Test
    public void checkUserInEvent() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        eventManager.enrollUser("Meeting Room 1" + time.toString(), "John");
        assertTrue(eventManager.checkUserInEvent(time, "John"));
        assertFalse(eventManager.checkUserInEvent(time2, "John"));
    }

    @Test
    public void checkConflictSpeaker() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        List<String> speakerList = Arrays.asList("Janet Haws", "Jack");
        assertTrue(eventManager.checkConflictSpeaker(time2, speakerList));
        assertFalse(eventManager.checkConflictSpeaker(time, speakerList));
    }

    @Test
    public void checkRoom() {
        EventManager eventManager = new EventManager(pMap);
        Instant time = MAX;
        Instant time2 = time.minus(1, ChronoUnit.HOURS);
        List<String> list = Arrays.asList("Bob Smithers");
        List<String> list2 = Arrays.asList("Janet Haws");
        eventManager.createEvent(list, time, "Test Event", "Meeting Room 1",  2);
        eventManager.createEvent(list2, time2, "Test Event 2", "Meeting Room 2",  2);
        assertTrue(eventManager.checkRoom(time, "Meeting Room 1"));
        assertFalse(eventManager.checkRoom(time, "Meeting Room 3"));
    }
}