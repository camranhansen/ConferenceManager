package Menus;

import Users.Permission;

import java.util.List;

public class inputPrompter {

    public inputPrompter() {
    }


    public Option menuOption(List<Option> options){
        return new Option("This is text");
        //input.isNumeric()
    }


    public String createPrompt(String promptText){
        return "this is user input";

    }


    public boolean wantsToExit(String text){
        return false;
    }

}
