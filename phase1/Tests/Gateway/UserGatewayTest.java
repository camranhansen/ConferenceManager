package Gateway;

import org.junit.Test;
import Users.UserManagerTest;
import Users.UserManager;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UserGatewayTest {

    @Test
    public void testSaveAllUsers() throws IOException, NoSuchFieldException, IllegalAccessException {
        UserGateway ug = new UserGateway();
        Field path = ug.getClass().getSuperclass().getDeclaredField("filePath");
        path.setAccessible(true);
        path.set(ug, "testfiles/user_data.csv");
        UserManager um = new UserManagerTest().createUserManager();
        ug.saveAllUsers(um);
    }

}