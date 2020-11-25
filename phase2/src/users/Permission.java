package users;

/**
 * Permissions available to the user. See template for specific permissions for user types.
 */
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Messages
    MESSAGE_ALL_USERS("MESSAGE"), //Determine the group of users you want to message(i.e. all users, specific permission group)
    MESSAGE_EVENT_USERS("MESSAGE"), //Determine whether the logged in user wants to message a specific event that they are hosting, or all events that they are hosting.
    MESSAGE_SINGLE_USER("MESSAGE"), //Determine the recipient user.

    //Event
    EVENT_SELF_ENROLL("EVENT"), //Determine whether want to enroll or unenroll. Determine the specific event to either enroll or unenroll.
    EVENT_OTHER_ENROLL("EVENT"), //Likewise, except also determine the username of the other person in question.
    EVENT_CREATE("EVENT"), //Takes in event parameters.
    EVENT_DELETE("EVENT"), //Delete event by ID.
    EVENT_EDIT("EVENT"), //Determine event ID, then determine what parameter you want to edit.

    //User
    USER_ALL_EDIT_PERMISSION("USER"),
    USER_SELF_EDIT_PASSWORD("USER"),
    USER_OTHER_EDIT_PASSWORD("USER"),
    USER_CREATE_ACCOUNT("USER"),
    USER_CREATE_SPEAKER_ACCOUNT("USER"),
    USER_DELETE_ACCOUNT("USER"), //Delete other people's account, or yourself

    //Viewing

    VIEW_HOSTING_EVENTS("EVENT"), //View the events you are speaking at (i.e. where speakerName = your name)
    VIEW_ALL_EVENTS("EVENT"), //Anyone can do this.
    // Determine whether the user wants to see:
    // 1. All the events they are attending
    // 2. All available events to them specifically
    // 3. All events regardless of availability
    VIEW_SELF_MESSAGES("MESSAGE"), //Determine whether you want to see all messages, or specific history between you and another user.
    VIEW_OTHER_MESSAGES("MESSAGE"), //Not necessary now. Determine the other user, then call view_self_messages from the perspective of that user

    ;

    private final String category; //Use getCategory


    Permission(String category){
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }
}
