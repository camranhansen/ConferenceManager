package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.events.EventType;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class EventSQLGatewayTest {
    private static SQLMapping mapping;

    @BeforeClass
    public static void setup() {
        mapping = new SQLMapping();
    }

    @Test
    public void SaveAndLoadTest() {
        EventSQLGateway eventSQLGateway = new EventSQLGateway(mapping);
        List<String> speaker = new ArrayList<>();
        speaker.add("Ashley");
        List<String> participants = new ArrayList<>();
        participants.add("Adam");
        participants.add("Mike");

        EventData expectedData = new EventData();
        expectedData.setSpeaker(speaker);
        expectedData.setTime(Instant.ofEpochMilli(1024));
        expectedData.setParticipants(participants);
        expectedData.setEventName("Review");
        expectedData.setRoom("102");
        expectedData.setDataId();
//        expectedData.setEventId();
        expectedData.setCapacity(20);
        expectedData.setType(EventType.SINGLE);

        String key = expectedData.getDataId();
        String id = eventSQLGateway.save(key, expectedData);
        EventData data = eventSQLGateway.load(id);
        assertEquals(expectedData, data);
    }

    @Test
    public void SizeTest() {
        mapping = new SQLMapping();
        EventSQLGateway eventSQLGateway = new EventSQLGateway(mapping);

        List<String> speaker = new ArrayList<>();
        List<String> participants = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            EventData events = new EventData();
            speaker.add("A"+i);
            events.setSpeaker(speaker);
            events.setEventName("Review"+i);
            events.setTime(Instant.ofEpochMilli(1024+i));
            participants.add("B"+i);
            events.setParticipants(participants);
            events.setRoom("105"+i);
            events.setDataId();
            events.setCapacity(20);
            events.setType(EventType.SINGLE);
            String key = events.getDataId();
            eventSQLGateway.save(key, events);
        }

        assertEquals(100, eventSQLGateway.size());
    }

    @Test
    public void retrieveByFieldTest() {
        EventSQLGateway eventSQLGateway = new EventSQLGateway(mapping);
        EventData expectedData = new EventData();

        List<String> speaker = new ArrayList<>();
        speaker.add("Allen");
        List<String> participants = new ArrayList<>();
        participants.add("Cat");
        participants.add("Dog");

        expectedData.setSpeaker(speaker);
        expectedData.setTime(Instant.ofEpochMilli(1024));
        expectedData.setParticipants(participants);
        expectedData.setEventName("Review 2");
        expectedData.setRoom("103");
        expectedData.setDataId();
//        expectedData.setEventId();
        expectedData.setCapacity(20);
        expectedData.setType(EventType.SINGLE);

        String key = expectedData.getDataId();
        eventSQLGateway.save(key, expectedData);

        //List<EventData> data = eventSQLGateway.retrieveByField("eventName", "Review 2");
        List<EventData> data = eventSQLGateway.retrieveByField("id", key);
        assertEquals(expectedData, data.get(0));
    }

    @Test
    public void isEmptyTest() {
        mapping = new SQLMapping();
        EventSQLGateway eventSQLGateway = new EventSQLGateway(mapping);
        assertTrue(eventSQLGateway.isEmpty());
    }

    @Test
    public void containsKeyTest(){
        EventSQLGateway eventSQLGateway = new EventSQLGateway(mapping);
        EventData expectedData = new EventData();

        List<String> speaker = new ArrayList<>();
        speaker.add("Coke");
        List<String> participants = new ArrayList<>();
        participants.add("Sprite");
        participants.add("7Up");

        expectedData.setSpeaker(speaker);
        expectedData.setTime(Instant.ofEpochMilli(1024));
        expectedData.setParticipants(participants);
        expectedData.setEventName("Review 3");
        expectedData.setRoom("288");
        expectedData.setDataId();
//        expectedData.setEventId();
        expectedData.setCapacity(20);
        expectedData.setType(EventType.SINGLE);

        String key = expectedData.getDataId();
        eventSQLGateway.save(key, expectedData);

        assertTrue(eventSQLGateway.containsKey(key));
        assertFalse(eventSQLGateway.containsKey("abc"));

    }

    @Test
    public void removeAndContainsKey(){
        EventSQLGateway eventSQLGateway = new EventSQLGateway(mapping);
        EventData ed = new EventData();

        List<String> speaker = new ArrayList<>();
        speaker.add("Speaker Coffee");
        List<String> participants = new ArrayList<>();
        participants.add("Water");
        participants.add("Tea");

        ed.setSpeaker(speaker);
        ed.setTime(Instant.ofEpochMilli(1024));
        ed.setParticipants(participants);
        ed.setEventName("Review 4");
        ed.setRoom("999");
        ed.setDataId();
        ed.setCapacity(20);
        ed.setType(EventType.SINGLE);
        String key = ed.getDataId();
        eventSQLGateway.save(key, ed);

        int size = eventSQLGateway.size();
        EventData dataRemoved = eventSQLGateway.remove(key);

        assertEquals(ed, dataRemoved);
        assertFalse(eventSQLGateway.containsKey(key));
        assertEquals(size-1, eventSQLGateway.size());
    }

}
