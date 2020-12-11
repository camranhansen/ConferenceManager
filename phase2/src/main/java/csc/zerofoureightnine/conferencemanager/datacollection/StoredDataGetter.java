package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;

import java.util.List;

public class StoredDataGetter {
    private EventManager eventManager;
    private MessageManager messageManager;
    private String name;

    public StoredDataGetter(EventManager em, MessageManager mm, String username){
        this.eventManager = em;
        this.messageManager = mm;
        this.name = username;
    }

    public int getRetrieveMessages(String name){
        return messageManager.getRetrieveInboxSize(name);
    }

    public int getUnreadMessages(String name){
        return messageManager.getUnreadInboxSize(name);
    }

    public int getReadMessages(String name){
        return messageManager.getReadInboxSize(name);
    }


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
