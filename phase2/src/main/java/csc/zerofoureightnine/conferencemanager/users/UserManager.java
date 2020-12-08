package csc.zerofoureightnine.conferencemanager.users;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.*;

public class UserManager {

    /**
     * csc.zerofoureightnine.conferencemanager.users stores a hashmap where the keys are usernames and the corresponding value is a User object.
     */
    private HashMap<String, User> users;
    private PersistentMap<String, UserData> userData;

    /**
     * Instantiates the UserManager
     * @param userData an empty or pre-constructed PersistentMap that relates a username to the appropriate entity.
     */
    public UserManager(PersistentMap<String, UserData> userData) {
//        this.users = new HashMap<>();
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
    public void createUser(String username, String password){
        this.userData.beginInteraction();
        User u = new User(username, password);
//        this.users.put(username, u);
        this.userData.put(username, this.convertUserToUserData(u));
        this.userData.endInteraction();
    }

    /**
     * Removes the given username and corresponding User object from the hashmap
     * Removes the given username and corresponding UserData object from PersistentMap
     * @param username the username of the User object to be removed
     */
    public void removeUser(String username){
        this.userData.beginInteraction();
//        this.users.remove(username);
        this.userData.remove(username);
        this.userData.endInteraction();
    }

    /**
     * Generates a UserData object and stores it into the map
     * @param username the username of the new user
     * @param password the password of the new user
     * @param permissions the list of permissions the user has access to
     */
    public void createUser(String username, String password, List<Permission> permissions){

        UserData u = new UserData(username, password, permissions);
        this.userData.put(username, u);
    }

    /**
     * Creates an instance of UserData from a given User object.
     * @param u the User object to be converted
     * @return the corresponding UserData object with identical username and password
     */
    private UserData convertUserToUserData(User u){
        UserData newData = new UserData();
        newData.setId(u.getUsername());
        newData.setPassword(u.getPassword());
        return newData;
    }

    //Password Methods

    /**
     * Returns the password associated with a given User
     * @param username the name of the User whose password should be retrieved
     * @return the password
     */
    public String getPassword(String username){
//        return this.users.get(username).getPassword();
        return this.userData.load(username).getPassword();
    }

    /**
     * Returns the password associated with a given User
     * @param username the name of the User whose password should be retrieved
     * @param newPassword the new password to set for this User
     */
    public void setPassword(String username, String newPassword){
        this.userData.beginInteraction();
//        this.users.get(username).setPassword(newPassword);
        this.userData.load(username).setPassword(newPassword);
        this.userData.endInteraction();
    }


    //Save and Set Methods
    /**
     * Converts the stored hashmap into a list of all csc.zerofoureightnine.conferencemanager.users, whose data is in turn
     * represented by a string array.
     * The format of the string array is: {username, password, permssions}
     * @return a nested list containing string arrays
     */
    @Deprecated
    public ArrayList<String[]> getAllUserData(){
        ArrayList<String[]> userList = new ArrayList<>();
        for (User u: users.values()) {
            userList.add(getSingleUserData(u.getUsername()));
        }
        return userList;
    }

    /**
     * Converts the data of a single User object into a string array.
     * The format of the string array is: {username, password, permssions}
     * @param username the username of the User object whose data needs to be converted
     * @return a string array corresponding to all the user's data
     */
    @Deprecated
    public String[] getSingleUserData(String username){
        if(userExists(username)){
            String password = users.get(username).getPassword();
            String permissions = this.PermissionsToString(users.get(username).getPermissions());
            return new String[]{username, password, permissions};
        }
        else {
            return new String[]{};
        }
    }

    /**
     * Set the data passed by the input into a user object. If the user does not exist, creates a new user.
     * @param userdata a string array with the format: {username, password, permssions}
     */
    @Deprecated
    public void setSingleUserData(String[] userdata){
        String username = userdata[0];
        String password = userdata[1];
        List<Permission> permissions = StringToPermissions(userdata[2]);
        if (userExists(username)){
            users.get(username).setPassword(password);
            users.get(username).setPermissions(permissions);
        }
        else {
            this.createUser(username, password, permissions);
        }
    }

    /**
     * Instantiates the UserManager
     * @param users an empty or pre-constructed hashmap that relates a username to the appropriate entity.
     */
    @Deprecated
    public UserManager(HashMap<String,User> users) {
        this.users = users;
    }


    /**
     * Convert a string corresponding to permissions into the appropriate Permissions
     * @param permission a string that corresponds to existing Permissions
     * @return a list of Permissions
     */
    @Deprecated
    public List<Permission> StringToPermissions(String permission){
        String[] strList = permission.split(", ");
        ArrayList<Permission> permissions = new ArrayList<>();
        for (String s: strList) {
            permissions.add(Permission.valueOf(s));
        }
        return permissions;
    }

    /**
     * Convert a list of Permissions into a string
     * @param permissions a list of Permissions
     * @return a string with each string of a Permission separated by a comma
     */
    @Deprecated
    public String PermissionsToString(List<Permission> permissions){
        return permissions.toString().replace("[", "").replace("]", "");
    }

    //Permission Methods
    @Deprecated
    public List<Permission> getPermissions(String username){
        return this.users.get(username).getPermissions();
    }

    /**
     * Takes in a list of permissions to replace the current list of permissions a user has
     * @param username the username of the User object who's permissions should be set
     * @param permissions a new list of permissions to set for this user
     */
    @Deprecated
    public void setPermission(String username, List<Permission> permissions){
        this.users.get(username).setPermissions(permissions);
    }

    /**
     * Takes in a single permission to add to the current list of permissions a user has
     * @param username the username of the User object who's permissions should be extended
     * @param permission the permission to add
     */
    @Deprecated
    public void addPermission(String username, Permission permission){
        this.users.get(username).addPermission(permission);
    }

    /**
     * Takes in a single permission to remove from the current list of permissions a user has
     * @param username the username of the User object who's permissions should be reduced
     * @param permission the permission to remove
     */
    @Deprecated
    public void removePermission(String username, Permission permission){
        this.users.get(username).removePermission(permission);
    }

    /**
     * Takes in a Permission Template which in turn corresponds to a pre-defined list of permissions.
     * It returns a list of usernames that have at least permissions defined by the Template.
     * @param template a Template that refers to a type of user
     * @return a list of usernames that meet (or exceed) the criteria defined by the template
     */
    @Deprecated
    public List<String> getUserByPermissionTemplate(Template template){
        List<String> fullFillingUsers = new ArrayList<>();

        for (User user : this.users.values()) {
            if(user.getPermissions().containsAll(template.getPermissions())){
                fullFillingUsers.add(user.getUsername());
            }
        }
        return fullFillingUsers;
    }



}
