package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserData {
    @Id
    public int id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "permissions")
    @ElementCollection
    private List<Permission> permissions;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
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