package Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;

public class UserManagerTest {

    public UserManager createUserManager(){
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_USER);
        User u1 = new User("bob", "123", permissions);

        List<Permission> permissions2 = new ArrayList<>();
        permissions2.add(Permission.MESSAGE_SINGLE_USER);
        permissions2.add(Permission.USER_ALL_EDIT_PERMISSION);
        User u2 = new User("louis", "asdf", permissions2);

        HashMap<String,User> usermap = new HashMap<>();
        usermap.put(u1.getUsername(), u1);
        usermap.put(u2.getUsername(), u2);
        UserManager um = new UserManager(usermap);
        return um;
    }

    @org.junit.Test
    public void userExists() {

        UserManager um = createUserManager();

        assertTrue(um.userExists("bob"));
        assertFalse(um.userExists("tom"));

    }

    @org.junit.Test
    public void createUser() {

        UserManager um = createUserManager();

        List<Permission> newpermissions = new ArrayList<>();
        newpermissions.add(Permission.MESSAGE_SINGLE_USER);
        newpermissions.add(Permission.USER_ALL_EDIT_PERMISSION);
            um.createUser("timmy","asdf1234",newpermissions);
        assertTrue(um.userExists("timmy"));

    }

    @org.junit.Test
    public void removeUser() {
        UserManager um = createUserManager();
        um.removeUser("tom");
        assertTrue(um.userExists("bob"));
        assertFalse(um.userExists("tom"));


    }

    @org.junit.Test
    public void setPermission() {
        UserManager um = createUserManager();
        List<Permission> permissionsToAdd = new ArrayList<>();
        permissionsToAdd.add(Permission.MESSAGE_SINGLE_USER);
        permissionsToAdd.add(Permission.USER_OTHER_EDIT_PASSWORD);
        permissionsToAdd.add(Permission.USER_CREATE_ACCOUNT);

        assertNotEquals(um.getPermissions("bob"),permissionsToAdd);
        um.setPermission("bob",permissionsToAdd);
        assertEquals(um.getPermissions("bob"), permissionsToAdd);
    }

    @org.junit.Test
    public void getUserByPermissionTemplate() {
        UserManager um = createUserManager();
        List<Permission> permissionsToAdd = new ArrayList<>();
        permissionsToAdd.add(Permission.USER_SELF_EDIT_PASSWORD);
        permissionsToAdd.add(Permission.MESSAGE_SINGLE_USER);
        permissionsToAdd.add(Permission.VIEW_ALL_EVENTS);
        permissionsToAdd.add(Permission.VIEW_SELF_MESSAGES);


        assertTrue(um.getUserByPermissionTemplate(Template.ATTENDEE).isEmpty());


        um.createUser("joe","imjoe",permissionsToAdd);
        assertFalse(um.getUserByPermissionTemplate(Template.ATTENDEE).isEmpty());
        assertTrue(um.getUserByPermissionTemplate(Template.ATTENDEE).contains("joe"));

    }

    @org.junit.Test
    public void addPermission() {
        UserManager um = createUserManager();
        assertFalse(um.getPermissions("bob").contains(Permission.USER_DELETE_ACCOUNT));
        um.addPermission("bob",Permission.USER_DELETE_ACCOUNT);
        assertTrue(um.getPermissions("bob").contains(Permission.USER_DELETE_ACCOUNT));

    }


    @org.junit.Test
    public void removePermission() {
        UserManager um = createUserManager();
        assertTrue(um.getPermissions("bob").contains(Permission.MESSAGE_SINGLE_USER));
        um.removePermission("bob", Permission.MESSAGE_SINGLE_USER);
        assertFalse(um.getPermissions("bob").contains(Permission.MESSAGE_SINGLE_USER));

    }


    @org.junit.Test
    public void setPassword() {
        UserManager um = createUserManager();
        assertEquals(um.getPassword("bob"), "123");
        um.setPassword("bob","789");
        assertNotEquals(um.getPassword("bob"),"123");
        assertEquals(um.getPassword("bob"),"789");
    }
}