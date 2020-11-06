package Users;

//TODO: Consider case when id does not refer to a valid enum value, using valueOf(id)
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Messages
    MESSAGE_ALL_ATTENDEES,
    MESSAGE_EVENT_ATTENDEES,
    MESSAGE_SINGLE_ATTENDEE,
    //Event
    EVENT_CREATE,
    EVENT_DELETE,
    EVENT_EDIT,
    //User
    USER_ALL_EDIT_PERMISSION,
    USER_SELF_EDIT_PASSWORD,
    USER_OTHER_EDIT_PASSWORD,
    USER_CREATE,
    USER_CREATE_SPEAKER_ACCOUNT,
    USER_DELETE,
    //Viewing
    VIEW_HOSTING_EVENTS,
    VIEW_ATTENDING_EVENTS,
    VIEW_SELF_CHAT_HISTORY,
    VIEW_OTHER_CHAT_HISTORY,

}
