package Users;

import Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
//TODO: make the contents of these tests less repetitive by creating a helper function
public class UserTest {

    @Test
    public void testConstructor() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        User u1 = new User("bob", "123", permissions);
    }

    @Test
    public void getPassword() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        User u1 = new User("bob", "123", permissions);
        assertEquals(u1.getPassword(), "123");
    }

    @Test
    public void getUsername() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        User u1 = new User("bob", "123", permissions);
        assertEquals(u1.getUsername(), "bob");
    }

    @Test
    public void getPermissions() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        User u1 = new User("bob", "123", permissions);
        assertEquals(u1.getPermissions(), permissions);
    }

    @Test
    public void setPermissions() {
        List<Permission> permissions1 = new ArrayList<>();
        permissions1.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        List<Permission> permissions2 = new ArrayList<>();
        permissions2.add(Permission.MESSAGE_EVENT_ATTENDEES);
        User u1 = new User("bob", "123", permissions1);
        u1.setPermissions(permissions2);
        assertEquals(u1.getPermissions(), permissions2);
    }

    @Test
    public void setPassword() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        User u1 = new User("bob", "123", permissions);
        u1.setPassword("54321");
        assertEquals(u1.getPassword(), "54321");
    }

    @Test
    public void addPermission() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        permissions.add(Permission.MESSAGE_EVENT_ATTENDEES);
        User u1 = new User("bob", "123", permissions);
        u1.addPermission(Permission.MESSAGE_ALL_ATTENDEES);

        List<Permission> testPermissionList = new ArrayList<>();
        testPermissionList.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        testPermissionList.add(Permission.MESSAGE_EVENT_ATTENDEES);
        testPermissionList.add(Permission.MESSAGE_ALL_ATTENDEES);

        assertEquals(u1.getPermissions(), testPermissionList);
    }

    @Test
    public void removePermission() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        permissions.add(Permission.MESSAGE_EVENT_ATTENDEES);
        User u1 = new User("bob", "123", permissions);
        u1.removePermission(Permission.MESSAGE_EVENT_ATTENDEES);

        List<Permission> testPermissionList = new ArrayList<>();
        testPermissionList.add(Permission.MESSAGE_SINGLE_ATTENDEE);

        assertEquals(u1.getPermissions(), testPermissionList);
    }

    @Test
    public void hasPermission() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_ATTENDEE);
        permissions.add(Permission.MESSAGE_EVENT_ATTENDEES);
        User u1 = new User("bob", "123", permissions);
        u1.addPermission(Permission.MESSAGE_ALL_ATTENDEES);

        assertTrue(u1.hasPermission(Permission.MESSAGE_SINGLE_ATTENDEE));
        assertTrue(u1.hasPermission(Permission.MESSAGE_ALL_ATTENDEES));
        assertFalse(u1.hasPermission(Permission.EVENT_DELETE));
    }

    @Test
    public void permissionValue(){
        assertEquals(Permission.valueOf("MESSAGE_ALL_ATTENDEES"),Permission.MESSAGE_ALL_ATTENDEES);


    }
}