package Users;
import java.util.*;


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

    public void setPermission(String username, List<Permission> permissions){
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
        //TODO: Validate username
        return this.users.get(username).getPassword();
    }

    public void setPassword(String username, String password){
        //TODO: Validate username and password
        this.users.get(username).setPassword(password);
    }

    //Gateway Methods
    public ArrayList<String[]> getAllUserData(){
        ArrayList<String[]> userList = new ArrayList<>();
        for (User u: users.values()) {
            userList.add(getSingleUserData(u.getUsername()));
        }
        return userList;
    }

    public String[] getSingleUserData(String username){
        String password = users.get(username).getPassword();
        String permissions =  Arrays.deepToString(users.get(username).getPermissions().toArray());
        return new String[]{username, password, permissions};
    }

    public void setSingleUserData(String[] userdata){
    }
}
