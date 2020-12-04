package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.menu.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class InputPrompter {
    private Scanner scanner;
    private InputPresenter inputPresenter;
    private ArrayList<SubController> subControllers;
    private InputStrategyManager inputStrategyManager;

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

    public String getValidResponse(MenuNode menuNode, LinkedHashMap<MenuNode, String> inputHistory,
                                   MenuNodeTraverser menuNodeTraverser){
        inputPresenter.printPrompt(menuNode.getInputStrategy().getPrompt());
        String input = scanner.nextLine();
        if (menuGoalCheck(menuNodeTraverser, input)){
            return "";
        }
        while(!inputStrategyManager.validate(menuNode.getInputStrategy(), input)){
            inputPresenter.invalidResponse();
            inputPresenter.printPrompt(menuNode.getInputStrategy().getPrompt());
            input = scanner.nextLine();
        }
        inputHistory.put(menuNode, input);
        return input;
    }

    private boolean menuGoalCheck(MenuNodeTraverser menuNodeTraverser, String userInput){
        switch (userInput.trim().toLowerCase()) {
            case "back":
                menuNodeTraverser.setMenuGoal(MenuGoal.BACK);
                return true;
            case "main":
                menuNodeTraverser.setMenuGoal(MenuGoal.MAIN);
                return true;
            case "exit":
                menuNodeTraverser.setMenuGoal(MenuGoal.LOGOUT);
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
        //TODO: Change to list not arrayList
        inputPresenter.printOptions(options);
        int userInput = userOptionInput(options);
        return options.get(userInput);
    }

    //NEW OPTION INPUT CODE. IF THIS WORKS TODO: DELETE OLD INPUT OPTION CODE
    public Option selectMenuOption(List<Option> options, MenuNode menuNode, LinkedHashMap<MenuNode,
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

    private String usersInputOption(MenuNodeTraverser menuNodeTraverser){
        String userInput =  scanner.nextLine();
        String input = userInput.trim();
        if (menuGoalCheck(menuNodeTraverser, input)){
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

    /**
     * Returns the user's response to a prompt prompt.
     *
     * @param prompt Prompt in need of a user response.
     * @return The user's response.
     */
    public String getResponse(String prompt){
        inputPresenter.printPrompt(prompt);
        return scanner.nextLine();
    }

    //TODO: Possible remove attach here and in subControllers
    public void attach(SubController attached){
        this.subControllers.add(attached);
    }
}
