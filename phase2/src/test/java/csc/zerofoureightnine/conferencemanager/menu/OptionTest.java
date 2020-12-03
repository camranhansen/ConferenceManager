package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import org.junit.Test;
import static org.junit.Assert.*;

public class OptionTest {

    @Test
    public void constructOptionTextTest(){
        Option option = new Option("Go left");
        assertEquals("Go left", option.toString());
    }

    @Test
    public void constructOptionPermissionTest(){
        Option option = new Option("Send to one", Permission.MESSAGE_SINGLE_USER);
        assertEquals("Send to one", option.toString());
        assertEquals(Permission.MESSAGE_SINGLE_USER, option.getPermissionHeld());
    }

    @Test
    public void constructOptionTemplateTest(){
        Option option = new Option("option", Template.ATTENDEE);
        assertEquals("option", option.toString());
        assertEquals(Template.ATTENDEE, option.getTemplateHeld());
    }

    @Test
    public void runOptionTest(){
        Option option = new Option("option", Permission.MESSAGE_SINGLE_USER){
            @Override
            public void run(){
            }
        };
        option.run();
        assertEquals(Permission.MESSAGE_SINGLE_USER, option.getPermissionHeld());
        assertEquals("option", option.toString());
    }
}
