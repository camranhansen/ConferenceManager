package csc.zerofoureightnine.conferencemanager.users;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.users.login.LoginController;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginControllerTest {

    @Test
    public void testLogin(){
        String input = "bob\n123";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        UserManager um = new UserManager(new UserManagerTest().createUserManager());
        LoginController login = new LoginController(um);

        assertEquals(login.loginUser(), "bob");





    }
}