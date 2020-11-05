package Users;

//TODO: Consider case when id does not refer to a valid enum value, using valueOf(id)
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Message
    //Note that message permissions also grant ability to view messages in the scope.
    MESSAGE_ALL_ATTENDEES("message_all_attendees"),
    MESSAGE_EVENT_ATTENDEES("message_event_attendees"),
    MESSAGE_SINGLE_ATTENDEE("message_single_attendees"),
    //Event
    EVENT_CREATE("event_create"),
    EVENT_DELETE("event_delete"),
    EVENT_EDIT("event_edit"),
    //User
    USER_ALL_EDIT_PERMISSION("user_change_permission"),
    USER_SELF_EDIT_PASSWORD("user_self_edit_password"),
    USER_ALL_EDIT_PASSWORD("user_all_edit_password"),
    USER_CREATE("user_create"),
    USER_DELETE("user_delete");
    //


    String identifier;
    Permission(String id){
        identifier = id;
    }
}
