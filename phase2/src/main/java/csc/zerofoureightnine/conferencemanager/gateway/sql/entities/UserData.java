package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Permission> permissions = new ArrayList<>();

    /**
     *Instantiates A {@link UserData}
     */
    public UserData(){
        this.permissions = new ArrayList<>();
    }

    /**
     *Initializes a {@link UserData}.
     * @param username {@link String} of username
     * @param password {@link String} of password
     * @param permissions users list of permissions
     */
    public UserData(String username, String password, List<Permission> permissions){
        this.userName = username;
        this.password = password;
        this.permissions = permissions;
    }

    /**
     * Returns a user's id.
     * @return {@link String} user id.
     */
    public String getId() {
        return userName;
    }

    /**
     * Sets a user's id.
     * @param id {@link String} id to be set
     */
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