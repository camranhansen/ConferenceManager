package Users;

import java.util.Arrays;
import java.util.List;

//TODO: Consider case when id does not refer to a valid enum value, using valueOf(id)
public enum Permission {
    //TEMPLATE for permission: ENTITY_SCOPE_ACTION,
    // where entity refers to the entity being accessed,
    // scope refers to whether the individual can access entities outside of their own (optional)
    // action refers to the specific action granted permission to do.
    //Messages
    MESSAGE_ALL_ATTENDEES("MESSAGE"),
    MESSAGE_EVENT_ATTENDEES("MESSAGE"),
    MESSAGE_SINGLE_ATTENDEE("MESSAGE"),
    //Event
    EVENT_CREATE("EVENT"),
    EVENT_DELETE("EVENT"),
    EVENT_EDIT("EVENT"),
    //User
    USER_ALL_EDIT_PERMISSION("USER"),
    USER_SELF_EDIT_PASSWORD("USER"),
    USER_OTHER_EDIT_PASSWORD("USER"),
    USER_CREATE_ACCOUNT("USER"),
    USER_CREATE_SPEAKER_ACCOUNT("USER"),
    USER_DELETE_ACCOUNT("USER"),
    //Viewing
    VIEW_HOSTING_EVENTS("VIEW"),
    VIEW_ATTENDING_EVENTS("VIEW"),
    VIEW_SELF_CHAT_HISTORY("VIEW"),
    VIEW_OTHER_CHAT_HISTORY("VIEW");

    private final String category; //Use getlabel


    private Permission(String category){
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }
}
