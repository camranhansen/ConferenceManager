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
        String input = "2\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_USER);
        permissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subcontrollers = new HashMap<>();
        SubController uc = new UserControllerTest().createUserController();
        subcontrollers.put("USER",uc);
        MenuController mc = new MenuController("tim",
                permissions, subcontrollers);


        mc.selectSubcontroller();
        //THIS will only work if user is selected, since messagecontroller is not put in subcontrollers
        //Therefore would call a nullpointer exception
        //This is because I do not wish to have to test the mutability.

    }

    @Test
    public void selectPermission() {
        String input = "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        List<Permission> newpermissions = new ArrayList<>();
        newpermissions.add(Permission.MESSAGE_SINGLE_USER);
        newpermissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subcontrollers = new HashMap<>();

        MenuController mc = new MenuController("bob",
                newpermissions, subcontrollers);



        assertEquals(mc.selectPermission(newpermissions),Permission.USER_ALL_EDIT_PERMISSION);

    }
}