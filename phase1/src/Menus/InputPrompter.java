package Menus;

import Users.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputPrompter {
    private Scanner scanner;
    private InputPresenter inputPresenter;

    public InputPrompter() {
        this.scanner = new Scanner(System.in);
        this.inputPresenter = new InputPresenter();
    }

    public Option menuOption(ArrayList<Option> options){
        addExitOption(options);
        inputPresenter.printOptions(options);
        int userInput = userInput(options);
        if (userInput == 0){
            exit();
        }
        return options.get(userInput);
    }

    private int userInput(ArrayList<Option> options){
        String userInput =  scanner.nextLine();
        String input = userInput.trim();
        while (! inputCheck(options, input)) {
            input = String.valueOf(userInput(options));
        }
        return Integer.parseInt(input);
    }

    private boolean inputCheck(ArrayList<Option> options, String userInput){
        //TODO: fix this--double digit case
        //i don't know how regex works but i think this is right... it works anyway
        if (!userInput.matches("^[0-" + (options.size() -  1) + "]$")){
            inputPresenter.errorMessage();
            return false;
        }
        return true;
    }

    private void addExitOption(ArrayList<Option> options){
        Option exit = new Option("exit");
        options.add(0, exit);
    }

    private void exit(){
        //TODO: back to main menu
    }


    public String getResponse(String prompt){
        inputPresenter.printPrompt(prompt);
        return scanner.nextLine();
    }
}
