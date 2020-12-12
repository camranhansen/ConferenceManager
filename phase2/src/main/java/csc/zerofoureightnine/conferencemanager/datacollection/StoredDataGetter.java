package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.events.EventType;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;

public class StoredDataGetter {
    private EventManager eventManager;
    private MessageManager messageManager;
    private String name;

    /**
     * Constructor
     * @param em EventManager
     * @param mm MessageManager
     * @param username The name of user who is currently using the program
     */
    public StoredDataGetter(EventManager em, MessageManager mm, String username){
        this.eventManager = em;
        this.messageManager = mm;
        this.name = username;
    }

    /**
     * Return the number of messages in retrieve inbox
     * @param name The name of user who is currently using the program
     * @return the number of messages in retrieve inbox
     */
    public int getRetrieveMessages(String name){
        return messageManager.getRetrieveInboxSize(name);
    }

    /**
     * Return the number of messages in unread inbox
     * @param name The name of user who is currently using the program
     * @return the number of messages in unread inbox
     */
    public int getUnreadMessages(String name){
        return messageManager.getUnreadInboxSize(name);
    }

    /**
     * Return the number of messages in read inbox
     * @param name The name of user who is currently using the program
     * @return the number of messages in read inbox
     */
    public int getReadMessages(String name){
        return messageManager.getReadInboxSize(name);
    }

    /**
     * Returns the total number of events.
     * @return An integer representing the number of events which are currently registered.
     */
    public int getTotalEvents() { return eventManager.totalEventNumber(); }

    /**
     * Returns the total number of parties.
     * @return An integer representing the number of parties which are currently registered.
     */
    public int getTotalParties() { return eventManager.totalOfEventType(EventType.PARTY); }

    /**
     * Returns the total number of single-speaker events.
     * @return An integer representing the number of single-speaker events which are currently registered.
     */
    public int getTotalSingles() { return eventManager.totalOfEventType(EventType.SINGLE); }

    /**
     * Returns the total number of multi-speaker events.
     * @return An integer representing the number of multi-speaker events which are currently registered.
     */
    public int getTotalMulties() { return eventManager.totalOfEventType(EventType.MULTI); }

    /**
     * Returns the most common {@link EventType} which events are registered as.
     * @return The most commonly-occuring {@link EventType}.
     */
    public EventType getMostPopularEventType() { return eventManager.mostPopularEventType(); }

    /**
     * Returns the truncated average capacity of all registered events.
     * @return An integer representing the truncated average capacity of all registered events.
     */
    public int getAverageCapacity() { return eventManager.averageCapacity(); }


}
