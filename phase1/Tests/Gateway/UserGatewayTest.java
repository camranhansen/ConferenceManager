package Gateway;

import org.junit.Test;
import Users.UserManagerTest;
import Users.UserManager;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserGatewayTest {

    @Test
    public void testSaveAllUsers() throws IOException {
        UserGateway ug = new UserGateway();
        UserManager um = new UserManagerTest().createUserManager();
        ug.saveAllUsers(um);
    }

    @Test
    public void testReadFromGateway() {

    }
}