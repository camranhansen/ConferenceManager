package csc.zerofoureightnine.conferencemanager.menu;


import csc.zerofoureightnine.conferencemanager.input.InputPrompter;
import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MenuNodeTraverser {

    private InputPrompter prompter;
    private String username;
    private List<Permission> userpermissionList;
    private LinkedHashMap<MenuNode, String> inputHistory;
    private MenuNode current;
    private HashMap<Permission, MenuAction> actionHolder;
    private MenuGoal menuGoal;


    public MenuNodeTraverser(InputPrompter prompter,
                             String username,
                             List<Permission> userpermissionList,
                             LinkedHashMap<MenuNode, String> inputHistory,
                             MenuNode current,
                             HashMap<Permission, MenuAction> actionHolder) {
        this.prompter = prompter;
        this.username = username;
        this.userpermissionList = userpermissionList;
        this.inputHistory = inputHistory;
        this.current = current;
        this.actionHolder = actionHolder;
        this.menuGoal = MenuGoal.CONTINUE;
        //attach the inputPrompter to this.
        //And then MenuGoal
    }

    public void traverseMenu(){
        doNodeInput();
        switch(this.menuGoal){
            case CONTINUE:
                doNodeAction();
                goToNextNode();
                traverseMenu();
                break;
            case BACK:
                goBackOneNode();
                traverseMenu();
                break;
            case MAIN:
                goBackMenuNode();
                traverseMenu();
                break;
            case LOGOUT:
                System.out.println("logging out!");
                break;
                //Note that the above print statement is a placeholder.
        }

    }


    private void goToNextNode(){
        //go to designated child node depending on input history.
        List<MenuNode> nodesByPosition = new ArrayList<>(current.getChildren().values());
        if(this.current.getInputStrategy().equals(InputStrategy.MENU)){
            //this RELIES on the invariant that
            //anything with inputSTrategy menu MUST give a single number.
            // list indices MUST start at 0
            current = nodesByPosition.get(Integer.parseInt(inputHistory.get(current)));
        }else{
            current = nodesByPosition.get(0); //

        }
    }


    private void doNodeInput(){

        this.prompter.addValidResponseToInputHistory();

        //Dealing with taskPermission

        traverseMenu();
    }

    private void doNodeAction() {
        if (current.getTaskPermission() != null){
            //
            actionHolder.get(current.getTaskPermission()).run(inputHistory, username);
        }
    }


    public MenuNode getCurrent(){
        return this.current;
    }

    public void addToInputHistory(String inputToAdd){
        this.inputHistory.put(current, inputToAdd);
    }

    //TEMPORARY SET MENU GOAL
    public void setMenuGoal(MenuGoal menuGoal){
        switch(menuGoal){
            case CONTINUE:
                this.menuGoal = MenuGoal.CONTINUE;
                break;
            case BACK:
                this.menuGoal = MenuGoal.BACK;
                break;
            case MAIN:
                this.menuGoal = MenuGoal.MAIN;
                break;
            case LOGOUT:
                this.menuGoal = MenuGoal.LOGOUT;
                break;
        }
    }

    /**
     * Go back to node's parent
     */
    private void goBackOneNode(){

        this.current = (this.current.getParent());

    }

    /**
     * Go to menu node (by definition - top node)
     */
    private void goBackMenuNode(){
        //go to to top node.
    }

}
