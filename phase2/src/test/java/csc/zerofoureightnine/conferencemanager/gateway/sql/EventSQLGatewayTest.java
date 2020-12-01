package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
        expectedData.setRoom("102");
        expectedData.setEventId();
        expectedData.setCapacity(20);

        String key = "1";
        eventSQLGateway.save(key, expectedData);
        EventData data = eventSQLGateway.load(key);

        assertEquals(expectedData, data);
    }

    @Test
    public void SizeTest() {
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
            events.setEventId();
            events.setCapacity(20);
            String key = ""+i;
            eventSQLGateway.save(key, events);
        }

        assertEquals(100, eventSQLGateway.size());
    }


}
