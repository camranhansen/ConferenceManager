package Gateway;

import Users.Permission;
import Users.Template;
import org.junit.Test;
import Users.UserManagerTest;
import Users.UserManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class UserGatewayTest {

    @Test
    public void testSaveAllUsers() throws IOException, NoSuchFieldException, IllegalAccessException {
        UserGateway ug = new UserGateway();
        Field path = ug.getClass().getSuperclass().getDeclaredField("filePath");
        path.setAccessible(true);
        path.set(ug, "testfiles/user_data.csv");
        UserManager um = new UserManagerTest().createUserManager();
        um.createUser("newTester", "test123", Template.ORGANIZER.getPermissions());
        ug.saveAllUsers(um);
    }

    @Test
    public void testReadFromGateway() throws IOException, NoSuchFieldException, IllegalAccessException {
        //Load data from the csv
        UserGateway ug = new UserGateway();
        Field path = ug.getClass().getSuperclass().getDeclaredField("filePath");
        path.setAccessible(true);
        path.set(ug, "testfiles/user_data_read_only.csv");
        UserManager um = new UserManager(new HashMap<>());
        ug.readUsersFromGateway(um);
        ArrayList<String[]> userData = um.getAllUserData();

        //Create representations of users from the csv
        ArrayList<Permission> permission1 = new ArrayList<>();
        permission1.add(Permission.MESSAGE_SINGLE_USER);
        String[] u1 = new String[]{"bob", "123", um.PermissionsToString(permission1)};

        ArrayList<Permission> permission2 = new ArrayList<>();
        permission2.add(Permission.MESSAGE_SINGLE_USER);
        permission2.add(Permission.USER_ALL_EDIT_PERMISSION);
        String[] u2 = new String[]{"louis","asdf", um.PermissionsToString(permission2)};
        //Check for equality
        assertNotEquals(0, userData.size());
        for (String[] s: userData) {
            assertTrue(Arrays.equals(u1, s) || Arrays.equals(u2, s));
            System.out.println(Arrays.toString(s));
        }
    }
}