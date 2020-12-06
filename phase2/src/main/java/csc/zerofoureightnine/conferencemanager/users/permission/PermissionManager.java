package csc.zerofoureightnine.conferencemanager.users.permission;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionManager {
    /**
     * permissions stores a hashmap where the keys are usernames and the corresponding value is a list of Permissions.
     */
    private PersistentMap<String, UserData> permissions;

    /**
     * Instantiates the UserManager
     * @param permissions an empty or pre-constructed hashmap that relates a username to the appropriate entity.
     */
    public PermissionManager(PersistentMap<String,UserData> permissions) {
        this.permissions = permissions;
    }

    /**
     * Returns the List of Permissions corresponding to a given username, if the username exists.
     * Otherwise, returns an empty List.
     * @param username the username of the User object who's Permissions should be retrieved
     * @return List of Permissions
     */
    public List<Permission> getPermissions(String username){
        if (this.permissions.containsKey(username)){
            return this.permissions.load(username).getPermissions();
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * Takes in a List of Permissions to replace the current list of permissions a user has.
     * If user exists in HashMap, replaces value to input permissions.
     * Otherwise, creates new key and sets value to input permissions.
     * @param username the username of the User object who's permissions should be set
     * @param permissions a new list of permissions to set for this user
     */
    public void setPermission(String username, List<Permission> permissions){
        this.permissions.beginInteraction();
        if (this.permissions.containsKey(username)) {
            getPermissions(username).clear();
            getPermissions(username).addAll(permissions);
        }
        else {
            UserData newUser = new UserData();
            newUser.getPermissions().addAll(permissions);
            this.permissions.save(username, newUser);
        }
        this.permissions.endInteraction();
    }

    /**
     * Takes in a single permission to add to the current list of permissions a user has
     * @param username the username of the User object who's permissions should be extended
     * @param permission the permission to add
     */
    public void addPermission(String username, Permission permission){
        this.permissions.beginInteraction();
        getPermissions(username).add(permission);
        this.permissions.endInteraction();
    }

    /**
     * Takes in a single permission to remove from the current list of permissions a user has
     * @param username the username of the User object who's permissions should be reduced
     * @param permission the permission to remove
     */
    public void removePermission(String username, Permission permission){
        this.permissions.beginInteraction();
        getPermissions(username).remove(permission);
        this.permissions.endInteraction();
    }

    /**
     * Takes in a Permission Template which in turn corresponds to a pre-defined list of permissions.
     * It returns a list of usernames that have at least permissions defined by the Template.
     * @param template a Template that refers to a type of user
     * @return a list of usernames that meet (or exceed) the criteria defined by the template
     */
    public List<String> getUserByPermissionTemplate(Template template){
        List<String> fullFillingUsers = new ArrayList<>();

        for (String username : this.permissions.keySet()) {
            if(getPermissions(username).containsAll(template.getPermissions())){
                fullFillingUsers.add(username);
            }
        }
        return fullFillingUsers;
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


}

