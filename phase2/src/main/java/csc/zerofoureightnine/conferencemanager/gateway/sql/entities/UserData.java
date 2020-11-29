package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserData {
    @Id
    public int id;

    @ManyToMany(mappedBy = "recipients", fetch = FetchType.EAGER)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return id == userData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}