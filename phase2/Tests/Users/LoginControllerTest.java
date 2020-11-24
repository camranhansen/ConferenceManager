package Users;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginControllerTest {

    //TODO: Find a way to import this exact function from
    //UserManagerTest
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
    public void testLogin(){
        String input = "bob\n123";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        UserManager um = createUserManager();
        LoginController login = new LoginController(um);

        assertEquals(login.loginUser(), "bob");





    }
}