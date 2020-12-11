package csc.zerofoureightnine.conferencemanager.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class PermissionManagerTest {

    public DummyPersistentMap<String, UserData> populatePermissions(){
        DummyPersistentMap<String, UserData> map = new DummyPersistentMap<>();
        UserData falcon = new UserData();
        falcon.getPermissions().addAll(Template.ATTENDEE.getPermissions());
        map.put("falcon", falcon);
        UserData raven = new UserData();
        raven.getPermissions().addAll(Template.SPEAKER.getPermissions());
        map.put("raven", raven);
        UserData robin = new UserData();
        robin.getPermissions().addAll(Template.ORGANIZER.getPermissions());
        map.put("robin", robin);
        return map;
    }

    @Test
    public void getPermissions() {
        PermissionManager pm = new PermissionManager(populatePermissions());
        //Test for existing usernames and correct List of Permissions
        assertEquals(Template.ATTENDEE.getPermissions(), pm.getPermissions("falcon"));
        assertEquals(Template.SPEAKER.getPermissions(), pm.getPermissions("raven"));
        assertEquals(Template.ORGANIZER.getPermissions(), pm.getPermissions("robin"));
        //Test for incorrect List of Permissions
        assertNotEquals(Template.SPEAKER.getPermissions(), pm.getPermissions("falcon"));
    }

    @Test
    public void setPermission() {
        PermissionManager pm = new PermissionManager(populatePermissions());
        //Ensure new data not in the Map
        assertNotEquals(Template.ADMIN.getPermissions(), pm.getPermissions("seagull"));
        //Set for new username
        pm.setPermission("seagull", Template.ADMIN.getPermissions());
        assertEquals(Template.ADMIN.getPermissions(), pm.getPermissions("seagull"));
        //Set for existing username
        pm.setPermission("falcon", Template.ADMIN.getPermissions());
        assertEquals(Template.ADMIN.getPermissions(), pm.getPermissions("falcon"));
    }

    @Test
    public void addPermission() {
        PermissionManager pm = new PermissionManager(populatePermissions());
        //Add first new Permission
        pm.addPermission("falcon", Permission.MESSAGE_EVENTS);
        assertNotEquals(Template.SPEAKER.getPermissions(), pm.getPermissions("falcon"));
        assertNotEquals(Template.ATTENDEE.getPermissions(), pm.getPermissions("falcon"));
        //Add second new Permission
        pm.addPermission("falcon", Permission.VIEW_HOSTING_EVENTS);
        assertTrue(pm.getPermissions("falcon").containsAll(Template.SPEAKER.getPermissions()));
        //Converted and Attendee to Speaker!

    }

    @Test
    public void removePermission() {
        PermissionManager pm = new PermissionManager(populatePermissions());
        //Remove first Permission
        pm.removePermission("raven", Permission.MESSAGE_EVENTS);
        assertNotEquals(Template.SPEAKER.getPermissions(), pm.getPermissions("raven"));
        assertNotEquals(Template.ATTENDEE.getPermissions(), pm.getPermissions("raven"));
        //Remove second Permission
        pm.removePermission("raven", Permission.VIEW_HOSTING_EVENTS);
        assertEquals(Template.ATTENDEE.getPermissions(), pm.getPermissions("raven"));
        //Converted and Speaker to Attendee!
    }

    @Test
    public void getUserByPermissionTemplate() {
        PermissionManager pm = new PermissionManager(populatePermissions());
        //Test when no user matches the Template
        assertTrue(pm.getUserByPermissionTemplate(Template.ADMIN).isEmpty());
        //Test for Attendee level (all users should match)
        List<String> attendees = new ArrayList<>(populatePermissions().keySet());
        assertTrue(attendees.containsAll(pm.getUserByPermissionTemplate(Template.ATTENDEE)));
        //Test for Organizer level
        List<String> organizer = new ArrayList<>();
        organizer.add("robin");
        assertEquals(organizer, pm.getUserByPermissionTemplate(Template.ORGANIZER));
    }

}