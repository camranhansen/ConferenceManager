package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
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


}
