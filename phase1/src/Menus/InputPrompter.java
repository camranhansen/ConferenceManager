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
        if (!userInput.matches("^[0-9]*$") || Integer.parseInt(userInput) >= options.size()) {
            inputPresenter.errorMessage();
            return false;
        }
        return true;
    }

    private void addExitOption(ArrayList<Option> options){
        Option exit = new Option("Exit");
        //TODO add the exit function to this option :)
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
