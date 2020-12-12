package csc.zerofoureightnine.conferencemanager.events;

/**
 * Denotes the event type. See description
 */
public enum EventType {
    PARTY, //No speaker. Speakers is empty.
    MULTI, //Multiple speakers. At least two speakers
    SINGLE, //One speaker. Speakers has one element.


}
