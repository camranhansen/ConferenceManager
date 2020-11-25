package users;

import java.util.Arrays;
import java.util.List;

/**
 * Template for user creation. The way it is set up now,
 * "higher" permission levels also have the permissions of the
 * "lower" ones, i.e. organizers have access to all speaker functions,
 *  but this can easily be changed later.
 */
public enum Template {
    ATTENDEE(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,

            Permission.MESSAGE_SINGLE_USER,

            Permission.EVENT_SELF_ENROLL,

            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS}),
    SPEAKER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENT_USERS,

            Permission.EVENT_SELF_ENROLL,

            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_HOSTING_EVENTS}),
    ORGANIZER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENT_USERS,
            Permission.MESSAGE_ALL_USERS,

            Permission.EVENT_SELF_ENROLL,
            Permission.EVENT_OTHER_ENROLL,
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

            Permission.EVENT_SELF_ENROLL,
            Permission.EVENT_OTHER_ENROLL,
            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,

            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_OTHER_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_HOSTING_EVENTS});

    private final List<Permission> permissions; //Use getPermissions


    Template(Permission[] permissions){
        this.permissions = Arrays.asList(permissions);
    }

    public List<Permission> getPermissions(){
        return this.permissions;
    }

    //TODO: Store as Array, return as List. Implement method to convert from Array to List.
    //TODO: Investigate inheritance using groups for templates(?)

}
