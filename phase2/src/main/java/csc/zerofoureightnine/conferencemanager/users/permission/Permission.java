package csc.zerofoureightnine.conferencemanager.users.permission;

/**
 * Permissions available to the user. See template for specific permissions for user types.
 */
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Messages
    MESSAGE_ALL_USERS(Category.MESSAGE), //Determine the group of csc.zerofoureightnine.conferencemanager.users you want to message(i.e. all csc.zerofoureightnine.conferencemanager.users, specific permission group)
    MESSAGE_EVENT_USERS(Category.MESSAGE), //Determine whether the logged in user wants to message a specific event that they are hosting, or all csc.zerofoureightnine.conferencemanager.events that they are hosting.
    MESSAGE_SINGLE_USER(Category.MESSAGE), //Determine the recipient user.
    //New for Phase 2!
    MESSAGE_ARCHIVE(Category.MESSAGE), //Determine the message to archive.
    MESSAGE_DELETE(Category.MESSAGE), //Determine the message to delete.
    MESSAGE_UNREAD(Category.MESSAGE), //Determine the message to unread.

    //Event
    EVENT_SELF_ENROLL(Category.EVENT), //Determine whether want to enroll or unenroll. Determine the specific event to either enroll or unenroll.
    EVENT_OTHER_ENROLL(Category.EVENT), //Likewise, except also determine the username of the other person in question.
    EVENT_CREATE(Category.EVENT), //Takes in event parameters.
    EVENT_DELETE(Category.EVENT), //Delete event by ID.
    EVENT_EDIT(Category.EVENT), //Determine event ID, then determine what parameter you want to edit.

    //User
    USER_ALL_EDIT_PERMISSION(Category.USER),
    USER_SELF_EDIT_PASSWORD(Category.USER),
    USER_OTHER_EDIT_PASSWORD(Category.USER),
    USER_CREATE_ACCOUNT(Category.USER),
    USER_CREATE_SPEAKER_ACCOUNT(Category.USER), //THIS SHOULD NOW EXTENDED TO ALL ACCOUNT TYPES NOT JUST SPEAKERS... perhaps
    USER_DELETE_ACCOUNT(Category.USER), //Delete other people's account, or yourself.
    //New for Phase 2!
    USER_CREATE_REQUEST(Category.SPECIAL_REQUEST), //Create a SpecialRequest.
    USER_SELF_EDIT_REQUEST(Category.SPECIAL_REQUEST), //Determine the SpecialRequest, and the type of edit to perform.
    USER_OTHER_EDIT_REQUEST(Category.SPECIAL_REQUEST), //Determine the user whose SpecialRequest should be edited, and edit it.


    //Viewing
    VIEW_HOSTING_EVENTS(Category.EVENT), //View the csc.zerofoureightnine.conferencemanager.events you are speaking at (i.e. where speakerName = your name)
    VIEW_ALL_EVENTS(Category.EVENT), //Anyone can do this.
    // Determine whether the user wants to see:
    //TODO make this permission explicit. I.e. VIEW_ATTENDING_EVENTS, VIEW_AVAILABLE_EVENTS, VIEW_ALL_EVENTS

    // 1. All the csc.zerofoureightnine.conferencemanager.events they are attending.
    // 2. All available csc.zerofoureightnine.conferencemanager.events to them specifically.
    // 3. All csc.zerofoureightnine.conferencemanager.events regardless of availability.
    VIEW_SELF_MESSAGES(Category.MESSAGE), //Determine whether you want to see all messages, or specific history between you and another user.
    VIEW_OTHER_MESSAGES(Category.MESSAGE), //Not necessary now. Determine the other user, then call view_self_messages from the perspective of that user.
    //New for Phase 2!
    VIEW_SELF_REQUESTS(Category.SPECIAL_REQUEST), //Determine the SpecialRequest to view.
    VIEW_OTHER_REQUESTS(Category.SPECIAL_REQUEST), //Determine the user whose SpecialRequest should be viewed
    VIEW_SELF_STATISTICS(Category.DATA), //Show personal statistics. SUBJECT TO CHANGE!
    VIEW_ALL_STATISTICS(Category.DATA) //Show statistics for whole conference. SUBJECT TO CHANGE!

    ;

    private final Category category; //Use getCategory


    Permission(Category category){
        this.category = category;
    }

    /**
     * Returns the category of this permission.
     * @return category of this permission
     */
    public Category getCategory(){
        return this.category;
    }
}
