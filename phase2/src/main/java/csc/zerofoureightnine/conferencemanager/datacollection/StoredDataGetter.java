package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.events.EventType;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestManager;

public class StoredDataGetter {
    private EventManager eventManager;
    private SpecialRequestManager requestManager;
    private UserManager userManager;
    private MessageManager messageManager;
    private String name;

    /**
     * Constructor
     *
     * @param messageManager {@link MessageManager}
     * @param eventManager   {@link EventManager}
     * @param requestManager {@link SpecialRequestManager}
     * @param userManager    {@link UserManager}
     * @param username       The name of user who is currently using the program
     */
    public StoredDataGetter(MessageManager messageManager, EventManager eventManager, SpecialRequestManager requestManager, UserManager userManager, String username) {
        this.eventManager = eventManager;
        this.messageManager = messageManager;
        this.requestManager = requestManager;
        this.userManager = userManager;
        this.name = username;
    }

    /**
     * Return the number of messages in retrieve inbox
     *
     * @param name The name of user who is currently using the program
     * @return the number of messages in retrieve inbox
     */
    public Integer getRetrieveMessages(String name) {
        return messageManager.getRetrieveInboxSize(name);
    }

    /**
     * Return the number of messages in unread inbox
     *
     * @param name The name of user who is currently using the program
     * @return the number of messages in unread inbox
     */
    public Integer getUnreadMessages(String name) {
        return messageManager.getUnreadInboxSize(name);
    }

    /**
     * Return the number of messages in read inbox
     *
     * @param name The name of user who is currently using the program
     * @return the number of messages in read inbox
     */
    public Integer getReadMessages(String name) {
        return messageManager.getReadInboxSize(name);
    }

    /**
     * Get the total number of users
     *
     * @return the total number of users
     */
    public Integer getTotalusers() {
        return userManager.getTotalUsers();
    }

    /**
     * Get the total number of sent messages
     *
     * @return the total number of sent messages
     */
    public Integer getTotalMessagesSent() {
        return messageManager.getTotalMessages();
    }

    /**
     * Get the total number of special requests
     *
     * @return the total number of special requests
     */
    public Integer getTotalSpecialRequests() {
        return requestManager.getTotalRequests();
    }

    /**
     * Get the total number of pending requests
     *
     * @return the total number of pending requests
     */
    public Integer getTotalPendingRequests() {
        return requestManager.getNumberPendingRequests();
    }

    /**
     * Get the total number of adressed requests
     *
     * @return the total number of adressed requests
     */
    public Integer getTotalAddressedRequests() {
        return requestManager.getNumberAdressedRequests();
    }

    /**
     * Get the percentage of requests that have been adressed out of all requests
     *
     * @return the percentage of adressed requests
     */
    public Double getPercentAdressedRequests() {
        return requestManager.getNumberAdressedRequests().doubleValue() / requestManager.getTotalRequests().doubleValue();
    }

    /**
     * Returns the total number of events.
     *
     * @return An integer representing the number of events which are currently registered.
     */
    public int getTotalEvents() {
        return eventManager.totalEventNumber();
    }

    /**
     * Returns the total number of parties.
     *
     * @return An integer representing the number of parties which are currently registered.
     */
    public int getTotalParties() {
        return eventManager.totalOfEventType(EventType.PARTY);
    }

    /**
     * Returns the total number of single-speaker events.
     *
     * @return An integer representing the number of single-speaker events which are currently registered.
     */
    public int getTotalSingles() {
        return eventManager.totalOfEventType(EventType.SINGLE);
    }

    /**
     * Returns the total number of multi-speaker events.
     *
     * @return An integer representing the number of multi-speaker events which are currently registered.
     */
    public int getTotalMulties() {
        return eventManager.totalOfEventType(EventType.MULTI);
    }

    /**
     * Returns the most common {@link EventType} which events are registered as.
     *
     * @return The most commonly-occuring {@link EventType}.
     */
    public EventType getMostPopularEventType() {
        return eventManager.mostPopularEventType();
    }

    /**
     * Returns the truncated average capacity of all registered events.
     *
     * @return An integer representing the truncated average capacity of all registered events.
     */
    public int getAverageCapacity() {
        return eventManager.averageCapacity();
    }


}
