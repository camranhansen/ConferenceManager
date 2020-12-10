package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.util.*;

public class MessageMover {
    private MessageManager messageManager;
    private String username;


    /**
     * Instantiates MessageMover
     * @param messageManager MessageManager class
     * @param username username of the user accessing MessageMover
     */
    public MessageMover(MessageManager messageManager, String username){
        this.messageManager = messageManager;
        this.username = username;
    }


    /**
     * Check if the message is in user's read inbox, if so, move the given message from user's read inbox to unread
     * inbox.
     * @param from username of sender
     * @param content message content
     * @param timeSent sent time of the message
     */
    public void moveReadToUnread(String from, String content, String timeSent){
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for (MessageData m: md){
            if(from.equals(m.getSender())&&content.equals(m.getContent())&&timeSent.equals(m.getTimeSent().toString())){
                m.removeFromRead(username);
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }


    /**
     * Check if the message is in user's unread inbox, if so, move the given message from user's unread inbox to read.
     * @param from username of sender
     * @param content message content
     * @param timeSent sent time of the message
     */
    public void moveUnreadToRead(String from, String content, String timeSent){
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for (MessageData m: md){
            if(from.equals(m.getSender())&&content.equals(m.getContent())&&timeSent.equals(m.getTimeSent().toString())){
                if(!m.getRead().contains(this.username)){
                    m.addToRead(username);
                }
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }



    /**
     * Check if this message is in user's unread inbox, if not, add the given message to this user's archived inbox.
     * @param from username of the sender
     * @param content message content
     * @param timeSent sent time of the message
     */
    public void moveToArchived(String from, String content, String timeSent) {
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for (MessageData m : md) {
            if (from.equals(m.getSender()) && content.equals(m.getContent()) && timeSent.equals
                    (m.getTimeSent().toString())) {
                if (!m.getArchived().contains(this.username)) {
                    m.addToArchived(username);
                }
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }


    /**
     * Remove the given message from user's archived inbox.
     * @param from username of the sender
     * @param content message content
     * @param timeSent sent time of the message
     */
    public void removeFromArchived(String from, String content, String timeSent){
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for (MessageData m : md) {
            if (from.equals(m.getSender()) && content.equals(m.getContent()) && timeSent.equals
                    (m.getTimeSent().toString())) {
                m.removeFromArchived(username);
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }


    /**
     * Delete the given message from user's inbox.
     * @param from username of the sender
     * @param content message content
     * @param timeSent sent time of the message
     */
    public void deleteOneMessage(String from, String content, String timeSent){
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for (MessageData m: md){
            if (from.equals(m.getSender()) && content.equals(m.getContent()) && timeSent.equals
                    (m.getTimeSent().toString())) {
                m.getRecipients().remove(username);
                if(m.getRecipients().isEmpty()){
                    this.messageManager.getMessageData().remove(m.getId());
                }
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }


        /**
         * Delete all conversations between the user and the given sender from user's inbox.
         * @param from username of the sender
         */
    public void deleteConversation(String from) {
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for (MessageData m : md) {
            if (m.getSender().equals(from)) {
                m.getRecipients().remove(username);
                if (m.getRecipients().isEmpty()) {
                    this.messageManager.getMessageData().remove(m.getId());
                }
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }


    /**
     * Clear this user's inbox.
     */
    public void clearAllInboxes(){
        this.messageManager.getMessageData().beginInteraction();
        List<MessageData> md = this.messageManager.getMessageData().loadInCollection("recipients", username);
        for(MessageData m: md) {
            m.getRecipients().remove(username);
            if(m.getRecipients().isEmpty()){
                this.messageManager.getMessageData().remove(m.getId());
            }
        }
        this.messageManager.getMessageData().endInteraction();
    }
}
