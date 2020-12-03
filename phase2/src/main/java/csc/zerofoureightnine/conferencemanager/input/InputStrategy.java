package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.input.validators.Validator;

public enum InputStrategy {
    //Not text based
    MENU(""), //Special
    OPTION(""), //Also special
    //SPECIAL

    //Text-based
        //Users
    VALID_USERNAME("Enter an existing username"),
    //TODO:Idk about this one...
    VALID_PASSWORD("Enter corresponding password"),
        //Events
    VALID_EVENT_ID("Enter an existing event id"),
    EVENT_DAY("Enter day of the event"),
    EVENT_HOUR("Enter hour of the event"),
    EVENT_CAPACITY("Enter the capacity for this event"),
    EVENT_ROOM("Enter the room this event will be in"),
    EVENT_SPEAKER_SINGLE("Enter speaker name"),
        //Messaging
    MESSAGE_CONTENT("Enter the content for your message")
    ;


    private final String prompt; //Use getCategory

    InputStrategy(String prompt){
        this.prompt = prompt;
    }

    /**
     *
     */
    public String getPrompt(){
        return this.prompt;
    }


}
