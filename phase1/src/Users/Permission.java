package Users;

//TODO: Consider case when id does not refer to a valid enum value, using valueOf(id)
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Message
    //Note that message permissions also grant ability to view messages in the scope.
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
    USER_ALL_EDIT_PASSWORD,
    USER_CREATE,
    USER_DELETE
}
