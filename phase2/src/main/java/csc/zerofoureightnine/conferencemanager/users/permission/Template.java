package csc.zerofoureightnine.conferencemanager.users.permission;

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
            Permission.USER_CREATE_REQUEST, //New
            Permission.USER_SELF_EDIT_REQUEST, //New

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_MOVE, //New
            Permission.MESSAGE_DELETE,//New

            Permission.EVENT_SELF_ENROLL,
            Permission.EVENT_SELF_DROP,


            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_AVAILABLE_EVENTS,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_SELF_REQUESTS, //New
            Permission.VIEW_SELF_STATISTICS //New
    }),
    SPEAKER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_REQUEST, //New
            Permission.USER_SELF_EDIT_REQUEST, //New

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENTS,
            Permission.MESSAGE_MOVE, //New
            Permission.MESSAGE_DELETE,//New

            Permission.EVENT_SELF_ENROLL,
            Permission.EVENT_SELF_DROP,


            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_AVAILABLE_EVENTS,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_HOSTING_EVENTS,
            Permission.VIEW_SELF_REQUESTS, //New
            Permission.VIEW_SELF_STATISTICS //New
    }),
    ORGANIZER(new Permission[] {
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,
            Permission.USER_CREATE_REQUEST, //New
            Permission.USER_SELF_EDIT_REQUEST, //New
            Permission.USER_OTHER_EDIT_REQUEST, //New

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENTS,
            Permission.MESSAGE_MOVE, //New
            Permission.MESSAGE_DELETE,//New

            Permission.EVENT_SELF_ENROLL,
            Permission.EVENT_SELF_DROP,
            Permission.EVENT_OTHER_ENROLL,
            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,

            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_AVAILABLE_EVENTS,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_HOSTING_EVENTS,
            Permission.VIEW_SELF_REQUESTS, //New
            Permission.VIEW_SELF_STATISTICS, //New
            Permission.VIEW_OTHER_PENDING_REQUESTS, //New
            Permission.VIEW_OTHER_ADDRESSED_REQUESTS,
            Permission.VIEW_ALL_STATISTICS //New
    }),
    ADMIN(new Permission[] {
            Permission.USER_ALL_EDIT_PERMISSION,
            Permission.USER_OTHER_EDIT_PASSWORD,
            Permission.USER_CREATE_ACCOUNT,
            Permission.USER_SELF_DELETE_ACCOUNT,
            Permission.USER_OTHER_DELETE_ACCOUNT,
            Permission.USER_SELF_EDIT_PASSWORD,
            Permission.USER_CREATE_SPEAKER_ACCOUNT,
            Permission.USER_CREATE_REQUEST, //New
            Permission.USER_SELF_EDIT_REQUEST, //New
            Permission.USER_OTHER_EDIT_REQUEST, //New

            Permission.MESSAGE_SINGLE_USER,
            Permission.MESSAGE_EVENTS,
            Permission.MESSAGE_ALL_USERS,
            Permission.MESSAGE_MOVE, //New
            Permission.MESSAGE_DELETE,//New

            Permission.EVENT_SELF_ENROLL,
            Permission.EVENT_SELF_DROP,
            Permission.EVENT_OTHER_ENROLL,
            Permission.EVENT_CREATE,
            Permission.EVENT_DELETE,
            Permission.EVENT_EDIT,

            Permission.VIEW_SELF_MESSAGES,
            Permission.VIEW_OTHER_MESSAGES,
            Permission.VIEW_ALL_EVENTS,
            Permission.VIEW_ATTENDING_EVENTS,
            Permission.VIEW_AVAILABLE_EVENTS,
            Permission.VIEW_SELF_REQUESTS, //New
            Permission.VIEW_HOSTING_EVENTS,
            Permission.VIEW_SELF_STATISTICS, //New
            Permission.VIEW_OTHER_PENDING_REQUESTS, //New
            Permission.VIEW_OTHER_ADDRESSED_REQUESTS,
            Permission.VIEW_ALL_STATISTICS //New
    });

    private final List<Permission> permissions; //Use getPermissions


    Template(Permission[] permissions){
        this.permissions = Arrays.asList(permissions);
    }

    public List<Permission> getPermissions(){
        return this.permissions;
    }


}
