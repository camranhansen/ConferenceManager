package csc.zerofoureightnine.conferencemanager.users;

/**
 * Permissions available to the user. See template for specific permissions for user types.
 */
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Messages
    MESSAGE_ALL_USERS("MESSAGE"), //Determine the group of csc.zerofoureightnine.conferencemanager.users you want to message(i.e. all csc.zerofoureightnine.conferencemanager.users, specific permission group)
    MESSAGE_EVENT_USERS("MESSAGE"), //Determine whether the logged in user wants to message a specific event that they are hosting, or all csc.zerofoureightnine.conferencemanager.events that they are hosting.
    MESSAGE_SINGLE_USER("MESSAGE"), //Determine the recipient user.
    //New for Phase 2!
    MESSAGE_ARCHIVE("MESSAGE"), //Determine the message to archive.
    MESSAGE_DELETE("MESSAGE"), //Determine the message to delete.
    MESSAGE_UNREAD("MESSAGE"), //Determine the message to unread.

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
    USER_DELETE_ACCOUNT("USER"), //Delete other people's account, or yourself.
    //New for Phase 2!
    USER_CREATE_REQUEST("USER"), //Create a SpecialRequest.
    USER_SELF_EDIT_REQUEST("USER"), //Determine the SpecialRequest, and the type of edit to perform.
    USER_OTHER_EDIT_REQUEST("USER"), //Determine the user whose SpecialRequest should be edited, and edit it.


    //Viewing
    VIEW_HOSTING_EVENTS("EVENT"), //View the csc.zerofoureightnine.conferencemanager.events you are speaking at (i.e. where speakerName = your name)
    VIEW_ALL_EVENTS("EVENT"), //Anyone can do this.
    // Determine whether the user wants to see:
    // 1. All the csc.zerofoureightnine.conferencemanager.events they are attending.
    // 2. All available csc.zerofoureightnine.conferencemanager.events to them specifically.
    // 3. All csc.zerofoureightnine.conferencemanager.events regardless of availability.
    VIEW_SELF_MESSAGES("MESSAGE"), //Determine whether you want to see all messages, or specific history between you and another user.
    VIEW_OTHER_MESSAGES("MESSAGE"), //Not necessary now. Determine the other user, then call view_self_messages from the perspective of that user.
    //New for Phase 2!
    VIEW_SELF_REQUESTS("USER"), //Determine the SpecialRequest to view.
    VIEW_OTHER_REQUESTS("USER"), //Determine the user whose SpecialRequest should be viewed
    VIEW_SELF_STATISTICS("USER"), //Show personal statistics. SUBJECT TO CHANGE!
    VIEW_ALL_STATISTICS("USER") //Show statistics for whole conference. SUBJECT TO CHANGE!

    ;

    private final String category; //Use getCategory


    Permission(String category){
        this.category = category;
    }

    /**
     * Returns the category of this permission.
     * @return category of this permission
     */
    public String getCategory(){
        return this.category;
    }
}
