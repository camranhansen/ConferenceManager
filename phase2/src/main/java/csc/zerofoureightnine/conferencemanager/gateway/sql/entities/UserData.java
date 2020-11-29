package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserData {
    @Id
    public int id;

    @ManyToMany(mappedBy = "recipients")
    private Set<MessageData> messages = new HashSet<>();

    public void addMessageData(MessageData messageData) {
        if (messages.add(messageData)) {
            messageData.addRecipient(this);
        }
    }

    public Set<MessageData> getMessages() {
        return messages;
    }

    public int getId() {
        return id;
    }
}