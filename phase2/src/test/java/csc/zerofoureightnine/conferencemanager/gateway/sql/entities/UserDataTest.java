package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import csc.zerofoureightnine.conferencemanager.users.Permission;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static csc.zerofoureightnine.conferencemanager.users.Permission.MESSAGE_ALL_USERS;
import static org.junit.Assert.assertEquals;

public class UserDataTest {
    @Test
    public void testUserNameData() {
        UserData userData = new UserData();
        userData.setUserName("admin");
        assertEquals("admin", userData.getUserName());
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
        List<Permission> p = new ArrayList<>();
        p.add(MESSAGE_ALL_USERS);
        userData.setPermissions(p);
        assertEquals(p, userData.getPermissions());
    }

}
