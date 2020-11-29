package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.events.EventType;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class EventData {

    @Id
    private int dataId;

    @Column(name = "speaker")
    @ElementCollection
    private List<String> speaker;

    @Column(name = "time")
    private Instant time;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "participants")
    @ElementCollection
    private List<String> participants;

    @Column(name = "room")
    private String room;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "event_type")
    private EventType type;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }
    public List<String> getSpeaker() {
        return speaker;
    }

    public void setSpeaker(List<String> speaker) {
        this.speaker = speaker;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
