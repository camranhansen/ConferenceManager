package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.options.Option;

import java.util.List;

public enum InputStrategy {

    //Menus
    CATEGORY_MENU("Enter corresponding number", true, "Invalid selection. Please try again"), //This means that the only job of this node is to do a menu choice.
    PERMISSION_MENU("Enter corresponding number", true, "Invalid selection. Please try again"),
        //Events related
    ENROLL_OPTIONS("Enter corresponding number", true, "Invalid selection. Please try again"),
    VIEWING_EVENT_OPTIONS("Enter corresponding number", true,
            "Invalid selection. Please try again"),
        //Messaging related
    VIEWING_MESSAGE_OPTIONS("Enter corresponding number", true, "Invalid selection. Please try again"),
    SEND_ALL_OPTIONS("Enter corresponding number", true, "Invalid selection. Please try again"),
    MESSAGE_EVENT_OPTIONS("Enter corresponding number", true, "Invalid selection. Please try again"),
    //Options that return text...
        //Users related
    USER_TEMPLATE_OPTIONS("Enter corresponding number", true,
            "Invalid selection. Please try again"),


    //Text-based
        //Users related
    VALID_USERNAME("Enter an existing username", false, "Invalid username. Please try again"),
        //Events related
    VALID_EVENT_ID("Enter an existing event id", false, "Invalid event id. Please try again"),
    EVENT_DAY("Enter day of the event", false, "Invalid day. Please try again"),
    EVENT_HOUR("Enter hour of the event", false, "Invalid hour. Please try again"),
    EVENT_CAPACITY("Enter the capacity for this event", false,
            "Invalid capacity. Please try again"),
    EVENT_ROOM("Enter the room this event will be in", false,
            "Room is unavailable. Please try again"),
    EVENT_SPEAKER_SINGLE("Enter speaker names, comma separated, or enter 'party' for no speakers",
            false, "Invalid speaker. Please try again"),
        //Messaging related
    MESSAGE_CONTENT("Enter the content for your message", false,
                "Message exceeds character limit. Please try again")
    ;

    private final String prompt; //Use getCategory
    private final boolean isMenu;
    private final String errorMessage;

    InputStrategy(String prompt, boolean isMenu, String errorMessage){
        this.prompt = prompt;
        this.isMenu = isMenu;
        this.errorMessage = errorMessage;
    }

    /**
     *
     */
    public String getPrompt(){
        return this.prompt;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public String getErrorMessage(){return this.errorMessage;}
}
