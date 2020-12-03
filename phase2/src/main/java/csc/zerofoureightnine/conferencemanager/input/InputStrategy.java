package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.input.validators.Validator;

public enum InputStrategy {
    //Not text based
    MENU(""), //Special
    OPTION(""), //Also special

    //SPECIAL

    //Text-based
    VALID_USERNAME("Please enter an existing username"),
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
