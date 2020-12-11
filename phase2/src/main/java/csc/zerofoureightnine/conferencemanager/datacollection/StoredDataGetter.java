package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;

import java.util.List;

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
     * Formatted all the information
     * @return All information that we want user to have access with.
     */
    @Override
    public String toString(){
        String lineSep = ":" + System.lineSeparator();
        String formatted = "Number of enrolled events: " + eventManager.getUserEvents(name).size() + lineSep +
                "Number of events: " + eventManager.getAllEventIds().size() + lineSep +
                "Number of events that are available: " + eventManager.getAvailableEvents(name).size() + lineSep +
                "Number of retrieve messages: " + getRetrieveMessages(name) + lineSep +
                "Number of unread messages: " + getUnreadMessages(name) + lineSep +
                "Number of read messages: " + getReadMessages(name) + lineSep;
        return formatted;
    }
}
