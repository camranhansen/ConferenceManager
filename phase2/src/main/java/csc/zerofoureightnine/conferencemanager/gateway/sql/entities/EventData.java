package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.events.EventType;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class EventData {

    @Id
    private String dataId;

    @Column(name = "speaker")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> speakers = new HashSet<>();

    @Column(name = "time")
    private Instant time;

    @Column(name = "eventName")
    private String eventName;

    @Column(name = "participants")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> participants = new HashSet<>();

    @Column(name = "room")
    private String room;

    @Column(name = "capacity")
    private int capacity;

//    @Column(name = "event_id")
//    private String eventId;

    @Column(name = "event_type")
    private EventType type;

    public String getDataId() {
        return dataId;
    }

    public void setDataId() {
        this.dataId =this.room + this.time.toString();
    }

    public Set<String> getSpeakers() {
        return speakers;
    }

    public void addSpeakers(String... speaker) {
        this.speakers.addAll(Arrays.asList(speaker));
    }

	public void addSpeakers(Collection<String> speaker) {
        this.speakers.addAll(speaker);
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

    public Set<String> getParticipants() {
        return participants;
    }

    public void addParticipants(String... participants) {
        this.participants.addAll(Arrays.asList(participants));
    }

    public void addParticipants(Collection<String> participants) {
        this.participants.addAll(participants);
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

//    public String getEventId() {
//        return eventId;
//    }
//
//    public void setEventId() {
//        this.eventId = this.room + this.time.toString();
//    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventData eventData = (EventData) o;
        return capacity == eventData.capacity &&
                dataId.equals(eventData.dataId) &&
                speakers.equals(eventData.speakers) &&
                time.equals(eventData.time) &&
                eventName.equals(eventData.eventName) &&
                participants.equals(eventData.participants) &&
                room.equals(eventData.room) &&
                type == eventData.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataId, speakers, time, eventName, participants, room, capacity, type);
    }
}
