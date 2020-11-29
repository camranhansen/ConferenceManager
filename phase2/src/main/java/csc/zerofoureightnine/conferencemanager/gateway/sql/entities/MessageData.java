package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class MessageData {
    @Id
    private String id;

    @Column(name = "content")
    private String content = "";

    @Column(name = "time_delivered")
    private Instant timeSent = Instant.ofEpochMilli(0);

    @Column(name = "sender")
    private String sender = "";


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private Set<UserData> recipients = new HashSet<>();

    public void addRecipients(Set<UserData> users) {

    }

    public void addRecipient(UserData user) {
        if (recipients.add(user)) {
            user.addMessageData(this);
        }
    }

    public Set<UserData> getRecipients() {
        return recipients;
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

    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}