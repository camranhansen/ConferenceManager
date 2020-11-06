package Messaging;

import java.time.Instant;

public class Message {
    private String content;
    private Instant timeSent;
    private String sender;
    private String[] recipients;

    public Message(String sender, String[] recipients, String content) {
        this.content = content;
        this.timeSent = Instant.now();
        this.sender = sender;
        this.recipients = recipients;
    }

    public Instant getTimeSent() {
        return timeSent;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String[] getRecipients() {
        return recipients;
    }

    //TODO: Talk about encapsulation.
}
