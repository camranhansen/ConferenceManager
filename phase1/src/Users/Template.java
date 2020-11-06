package Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Template {
    ATTENDEE(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.MESSAGE_SINGLE_ATTENDEE,
            Permission.VIEW_SELF_CHAT_HISTORY,
            Permission.VIEW_ATTENDING_EVENTS}),
    SPEAKER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.MESSAGE_SINGLE_ATTENDEE,
            Permission.MESSAGE_EVENT_ATTENDEES,
            Permission.VIEW_SELF_CHAT_HISTORY,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_HOSTING_EVENTS}),
    ORGANIZER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,
            Permission.MESSAGE_SINGLE_ATTENDEE,
            Permission.MESSAGE_EVENT_ATTENDEES,
            Permission.MESSAGE_ALL_ATTENDEES,
            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,
            Permission.VIEW_SELF_CHAT_HISTORY,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_HOSTING_EVENTS}),
    ADMIN(new Permission[] {
            Permission.USER_ALL_EDIT_PERMISSION,
            Permission.USER_OTHER_EDIT_PASSWORD,
            Permission.USER_CREATE,
            Permission.USER_DELETE,
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,

            Permission.MESSAGE_SINGLE_ATTENDEE,
            Permission.MESSAGE_EVENT_ATTENDEES,
            Permission.MESSAGE_ALL_ATTENDEES,

            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,

            Permission.VIEW_SELF_CHAT_HISTORY,
            Permission.VIEW_OTHER_CHAT_HISTORY,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_HOSTING_EVENTS});

    private final List<Permission> permissions; //Use getlabel


    private Template(Permission[] permissions){
        this.permissions = Arrays.asList(permissions);
    }

    public List<Permission> getPermissions(){
        return this.permissions;
    }
}
