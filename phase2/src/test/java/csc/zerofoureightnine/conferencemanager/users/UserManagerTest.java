package csc.zerofoureightnine.conferencemanager.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;

public class UserManagerTest {

    public PersistentMap<String, UserData> createUserManager(){
        DummyPersistentMap<String, UserData> map = new DummyPersistentMap<>();
        UserData u1 = new UserData();
        u1.setId("bob");
        u1.setPassword( "123");
        UserData u2 = new UserData();
        u2.setId("louis");
        u2.setPassword( "asdf");
        map.put(u1.getId(), u1);
        map.put(u2.getId(), u2);
        return map;
    }

    @Test
    public void userExists() {
        UserManager um = new UserManager(createUserManager());
        assertTrue(um.userExists("bob"));
        assertFalse(um.userExists("tom"));

    }

    @Test
    public void createUser() {
        UserManager um = new UserManager(createUserManager());
        um.createUser("timmy","asdf1234");
        assertTrue(um.userExists("timmy"));
        assertEquals("asdf1234", um.getPassword("timmy"));
    }

    @Test
    public void removeUser() {
        UserManager um = new UserManager(createUserManager());
        um.removeUser("tom");
        assertTrue(um.userExists("bob"));
        assertFalse(um.userExists("tom"));
    }

    @Test
    public void setPassword() {
        UserManager um = new UserManager(createUserManager());
        assertEquals("123", um.getPassword("bob"));
        um.setPassword("bob","789");
        assertNotEquals("123", um.getPassword("bob"));
        assertEquals("789", um.getPassword("bob"));
    }

}