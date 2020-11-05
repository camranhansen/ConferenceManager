package Users;

//TODO: Consider case when id does not refer to a valid enum value, using valueOf(id)
public enum Permission {
    MESSAGE_ALL_ATTENDEES("message_all_attendees"),
    MESSAGE_EVENT_ATTENDEES("message_event_attendees"),
    MESSAGE_SINGLE_ATTENDEE("message_single_attendees"),
    EVENT_CREATE("event_create"),
    EVENT_DELETE("event_delete");

    String identifier;
    Permission(String id){
        identifier = id;
    }
}
