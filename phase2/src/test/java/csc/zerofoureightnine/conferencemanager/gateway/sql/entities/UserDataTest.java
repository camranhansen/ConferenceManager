package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import org.junit.Test;

import static csc.zerofoureightnine.conferencemanager.users.permission.Permission.MESSAGE_ALL_USERS;
import static org.junit.Assert.assertEquals;

public class UserDataTest {
    @Test
    public void testUserNameData() {
        UserData userData = new UserData();
        userData.setId("admin");
        assertEquals("admin", userData.getId());
    }

    @Test
    public void testPasswordData() {
        UserData userData = new UserData();
        userData.setPassword("12345");
        assertEquals("12345", userData.getPassword());
    }

    @Test
    public void testPermissionsData() {
        UserData userData = new UserData();
        userData.getPermissions().add(Permission.MESSAGE_ALL_USERS);
        assertEquals(Permission.MESSAGE_ALL_USERS, userData.getPermissions().get(0));
    }

}
