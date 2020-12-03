package csc.zerofoureightnine.conferencemanager.users;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.*;


//TODO: Add exceptions for inappropriate usernames, passwords and permissions :)
public class UserManager {

    /**
     * csc.zerofoureightnine.conferencemanager.users stores a hashmap where the keys are usernames and the corresponding value is a User object.
     */
    private HashMap<String, User> users;

    /**
     * Instantiates the UserManager
     * @param users an empty or pre-constructed hashmap that relates a username to the appropriate entity.
     */
    public UserManager(HashMap<String,User> users) {
        this.users = users;
    }

    //User Methods

    /**
     * Checks whether the user exists in the stored hashmap
     * @param username the username of the user
     * @return true if the user exists in the stored hashmap, false otherwise
     */
    public boolean userExists(String username){
        return this.users.containsKey(username);
    }

    /**
     * Generates a user object and stores it into the hashmap
     * @param username the username of the new user
     * @param password the password of the new user
     * @param permissions the list of permissions the user has access to
     */
    public void createUser(String username, String password, List<Permission> permissions){

        User u = new User(username, password, permissions);
        this.users.put(username, u);
    }

    /**
     * Removes the given username and corresponding User object from the hashmap
     * @param username the username of the User object to be removed
     */
    public void removeUser(String username){

        this.users.remove(username);
    }


    //Permission Methods
    public List<Permission> getPermissions(String username){
        return this.users.get(username).getPermissions();
    }

    /**
     * Takes in a list of permissions to replace the current list of permissions a user has
     * @param username the username of the User object who's permissions should be set
     * @param permissions a new list of permissions to set for this user
     */
    public void setPermission(String username, List<Permission> permissions){
        //TODO: Validate username
        this.users.get(username).setPermissions(permissions);
    }

    /**
     * Takes in a single permission to add to the current list of permissions a user has
     * @param username the username of the User object who's permissions should be extended
     * @param permission the permission to add
     */
    public void addPermission(String username, Permission permission){
        //TODO: Validate username
        this.users.get(username).addPermission(permission);
    }

    /**
     * Takes in a single permission to remove from the current list of permissions a user has
     * @param username the username of the User object who's permissions should be reduced
     * @param permission the permission to remove
     */
    public void removePermission(String username, Permission permission){
        //TODO: Validate username
        this.users.get(username).removePermission(permission);
    }

    /**
     * Takes in a Permission Template which in turn corresponds to a pre-defined list of permissions.
     * It returns a list of usernames that have at least permissions defined by the Template.
     * @param template a Template that refers to a type of user
     * @return a list of usernames that meet (or exceed) the criteria defined by the template
     */
    public List<String> getUserByPermissionTemplate(Template template){
        List<String> fullFillingUsers = new ArrayList<>();

        for (User user : this.users.values()) {
            if(user.getPermissions().containsAll(template.getPermissions())){
                fullFillingUsers.add(user.getUsername());
            }
        }
        return fullFillingUsers;
    }

    //Password Methods
    public String getPassword(String username){
        return this.users.get(username).getPassword();
    }

    public void setPassword(String username, String password){
        this.users.get(username).setPassword(password);
    }

    //Save and Set Methods
    /**
     * Converts the stored hashmap into a list of all csc.zerofoureightnine.conferencemanager.users, whose data is in turn
     * represented by a string array.
     * The format of the string array is: {username, password, permssions}
     * @return a nested list containing string arrays
     */
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
     * Convert a string corresponding to permissions into the appropriate Permissions
     * @param permission a string that corresponds to existing Permissions
     * @return a list of Permissions
     */
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
    public String PermissionsToString(List<Permission> permissions){
        return permissions.toString().replace("[", "").replace("]", "");
    }

    //TODO: this is duplicate code... remove.
    //Validation methods
    public boolean uNameExists(String uname){
        return users.containsKey(uname);
    }


}
