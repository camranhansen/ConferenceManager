package csc.zerofoureightnine.conferencemanager.messaging;

import java.time.Instant;

@Deprecated
public class Message {
    private String content;
    private Instant timeSent;
    private String sender;
    private String[] recipients;

    /**
     * Stores the message's data.
     * @param sender sender of the message
     * @param recipients a {@code String[]} of recipients of the message
     * @param content content of the message
     */
    public Message(String sender, String[] recipients, String content) {
        this.content = content;
        this.timeSent = Instant.now();
        this.sender = sender;
        this.recipients = recipients;
    }

    /**
     * Returns the sent time of the message.
     * @return an instant representing the sent time of the message
     */
    public Instant getTimeSent() {
        return timeSent;
    }

    /**
     * Returns the content of the message
     * @return a string representing the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the sender of the message
     * @return a string representing the username of the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns all recipients of the message
     * @return a {@code String[]} representing the usernames of recipients of the message
     */
    public String[] getRecipients() {
        return recipients;
    }

    /**
     * Set the sent time of the message to {@code time}
     * @param time instant representing sent time of the message
     */
    public void setTimeSent(Instant time){
        this.timeSent = time;
    }
}
