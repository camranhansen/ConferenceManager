package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

@Table(name = "messages")
@Entity
public class MessageData implements Identifiable<UUID> {
    @Id
    private UUID id;

    @Column(name = "content")
    private String content = "";

    @Column(name = "time_delivered")
    private Instant timeSent = Instant.ofEpochMilli(0);

    @Column(name = "sender")
    private String sender = "";

    @Column(name = "recipients")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> recipients = new HashSet<>();

    @Column(name = "read")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> read = new HashSet<>();

    @Column(name = "archived")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> archived = new HashSet<>();

    /**
     *Instantiates A {@link MessageData}
     */
    public MessageData() {
    }

    /**
     * Returns all the recipients of the message.
     * @return a {@link Set} of the usernames of the recipients of the message
     */
    public Set<String> getRecipients() {
        return recipients;
    }

    /**
     * Adds more recipients to the message.
     * @param recipients username of the recipient
     */
    public void addRecipients(String... recipients) {
        this.recipients.addAll(Arrays.asList(recipients));
    }

    /**
     * Adds more recipients to the message.
     * @param recipients username of the recipient
     */
    public void addRecipients(Collection<String> recipients) {
        this.recipients.addAll(recipients);
    }

    /**
     * Returns the sender of the message.
     * @return a {@link String} representing the username of the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the content of the message.
     * @return a {@link String} representing the message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the sent time of the message.
     * @return an {@link Instant} representing the sent time of the message
     */
    public Instant getTimeSent() {
        return timeSent;
    }

    /**
     * Sets the content of the message.
     * @param content message content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets the sender of the message.
     * @param sender username of the sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Sets the sent time of the message.
     * @param timeSent message's sent time
     */
    public void setTimeSent(Instant timeSent) {
        this.timeSent = timeSent;
    }

    /**
     * Returns all recipients that have read the message.
     * @return a {@link Set} of usernames of recipients that have read the message
     */
    public Set<String> getRead() {
        return this.read;
    }

    /**
     * Returns all recipients that have marked this message as archived.
     * @return a {@link Set} of usernames of recipients that have marked this message as archived
     */
    public Set<String> getArchived() {
        return archived;
    }

    /**
     * Adds recipient to the list of recipients that have read the message.
     * @param username username of the recipient
     */
    public void addToRead(String username) {
        this.read.add(username);
    }

    /**
     * Removes recipient to the list of recipients that have read the message.
     * @param username username of the recipient
     */
    public void removeFromRead(String username) {
        this.read.remove(username);
    }

    /**
     * Adds recipient to the list of recipients that have marked the message as archived.
     * @param username username of the recipient
     */
    public void addToArchived(String username) {
        this.archived.add(username);
    }

    /**
     * Removes recipient to the list of recipients that have marked the message as archived.
     * @param username username of the recipient
     */
    public void removeFromArchived(String username) {
        this.archived.remove(username);
    }

    /**
     * Compares this message with the given message. Returns true if they are exactly the same, false otherwise.
     * @param o {@link Object} a message
     * @return true if the two messages are exactly the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MessageData that = (MessageData) o;
        return getId().equals(that.getId()) && Objects.equals(content, that.content) && timeSent.equals(that.timeSent)
                && sender.equals(that.sender) && recipients.equals(that.recipients);
    }

    /**
     * Returns the hash code of the message.
     * @return int representing message's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), content, timeSent, sender, recipients);
    }

    /**
     * Sets message id.
     * @param id message id
     */
    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns message id.
     * @return {@link UUID} message id
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Returns a formatted message.
     * @return a {@link String} representing the message
     */
    @Override
    public String toString() {
        return id.toString() + " " +  getSender() + " at " + DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault()).format(getTimeSent()) + " said: "
                + getContent();
    }
}