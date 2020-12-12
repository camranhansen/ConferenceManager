package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.List;

public class UserManager {

    /**
     * csc.zerofoureightnine.conferencemanager.users stores a hashmap where the keys are usernames and the corresponding value is a User object.
     */
    private PersistentMap<String, UserData> userData;

    /**
     * Instantiates the UserManager
     * @param userData an empty or pre-constructed PersistentMap that relates a username to the appropriate entity.
     */
    public UserManager(PersistentMap<String, UserData> userData) {
        this.userData = userData;
    }

    //User Methods
    /**
     * Checks whether the user exists in the stored hashmap
     * @param username the username of the user
     * @return true if the user exists in the stored hashmap, false otherwise
     */
    public boolean userExists(String username){
        return this.userData.containsKey(username);
    }

    /**
     * Generates a user object and stores it into the hashmap
     * @param username the username of the new user
     * @param password the password of the new user
     */
    public void createUser(String username, String password, List<Permission> permissions) {
        this.userData.beginInteraction();
        UserData newData = new UserData();
        newData.setId(username);
        newData.setPassword(password);
        newData.getPermissions().addAll(permissions);
        this.userData.put(username, newData);
        this.userData.endInteraction();
    }

    /**
     * Removes the given username and corresponding User object from the hashmap
     * Removes the given username and corresponding UserData object from PersistentMap
     * @param username the username of the User object to be removed
     */
    public void removeUser(String username){
        this.userData.beginInteraction();
        this.userData.remove(username);
        this.userData.endInteraction();
    }

    //Password Methods

    /**
     * Returns the password associated with a given User
     * @param username the name of the User whose password should be retrieved
     * @return the password
     */
    public String getPassword(String username){
        return this.userData.load(username).getPassword();
    }

    /**
     * Returns the password associated with a given User
     *
     * @param username    the name of the User whose password should be retrieved
     * @param newPassword the new password to set for this User
     */
    public void setPassword(String username, String newPassword) {
        this.userData.beginInteraction();
        this.userData.load(username).setPassword(newPassword);
        this.userData.endInteraction();
    }

    /**
     * Get the total number of users in this program!
     *
     * @return the total number of uzers
     */
    public Integer getTotalUsers() {
        return userData.keySet().size();
    }
}
