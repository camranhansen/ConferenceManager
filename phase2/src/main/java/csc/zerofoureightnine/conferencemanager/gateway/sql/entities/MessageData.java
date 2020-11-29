package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class MessageData {
    @Id
    private String id;

    @Column(name = "content")
    private String content;

    @Column(name = "time_delivered")
    private Instant timeSent;

    @Column(name = "sender")
    private String sender;


    @ManyToMany
    @JoinTable
    private Set<UserData> recipients;

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
        return id.equals(that.getID()) &&
                content.equals(that.content) &&
                timeSent.equals(that.timeSent) &&
                sender.equals(that.sender) &&
                recipients.equals(that.recipients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, timeSent, sender, recipients);
    }
}