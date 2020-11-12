package Menus;

import Users.Permission;
import Users.UserController;
import Users.UserManager;
import org.junit.Test;
import Users.UserManagerTest;
import Users.UserControllerTest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MenuControllerTest {

    @Test
    public void selectSubcontroller() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_USER);
        permissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subcontrollers = new HashMap<>();
        SubController uc = new UserControllerTest().createUserController();
        subcontrollers.put("USER",uc);
        MenuController mc = new MenuController("tim",
                permissions, subcontrollers);
        String input = "1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        mc.selectSubcontroller();


    }

    @Test
    public void selectPermission() {
        List<Permission> newpermissions = new ArrayList<>();
        newpermissions.add(Permission.MESSAGE_SINGLE_USER);
        newpermissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subcontrollers = new HashMap<>();

        MenuController mc = new MenuController("bob",
                newpermissions, subcontrollers);

        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals(mc.selectPermission(newpermissions),Permission.USER_ALL_EDIT_PERMISSION);

    }
}