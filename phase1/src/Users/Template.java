package Users;

import java.util.Arrays;
import java.util.List;

public enum Template {
    //TODO add EVENT_ENROLL
    ATTENDEE(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.MESSAGE_SINGLE_USER,
            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS}),
    SPEAKER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENT_USERS,
            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_HOSTING_EVENTS}),
    ORGANIZER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,
            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENT_USERS,
            Permission.MESSAGE_ALL_USERS,
            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,
            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_HOSTING_EVENTS}),
    ADMIN(new Permission[] {
            Permission.USER_ALL_EDIT_PERMISSION,
            Permission.USER_OTHER_EDIT_PASSWORD,
            Permission.USER_CREATE_ACCOUNT,
            Permission.USER_DELETE_ACCOUNT,
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENT_USERS,
            Permission.MESSAGE_ALL_USERS,

            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,

            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_OTHER_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_HOSTING_EVENTS});

    private final List<Permission> permissions; //Use getlabel


    private Template(Permission[] permissions){
        this.permissions = Arrays.asList(permissions);
    }

    public List<Permission> getPermissions(){
        return this.permissions;
    }
}
