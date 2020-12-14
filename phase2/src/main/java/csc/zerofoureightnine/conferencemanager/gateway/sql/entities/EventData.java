package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.events.EventType;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "events")

public class EventData implements Identifiable<String> {
    @Id
    private String id;

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

    @Column(name = "event_type")
    private EventType type;

    /**
     * Sets all components of the event
     * @param speakerName speakers' usernames
     * @param time event time
     * @param eventName name of the event
     * @param room room number
     * @param capacity event capacity
     * @param type event type
     */
    public void setEventData(Collection<String> speakerName, Instant time, String eventName, String room, int capacity, EventType type) {
        speakers.addAll(speakerName);
        this.time = time;
        this.eventName = eventName;
        this.room = room;
        this.capacity = capacity;
        this.type = type;
    }

    /**
     * Returns all speakers of the event.
     * @return a {@link Set} of speakers' usernames
     */
    public Set<String> getSpeakers() {
        return speakers;
    }

    public void addSpeakers(String... speaker) {
        this.speakers.addAll(Arrays.asList(speaker));
    }

    /**
     * Adds more speakers to the event.
     * @param speaker a {@link Collection} of speakers' usernames
     */
	public void addSpeakers(Collection<String> speaker) {
        this.speakers.addAll(speaker);
	}

    /**
     * Returns the time of the event.
     * @return {@link Instant} representing the time of the event
     */
    public Instant getTime() {
        return time;
    }

    /**
     * Sets the time of the event.
     * @param time time of the event
     */
    public void setTime(Instant time) {
        this.time = time;
    }

    /**
     * Returns the name of the event.
     * @return a {@link String} representing the event name
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the event name.
     * @param eventName name of the event
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Returns all the participants of the event.
     * @return a {@link Set} of participants' usernames
     */
    public Set<String> getParticipants() {
        return participants;
    }

    /**
     * Adds more participants in the event.
     * @param participants username of the participants
     */
    public void addParticipants(String... participants) {
        this.participants.addAll(Arrays.asList(participants));
    }


     /**
     * Adds more participants in the event.
     * @param participants username of the participants
     */
    public void addParticipants(Collection<String> participants) {
        this.participants.addAll(participants);
    }

    /**
     * Removes a participant from the event.
     * @param participants username of the participant
     */
    public void removeParticipant(String participants){
        this.participants.remove(participants);
    }

    /**
     * Returns the room number of the event.
     * @return {@link String} room number
     */
    public String getRoom() {
        return room;
    }

    /**
     * Changes the event to a different room.
     * @param room room number
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Returns the capacity of the event.
     * @return an int representing the event capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the event capacity.
     * @param capacity event capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the type of the event.
     * @return {@link EventType}
     */
    public EventType getType() {
        return type;
    }

    /**
     * Sets the type of the event.
     * @param type {@link EventType}
     */
    public void setType(EventType type) {
        this.type = type;
    }

    /**
     * Compares this event with the given event. Returns true if they are exactly the same, false otherwise.
     * @param o {@link Object} an event
     * @return true if the two events are exactly the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventData eventData = (EventData) o;
        return capacity == eventData.capacity &&
                getId().equals(eventData.getId()) &&
                speakers.equals(eventData.speakers) &&
                time.equals(eventData.time) &&
                eventName.equals(eventData.eventName) &&
                participants.equals(eventData.participants) &&
                room.equals(eventData.room) &&
                type == eventData.type;
    }

    /**
     * Returns the hash code of the event.
     * @return int representing event's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), speakers, time, eventName, participants, room, capacity, type);
    }

    /**
     * Sets event id.
     * @param id event id
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the event id.
     * @return {@link String} event id
     */
    @Override
    public String getId() {
        return id;
    }
}
