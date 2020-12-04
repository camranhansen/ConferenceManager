package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequest;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserData implements Identifiable<String>{
    @Id
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "permissions")
    @ElementCollection
    private List<Permission> permissions;

    public String getId() {
        return userName;
    }

    public void setId(String id){ this.userName = id;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return userName.equals(userData.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}