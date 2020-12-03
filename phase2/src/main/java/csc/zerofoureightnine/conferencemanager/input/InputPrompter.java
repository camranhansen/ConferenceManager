package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.menu.Option;
import csc.zerofoureightnine.conferencemanager.menu.SubController;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class InputPrompter {
    private Scanner scanner;
    private InputPresenter inputPresenter;
    private ArrayList<SubController> subControllers;

    /**
     * Creates a new InputPrompter.
     */
    public InputPrompter() {
        this.scanner = new Scanner(System.in);
        this.inputPresenter = new InputPresenter();
        this.subControllers = new ArrayList<>();
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
        while (! optionInputCheck(options, input)) {
            input = String.valueOf(userInput(options));
        }
        return Integer.parseInt(input);
    }

    private boolean optionInputCheck(ArrayList<Option> options, String userInput){
        if (!userInput.matches("^[0-9]+$") || Integer.parseInt(userInput) >= options.size()) {
            inputPresenter.errorMessageForOptions();
            return false;
        }
        return true;
    }

    private void addExitOption(ArrayList<Option> options){
        Option exit = new Option("EXIT");
        options.add(0, exit);
    }

    private void exit(){
        //TODO: back to main csc.zerofoureightnine.conferencemanager.menu
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
        if (in.equals("exit")||in.equals("EXIT")||in.equals("Exit")){
            exitOut();
            return null;
        }
        return in;
    }

    public void attach(SubController attached){
        this.subControllers.add(attached);
    }

    public void exitOut(){
        for(SubController sub: this.subControllers){
            sub.exitEarly();
        }
    }

    public String loginIn(UserManager userManager){
        String username = enterValidUsername(userManager);
        passwordCheck(username, userManager);
        return username;
    }

    public String enterValidUsername(UserManager userManager){
        String username = getResponse("Please enter a username");
        while(!userManager.userExists(username)){
            doesNotExist("This username ");
            username = getResponse("Please enter a username");
        }
        return username;
    }

    private void passwordCheck(String username, UserManager userManager){
        String password = getResponse("Please enter your password");
        while(!userManager.getPassword(username).equals(password)){
            inputPresenter.incorrectPassword();
            password = getResponse("Please enter your password");
        }
    }

    public void checkPermission(){} //?

    public void doesNotExist(String invalid){
        inputPresenter.doesNotExist(invalid);
    }

    //TODO implement specific prompter methods... e.g. getExistingUsername, getExistingUsername(Template t), getValidTimeslot, getValidRoom.... etc.

}