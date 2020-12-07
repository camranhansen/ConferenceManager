package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class UserTest {

    @Test
    public void testConstructor() {
        User u1 = new User("bob", "123");
    }

    @Test
    public void getPassword() {
        User u1 = new User("bob", "123");
        assertEquals(u1.getPassword(), "123");
    }

    @Test
    public void getUsername() {
        User u1 = new User("bob", "123");
        assertEquals(u1.getUsername(), "bob");
    }


    @Test
    public void setPassword() {
        User u1 = new User("bob", "123");
        u1.setPassword("54321");
        assertEquals(u1.getPassword(), "54321");
    }


}