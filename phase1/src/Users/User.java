package Users;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private HashMap<String, Boolean> permissions;

    public User(String username, String password, HashMap<String, Boolean> permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public HashMap<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashMap<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
