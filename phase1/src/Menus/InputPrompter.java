package Menus;

import java.util.ArrayList;
import java.util.Scanner;

public class InputPrompter {
    private Scanner scanner;
    private InputPresenter inputPresenter;

    /**
     * Creates a new InputPrompter.
     */
    public InputPrompter() {
        this.scanner = new Scanner(System.in);
        this.inputPresenter = new InputPresenter();
    }

    /**
     * Shows the user the options options and returns the user's
     * selected option out of the options options.
     *
     * @param options Options to select from.
     * @return The option the user has selected.
     */
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
        options.add(0, exit);
    }

    private void exit(){
        //TODO: back to main menu
    }

    /**
     * Returns the user's response to a prompt prompt.
     *
     * @param prompt Prompt in need of a user response.
     * @return The user's response.
     */
    public String getResponse(String prompt){
        inputPresenter.printPrompt(prompt);

        String in = scanner.nextLine();
        //Check if input is "exit"
        //Do something here
        //like call some method....
        return in;
    }
}
