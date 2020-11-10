package Menus;

import Users.LoginController;
import Users.Permission;
import Users.UserManager;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MenuControllerTest {

    @Test
    public void selectSubcontroller() {
        //TODO!!

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