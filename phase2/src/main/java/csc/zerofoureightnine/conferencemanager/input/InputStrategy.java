package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.List;

public enum InputStrategy {
    //Not text based
    CATEGORY_MENU("Enter corresponding number", true, "Invalid selection. Please try again"), //This means that the only job of this node is to do a menu choice.
    PERMISSION_MENU("Enter corresponding number", true, "Invalid selection. Please try again"), //TODO make prompts for these
    OPTION("Enter corresponding number", true, "Invalid selection. Please try again"), //Also special

    //Text-based
        //Users related
    VALID_USERNAME("Enter an existing username", false, "Invalid username. Please try again"),
        //Events related
    VALID_EVENT_ID("Enter an existing event id", false, "Invalid event id. Please try again"),
    EVENT_DAY("Enter day of the event", false, "Invalid day. Please try again"),
    EVENT_HOUR("Enter hour of the event", false, "Invalid hour. Please try again"),
    EVENT_CAPACITY("Enter the capacity for this event", false,
            "Invalid capacity. Please try again"),
    EVENT_ROOM("Enter the room this event will be in", false, "Invalid room. Please try again"),
    EVENT_SPEAKER_SINGLE("Enter speaker name", false, "Invalid speaker. Please try again"),
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
