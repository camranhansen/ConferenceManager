package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import org.junit.Test;

import java.util.*;

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

    @Test
    public void userExists() {

        UserManager um = createUserManager();

        assertTrue(um.userExists("bob"));
        assertFalse(um.userExists("tom"));

    }

    @Test
    public void createUser() {

        UserManager um = createUserManager();

        List<Permission> newpermissions = new ArrayList<>();
        newpermissions.add(Permission.MESSAGE_SINGLE_USER);
        newpermissions.add(Permission.USER_ALL_EDIT_PERMISSION);
            um.createUser("timmy","asdf1234",newpermissions);
        assertTrue(um.userExists("timmy"));

    }

    @Test
    public void removeUser() {
        UserManager um = createUserManager();
        um.removeUser("tom");
        assertTrue(um.userExists("bob"));
        assertFalse(um.userExists("tom"));


    }

    @Test
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

    @Test
    public void getUserByPermissionTemplate() {
        UserManager um = createUserManager();
        List<Permission> permissionsToAdd = new ArrayList<>();


        assertTrue(um.getUserByPermissionTemplate(Template.ATTENDEE).isEmpty());


        um.createUser("joe","imjoe",Template.ATTENDEE.getPermissions());
        assertFalse(um.getUserByPermissionTemplate(Template.ATTENDEE).isEmpty());
        assertTrue(um.getUserByPermissionTemplate(Template.ATTENDEE).contains("joe"));

    }

    @Test
    public void addPermission() {
        UserManager um = createUserManager();
        assertFalse(um.getPermissions("bob").contains(Permission.USER_DELETE_ACCOUNT));
        um.addPermission("bob",Permission.USER_DELETE_ACCOUNT);
        assertTrue(um.getPermissions("bob").contains(Permission.USER_DELETE_ACCOUNT));

    }


    @Test
    public void removePermission() {
        UserManager um = createUserManager();
        assertTrue(um.getPermissions("bob").contains(Permission.MESSAGE_SINGLE_USER));
        um.removePermission("bob", Permission.MESSAGE_SINGLE_USER);
        assertFalse(um.getPermissions("bob").contains(Permission.MESSAGE_SINGLE_USER));

    }


    @Test
    public void setPassword() {
        UserManager um = createUserManager();
        assertEquals(um.getPassword("bob"), "123");
        um.setPassword("bob","789");
        assertNotEquals(um.getPassword("bob"),"123");
        assertEquals(um.getPassword("bob"),"789");
    }

    //Save and Set methods for Gateway
    public HashMap<String, User> generateUserData(){
        User u1 = new User("bob", "1234", Template.ATTENDEE.getPermissions());
        User u2 = new User("jane", "5678", Template.ATTENDEE.getPermissions());
        User u3 = new User("doe", "9012", Template.SPEAKER.getPermissions());
        HashMap<String, User> userHashMap = new HashMap<>();
        userHashMap.put(u1.getUsername(), u1);
        userHashMap.put(u2.getUsername(), u2);
        userHashMap.put(u3.getUsername(), u3);
        return userHashMap;
    }

    @Test
    public void getSingleUserData(){
        //Test empty UserManager
        UserManager um1 = new UserManager(new HashMap<>());
        assertArrayEquals(new String[]{}, um1.getSingleUserData(""));

        //Test correct data
        UserManager um2 = new UserManager(generateUserData());
        String permissions1 = um2.PermissionsToString(Template.ATTENDEE.getPermissions());
        assertArrayEquals(new String[]{"bob", "1234",permissions1}, um2.getSingleUserData("bob"));
        assertNotEquals(new String[]{"doe", "9012",permissions1}, um2.getSingleUserData("doe"));
        //Test with additional data
        String permissions2 = um2.PermissionsToString(Template.SPEAKER.getPermissions());
        assertArrayEquals(new String[]{"doe", "9012",permissions2}, um2.getSingleUserData("doe"));
    }

    @Test
    public void getAllUserData(){
        UserManager um = new UserManager(generateUserData());
        String[] u1 = um.getSingleUserData("bob");
        String[] u2 = um.getSingleUserData("jane");
        String[] u3 = um.getSingleUserData("doe");
        ArrayList<String[]> userList = um.getAllUserData();

        assertEquals(3, userList.size());
        for (String[] data : userList) {
            assertTrue(Arrays.equals(u1, data) || Arrays.equals(u2, data) || Arrays.equals(u3, data));
        }

    }

    @Test
    public void setSingleUserData(){
        UserManager um = new UserManager(new HashMap<>());
        assertEquals(new ArrayList<>(), um.getAllUserData());
        String[] u1 = new String[]{"bob", "1234", um.PermissionsToString(Template.ATTENDEE.getPermissions())};
        um.setSingleUserData(u1);
        assertArrayEquals(u1, um.getSingleUserData("bob"));
    }

    @Test
    public void StringToPermissions(){
        UserManager um = new UserManager(new HashMap<>());
        String str1 = "MESSAGE_ALL_USERS, USER_ALL_EDIT_PERMISSION, VIEW_SELF_MESSAGES";
        List<Permission> permissions1 = new ArrayList<>();
        permissions1.add(Permission.MESSAGE_ALL_USERS);
        permissions1.add(Permission.USER_ALL_EDIT_PERMISSION);
        permissions1.add(Permission.VIEW_SELF_MESSAGES);

        String str2 = "MESSAGE_ALL_USERS";
        List<Permission> permissions2 = new ArrayList<>();
        permissions2.add(Permission.MESSAGE_ALL_USERS);
        assertEquals(permissions1, um.StringToPermissions(str1));
        assertEquals(permissions2, um.StringToPermissions(str2));
        assertNotEquals(permissions1, um.StringToPermissions(str2));
    }

    @Test
    public void PermissionsToString(){
        UserManager um = new UserManager(new HashMap<>());
        List<Permission> permissions1 = new ArrayList<>();
        permissions1.add(Permission.MESSAGE_ALL_USERS);
        permissions1.add(Permission.USER_ALL_EDIT_PERMISSION);
        permissions1.add(Permission.VIEW_SELF_MESSAGES);

        String str1 = "MESSAGE_ALL_USERS, USER_ALL_EDIT_PERMISSION, VIEW_SELF_MESSAGES";
        assertEquals(str1, um.PermissionsToString(permissions1));
    }
}