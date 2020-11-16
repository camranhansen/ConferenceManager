package Menus;

import Events.EventController;
import Events.EventManager;
import Messaging.MessageController;
import Messaging.MessageManager;
import Users.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MenuControllerTest {

    @Test
    public void selectSubController() {
        String input = "2\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_USER);
        permissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subControllers = new HashMap<>();
        SubController uc = new UserControllerTest().createUserController();
        subControllers.put("USER",uc);
        MenuController mc = new MenuController("tim",
                permissions, subControllers);

        assertTrue(mc.selectSubcontroller());
        //THIS will only work if user is selected, since messageController is not put in subControllers
        //Therefore would call a nullPointer exception
        //This is because I do not wish to have to test the mutability.
    }

    @Test
    public void selectPermission() {
        String input = "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        List<Permission> newPermissions = new ArrayList<>();
        newPermissions.add(Permission.MESSAGE_SINGLE_USER);
        newPermissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subControllers = new HashMap<>();
        MenuController mc = new MenuController("bob",
                newPermissions, subControllers);

        assertEquals(Permission.USER_ALL_EDIT_PERMISSION, mc.selectPermission(newPermissions).getPermissionHeld());
    }

    @Test
    public void constructMenuControllerTest(){
        MenuController menuController = generateMenuController();
    }

    @Test
    public void selectPermissionInvalidInputTest(){
        String input = "5\n1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        MenuController menuController = generateMenuController();
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_USER);
        permissions.add(Permission.MESSAGE_ALL_USERS);
        permissions.add(Permission.VIEW_SELF_MESSAGES);
        assertEquals(Permission.MESSAGE_SINGLE_USER, menuController.selectPermission(permissions).getPermissionHeld());
    }

    private MenuController generateMenuController(){
        String username = "u1";
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MESSAGE_SINGLE_USER);
        permissions.add(Permission.MESSAGE_ALL_USERS);
        permissions.add(Permission.VIEW_SELF_MESSAGES);
        permissions.add(Permission.USER_ALL_EDIT_PERMISSION);
        HashMap<String,SubController> subControllers = new HashMap<>();
        MessageManager messageManager = new MessageManager();
        EventManager eventManager = new EventManager();
        HashMap<String, User> users = generateUserHash();
        UserManager userManager = new UserManager(users);
        SubController messageController = new MessageController(messageManager, userManager, eventManager);
        SubController userController = new UserController(userManager);
        SubController eventController = new EventController(eventManager, userManager);
        subControllers.put("message controller", messageController);
        subControllers.put("user controller", userController);
        subControllers.put("event controller", eventController);
        return new MenuController(username, permissions, subControllers);
    }

    private HashMap<String, User> generateUserHash() {
        User u1 = new User("u1", "pass1", Template.ATTENDEE.getPermissions());
        User u2 = new User("u2", "pass2", Template.ATTENDEE.getPermissions());
        User spk1 = new User("spk1", "pass1", Template.SPEAKER.getPermissions());
        User spk2 = new User("spk2", "pass2", Template.SPEAKER.getPermissions());
        HashMap<String, User> users = new HashMap<>();
        users.put(u1.getUsername(), u1);
        users.put(u2.getUsername(), u2);
        users.put(spk1.getUsername(), spk1);
        users.put(spk2.getUsername(), spk2);
        return users;
    }
}