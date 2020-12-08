package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.List;

@Deprecated
public class User {
    private final String username;
    private String password;
    @Deprecated
    private List<Permission> permissions;

    /**
     * Stores user data.
     * @param username the username
     * @param password password
     * @param permissions list of permissions associated with this user
     */
    @Deprecated
    public User(String username, String password, List<Permission> permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    /**
     * Stores user data.
     * @param username the username
     * @param password password
     */
    public User(String username, String password ) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Deprecated
    public List<Permission> getPermissions() {
        return permissions;
    }

    @Deprecated
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Deprecated
    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    @Deprecated
    public void removePermission(Permission permission) {
        this.permissions.remove(permission);
    }

    @Deprecated
    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }
}
