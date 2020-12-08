package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.menu.*;
import csc.zerofoureightnine.conferencemanager.options.Option;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputPrompter implements Inputable{
    private Scanner scanner;
    private InputPresenter inputPresenter;
    private ArrayList<SubController> subControllers;//TODO remove this.
    private InputStrategyManager inputStrategyManager;
    private MenuNodeTraverser currentTraverser;

    /**
     * Creates a new InputPrompter.
     */
    public InputPrompter() {
        this.scanner = new Scanner(System.in);
        this.inputPresenter = new InputPresenter();
        this.subControllers = new ArrayList<>();
    }

    //NEW CODE FOR INPUT WITH STRATEGY IN PLACE:
    public InputPrompter(InputStrategyManager inputStrategyManager) {
        this.scanner = new Scanner(System.in);
        this.inputPresenter = new InputPresenter();
        this.subControllers = new ArrayList<>();
        this.inputStrategyManager = inputStrategyManager;
    }

    public void attachCurrentTraverser(MenuNodeTraverser traverser){
        this.currentTraverser = traverser;

    }

    public String getValidResponse(InputStrategy inputStrategy) {
        inputPresenter.presentPrompt(inputStrategy.getPrompt());
        String input = scanner.nextLine();
        if (inputIsReservedKeyword(input)) {
            return "";
        } else {
            while (!inputStrategyManager.validate(inputStrategy, input)) {
                inputPresenter.invalidResponse(inputStrategy.getErrorMessage());
                //put run time stats here....
                inputPresenter.presentPrompt(inputStrategy.getPrompt());
                input = scanner.nextLine();
            }
        }
        return input;
    }

    public String getValidMenuResponse(InputStrategy inputStrategy){
        inputPresenter.presentPrompt(inputStrategy.getPrompt());
        inputPresenter.printOptions(inputStrategyManager.getOptions(inputStrategy));
        String input = scanner.nextLine();
        if (inputIsReservedKeyword(input)) {
            return "";
        } else {
            while (!inputStrategyManager.validate(inputStrategy, input)) {
                inputPresenter.invalidResponse(inputStrategy.getErrorMessage());
                inputPresenter.presentPrompt(inputStrategy.getPrompt());
                inputPresenter.printOptions(inputStrategyManager.getOptions(inputStrategy));
                input = scanner.nextLine();
            }
        }
        return input;

    }
    public void addValidResponseToInputHistory(){
        InputStrategy strategy = currentTraverser.getCurrent().getInputStrategy();
        String validInput = "";
        if (strategy.isMenu()){
            validInput = getValidMenuResponse(strategy);
        }
        else{
            validInput = getValidResponse(strategy);}
        currentTraverser.addToInputHistory(validInput);
    }

    private boolean inputIsReservedKeyword(String userInput){
        //TODO this later
        switch (userInput.trim().toLowerCase()) {
            case "back":
                this.currentTraverser.setMenuGoal(MenuGoal.BACK);//todo change this to..
                //setMenuGoal(MenuGoal.BACK). i.e. have a helper function here
                return true;
            case "main":
                this.currentTraverser.setMenuGoal(MenuGoal.MAIN);
                return true;
            case "logout":
                this.currentTraverser.setMenuGoal(MenuGoal.LOGOUT);
                return true;
        }
        return false;
    }

    /**
     * Shows the user the options options and returns the user's
     * selected option out of the options options.
     *
     * @param options Options to select from.
     * @return The option the user has selected.
     */
    public Option menuOption(List<Option> options){
        inputPresenter.printOptions(options);
        int userInput = userOptionInput(options);
        return options.get(userInput);
    }

    //NEW OPTION INPUT CODE. IF THIS WORKS TODO: DELETE OLD INPUT OPTION CODE
    public Option selectMenuOption(List<Option> options, MenuNode menuNode, Map<MenuNode,
            String> inputHistory, MenuNodeTraverser menuNodeTraverser){
        inputPresenter.printOptions(options);
        String optionSelected = usersInputOption(menuNodeTraverser);
        if(optionSelected.isEmpty()){
            return new Option("");
        }
        if(invalidOption(options, optionSelected)){
            optionSelected = usersInputOption(menuNodeTraverser);
        }
        inputHistory.put(menuNode, optionSelected);
        return options.get(Integer.parseInt(optionSelected));
    }
    //TODO change this as well to match current conception.
    private String usersInputOption(MenuNodeTraverser menuNodeTraverser){
        String userInput =  scanner.nextLine();
        String input = userInput.trim();
        if (inputIsReservedKeyword(input)){
            return "";
        }
        return input;
    }

    //OLD OPTION INPUT CODE:
    private int userOptionInput(List<Option> options){
        String userInput =  scanner.nextLine();
        String input = userInput.trim();
        //TODO: Reserved input check
        while (invalidOption(options, input)) {
            input = String.valueOf(userOptionInput(options));
        }
        return Integer.parseInt(input);
    }

    //returns true if invalid selection.
    private boolean invalidOption(List<Option> options, String userInput){
        if (!userInput.matches("^[0-9]+$") || Integer.parseInt(userInput) >= options.size()) {
            inputPresenter.errorMessageForOptions();
            return true;
        }
        return false;
    }

    //TODO: Remove, if we are just using the validation one
    /**
     * Returns the user's response to a prompt prompt.
     *
     * @param prompt Prompt in need of a user response.
     * @return The user's response.
     */
    public String getResponse(String prompt){
        inputPresenter.presentPrompt(prompt);
        return scanner.nextLine();
    }

    //TODO: Possible remove attach here and in subControllers
    public void attach(SubController attached){
        this.subControllers.add(attached);
    }


}
