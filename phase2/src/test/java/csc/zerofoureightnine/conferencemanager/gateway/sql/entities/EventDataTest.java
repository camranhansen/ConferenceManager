package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.events.EventType;
import org.junit.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static csc.zerofoureightnine.conferencemanager.events.EventType.PARTY;
import static org.junit.Assert.assertEquals;

public class EventDataTest {

    @Test
    public void testEventSpeakerData() {
        EventData eventData = new EventData();
        Set<String> speaker = new HashSet<>();
        speaker.add("Bob");
        eventData.addSpeakers(speaker);
        assertEquals(speaker, eventData.getSpeakers());
    }

    @Test
    public void testEventTimeData() {
        EventData eventData = new EventData();
        Instant timeSent = Instant.ofEpochMilli(1024);
        eventData.setTime(timeSent);

        assertEquals(timeSent, eventData.getTime());
    }

    @Test
    public void testEventNameData() {
        EventData eventData = new EventData();
        eventData.setEventName("Final Exam Review");

        assertEquals("Final Exam Review", eventData.getEventName());
    }

    @Test
    public void testEventParticipantData() {
        EventData eventData = new EventData();
        Set<String> participants = new HashSet<>();
        participants.add("Annie");
        participants.add("Shawn");
        eventData.addParticipants(participants);
        assertEquals(participants, eventData.getParticipants());
    }

    @Test
    public void testEventRoomData() {
        EventData eventData = new EventData();
        eventData.setRoom("101");

        assertEquals("101", eventData.getRoom());
    }

    @Test
    public void testEventCapacityData() {
        EventData eventData = new EventData();
        eventData.setCapacity(20);

        assertEquals(20, eventData.getCapacity());
    }

//    @Test
//    public void testEventIdData() {
//        EventData eventData = new EventData();
//        Instant timeSent = Instant.ofEpochMilli(1024);
//        eventData.setTime(timeSent);
//        eventData.setRoom("101");
//        eventData.setEventId();
//
//        assertEquals(eventData.getRoom()+eventData.getTime().toString(), eventData.getEventId());
//    }

    @Test
    public void testEventTypeData() {
        EventData eventData = new EventData();
        EventType type = PARTY;
        eventData.setType(type);

        assertEquals(PARTY, eventData.getType());
       }
}
