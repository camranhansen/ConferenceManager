package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class UserControllerTest {

    public UserController createUserController(){
        UserManager um = new UserManager(new UserManagerTest().createUserManager());
        UserController uc = new UserController(um);
        return uc;
    }

    @Test
    public void performSelectedAction() {

    }

    public void testsetset(UserController uc){
        System.out.println(uc.selectTemplate().toString());
    }


    @Test
    public void selectTemplate() {
        //TODO: This test does not run because of some strange interaction
        //Between scanner and having it in another file.
        //It would work if a scanner object was instantiated inside usercontroller
        String input = "0"+System.lineSeparator()+"0"+System.lineSeparator()+"0"+System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        UserController uc = this.createUserController();
        assertEquals(uc.selectTemplate(), Template.ATTENDEE);
//        String input2 = "4";
//        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
//        System.setIn(in2);
//        assertEquals(uc.selectTemplate(), Template.ADMIN);

    }

    @Test
    public void createAccount() {
        //TODO write these other tests.
    }

    @Test
    public void deleteAccount() {
    }

    @Test
    public void editPassword() {
    }

    @Test
    public void editOtherPassword() {
    }
}