package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

@Table(name = "messages") @Entity
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

    public MessageData() {
    }


    public Set<String> getRecipients() {
        return recipients;
    }

    public void addRecipients(String... recipients) {
        this.recipients.addAll(Arrays.asList(recipients));
    }

    public void addRecipients(Collection<String> recipients) {
        this.recipients.addAll(recipients);
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Instant getTimeSent() {
        return timeSent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTimeSent(Instant timeSent) {
        this.timeSent = timeSent;
    }

    public Set<String> getRead(){
        return this.read;
    }

    public Set<String> getArchived(){
        return archived;
    }

    public void addToRead(String username){
        this.read.add(username);
    }

    public void removeFromRead(String username){
        this.read.remove(username);
    }

    public void addToArchived(String username){
        this.archived.add(username);
    }

    public void removeFromArchived(String username){
        this.archived.remove(username);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return getId().equals(that.getId()) &&
                Objects.equals(content, that.content) &&
                timeSent.equals(that.timeSent) &&
                sender.equals(that.sender) &&
                recipients.equals(that.recipients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), content, timeSent, sender, recipients);
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString() + " From: " + getSender() + " at " + getTimeSent() + ": " + getContent();
    }
}