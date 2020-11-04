package Users;

import Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testConstructor() {
        HashMap<String, Boolean> permissions = new HashMap<>();
        permissions.put("message_user", true);
        User u1 = new User("bob", "123", permissions);
    }

    @Test
    public void getPassword() {
        HashMap<String, Boolean> permissions = new HashMap<>();
        permissions.put("message_user", true);
        User u1 = new User("bob", "123", permissions);
        assertEquals(u1.getPassword(), "123");
    }

    @Test
    public void getUsername() {
        HashMap<String, Boolean> permissions = new HashMap<>();
        permissions.put("message_user", true);
        User u1 = new User("bob", "123", permissions);
        assertEquals(u1.getUsername(), "bob");
    }

    @Test
    public void getPermissions() {
        HashMap<String, Boolean> permissions = new HashMap<>();
        permissions.put("message_user", true);
        User u1 = new User("bob", "123", permissions);
        assertEquals(u1.getPermissions(), permissions);
    }

    @Test
    public void setPermissions() {
    }

    @Test
    public void setUsername() {
    }

    @Test
    public void setPassword() {
    }
}