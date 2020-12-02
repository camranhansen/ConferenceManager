package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import org.junit.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventDataTest {

    @Test
    public void testEventSpeakerData() {
        EventData eventData = new EventData();
        List<String> speaker = new ArrayList<>();
        speaker.add("Bob");
        eventData.setSpeaker(speaker);
        assertEquals(speaker, eventData.getSpeaker());
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
        List<String> participants = new ArrayList<>();
        participants.add("Annie");
        participants.add("Shawn");
        eventData.setParticipants(participants);
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

//    @Test
//    public void testEventTypeData() {
//        EventData eventData = new EventData();
//        EventType type = new
//        eventData.setType(PARTY);
//
//        assertEquals(PARTY, eventData.getType());
    //   }
}
