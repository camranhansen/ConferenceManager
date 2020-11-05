package Users;
import java.util.HashMap;
import java.util.List;


//TODO: Add exceptions for inappropriate usernames, passwords and permissions :)
public class UserManager {
    private HashMap<String, User> users;

    public UserManager(HashMap<String,User> users) {
        this.users = users;
    }

    //User Methods
    //TODO: Use this logic as an example to create exceptions
    public boolean userExists(String username){
        return this.users.containsKey(username);
    }

    public void createUser(String username, String password, List<Permission> permissions){
        //TODO: Validate username and password
        User u = new User(username, password, permissions);
        this.users.put(username, u);
    }

    public void removeUser(String username){
        //TODO: Validate username
        this.users.remove(username);
    }

    //Permission Methods
    public List<Permission> getPermissions(String username){
        //TODO: Validate username
        return this.users.get(username).getPermissions();
    }

    public void changePermission(String username, List<Permission> permissions){
        //TODO: Validate username
        this.users.get(username).setPermissions(permissions);
    }

    public void addPermission(String username, Permission permission){
        //TODO: Validate username
        this.users.get(username).addPermission(permission);
    }

    public void removePermission(String username, Permission permission){
        //TODO: Validate username
        this.users.get(username).removePermission(permission);
    }

    //Password Methods
    public String getPassword(String username){
        //TODO: Validate username
        return this.users.get(username).getPassword();
    }

    public void changePassword(String username, String password){
        //TODO: Validate username and password
        this.users.get(username).setPassword(password);
    }
}