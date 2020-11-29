package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

import java.util.List;

public class StoredDataGetter {
    private EventManager eventManager;
    private MessageManager messageManager;
    private UserManager userManager;
    private String name;

    public StoredDataGetter(EventManager em, MessageManager mm ,UserManager um, String username){
        this.eventManager = em;
        this.messageManager = mm;
        this.userManager = um;
        this.name = username;
    }

    public int getRetrieveMessages(String name){
        int num = 0;
        for(String key : messageManager.retrieveUserInbox(name).keySet()){
            num += messageManager.retrieveUserInbox(name).get(key).size();
        }
        return num;
    }

    public int getUnreadMessages(String name){
        int num = 0;
        for(String key : messageManager.getUnreadInbox(name).keySet()){
            num += messageManager.getUnreadInbox(name).get(key).size();
        }
        return num;
    }

    public int getReadMessages(String name){
        int num = 0;
        for(String key : messageManager.getReadInbox(name).keySet()){
            num += messageManager.getReadInbox(name).get(key).size();
        }
        return num;
    }

    public double getAverageCapacity(){
        List<String> aList = this.eventManager.getAllEventIds();
        int capacity = 0;
        for(String id: aList){
            capacity += this.eventManager.getCapacity(id);
        }
        return capacity/(aList.size());
    }

    @Override
    public String toString(){
        String lineSep = ":" + System.lineSeparator();
        String formatted = "Number of enrolled events: " + this.eventManager.getUserEvents(this.name).size() + lineSep +
                "Number of events: " + this.eventManager.getAllEventIds().size() + lineSep +
                "Number of events that are available: " + this.eventManager.getAvailableEvents(this.name).size() + lineSep +
                "Number of retrieve messages: " + getRetrieveMessages(this.name) + lineSep +
                "Number of unread messages: " + getUnreadMessages(this.name) + lineSep +
                "Number of read messages: " + getReadMessages(this.name) + lineSep +
                "Average room capacity: " + getAverageCapacity() + lineSep;
        return formatted;
    }
}
