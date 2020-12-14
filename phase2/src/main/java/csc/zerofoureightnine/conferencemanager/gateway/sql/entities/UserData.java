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

    /**
     * Returns user's password.
     * @return a {@link String} representing the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password.
     * @param password {@link String} user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns all permissions that the user has.
     * @return a {@link List} of {@link Permission} of the user
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Compares this user with the given user. Returns true if they are exactly the same, false otherwise.
     * @param o {@link Object} a user
     * @return true if the two users are exactly the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return userName.equals(userData.userName);
    }

    /**
     * Returns the hash code of the user.
     * @return int representing user's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}