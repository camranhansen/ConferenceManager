package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MessageMover {
    private MessageManager messageManager;


    /**
     * Instantiates MessageMover
     * @param messageManager {@link MessageManager} class
     *
     */
    public MessageMover(MessageManager messageManager){
        this.messageManager = messageManager;
    }


    /**
     * Check if the message is in user's read inbox, if so, move the given message from user's read inbox to unread
     * inbox.
     * @param id id of the MessageData
     * @param username username of the current user
     */
    public void moveReadToUnread(UUID id, String username){
        this.messageManager.getMessageData().beginInteraction();
        PersistentMap<UUID, MessageData> md = this.messageManager.getMessageData();
        MessageData m = md.get(id);
        m.getRead().remove(username);
        this.messageManager.getMessageData().endInteraction();

    }

    /**
     * Check if the message is in user's unread inbox, if so, move the given message from user's unread inbox to read.
     * @param id id of the MessageData
     * @param username username of the current user
     */
    public void moveUnreadToRead(UUID id, String username){
        this.messageManager.getMessageData().beginInteraction();
        PersistentMap<UUID, MessageData> md = this.messageManager.getMessageData();
        MessageData m = md.get(id);
        m.getRead().add(username);
        this.messageManager.getMessageData().endInteraction();
    }


    /**
     * Check if this message is in user's unread inbox, if not, add the given message to this user's archived inbox.
     * @param id id of the MessageData
     * @param username username of the current user
     */

    public void moveToArchived(UUID id, String username){
        this.messageManager.getMessageData().beginInteraction();
        PersistentMap<UUID, MessageData> md = this.messageManager.getMessageData();
        MessageData m = md.get(id);
        m.getArchived().add(username);
        this.messageManager.getMessageData().endInteraction();

    }

    /**
     * Remove the given message from user's archived inbox.
     * @param id id of the MessageData
     * @param username username of the current user
     */
    public void removeFromArchived(UUID id, String username){
        this.messageManager.getMessageData().beginInteraction();
        PersistentMap<UUID, MessageData> md = this.messageManager.getMessageData();
        MessageData m = md.get(id);
        m.getArchived().remove(username);
        this.messageManager.getMessageData().endInteraction();
    }


    /**
     * Delete the given message from user's inbox.
     * @param id id of the MessageData
     * @param username username of the current user
     */
    public void deleteOneMessage(UUID id, String username){
        this.messageManager.getMessageData().beginInteraction();
        PersistentMap<UUID, MessageData> md = this.messageManager.getMessageData();
        MessageData m = md.get(id);
        m.getRecipients().remove(username);
        if(m.getRecipients().isEmpty()){
            this.messageManager.getMessageData().remove(id);
        }
        this.messageManager.getMessageData().endInteraction();
    }

        /**
         * Delete all conversations between the user and the given sender from user's inbox.
         * @param username username of the current user
         * @param from username of the sender
         */
    public void deleteConversation(String username, String from) {
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
     * @param username username of the current user
     */
    public void clearAllInboxes(String username){
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