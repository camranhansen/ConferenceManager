package Users;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class UserControllerTest {

    public UserController createUserController(){
        UserManager um = new UserManagerTest().createUserManager();
        UserController uc = new UserController(um);
        return uc;
    }

    @Test
    public void performSelectedAction() {

    }

    @Test
    public void selectTemplate() {
        UserController uc = this.createUserController();
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(uc.selectTemplate(), Template.ATTENDEE);
        String input2 = "3";
        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(in2);
        assertEquals(uc.selectTemplate(), Template.ADMIN);

    }

    @Test
    public void createAccount() {
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