package csc.zerofoureightnine.conferencemanager.input;

public enum InputStrategy {
    //Not text based
    MENU("", true), //Special
    OPTION("", true), //Also special
    //SPECIAL

    //Text-based
        //Users related
    VALID_USERNAME("Enter an existing username", false),
        //Events related
    VALID_EVENT_ID("Enter an existing event id", false),
    EVENT_DAY("Enter day of the event", false),
    EVENT_HOUR("Enter hour of the event", false),
    EVENT_CAPACITY("Enter the capacity for this event", false),
    EVENT_ROOM("Enter the room this event will be in", false),
    EVENT_SPEAKER_SINGLE("Enter speaker name", false),
        //Messaging related
    MESSAGE_CONTENT("Enter the content for your message", false)
    ;

    private final String prompt; //Use getCategory
    private final boolean isMenu;
    InputStrategy(String prompt, boolean isMenu){
        this.prompt = prompt;
        this.isMenu = isMenu;
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
}
