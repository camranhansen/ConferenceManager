package csc.zerofoureightnine.conferencemanager.menu;


import csc.zerofoureightnine.conferencemanager.input.InputPrompter;
import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.*;

public class MenuNodeTraverser {

    private InputPrompter prompter;
    private String username;
    private List<Permission> userpermissionList;
    private LinkedHashMap<MenuNode, String> nodeInputHistory;
    private MenuNode current;
    private SubControllerDecider controllerDecider;
    private MenuGoal menuGoal;


    public MenuNodeTraverser(InputPrompter prompter,
                             String username,
                             List<Permission> userpermissionList,
                             LinkedHashMap<MenuNode, String> nodeInputHistory,
                             MenuNode current,
                             SubControllerDecider controllerDecider) {
        this.prompter = prompter;
        this.username = username;
        this.userpermissionList = userpermissionList;
        this.nodeInputHistory = nodeInputHistory;
        this.current = current;
        this.controllerDecider = controllerDecider;
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
        if(this.current.getInputStrategy().equals(InputStrategy.CATEGORY_MENU)|
                (this.current.getInputStrategy().equals(InputStrategy.PERMISSION_MENU))){
            //this RELIES on the invariant that
            //anything with inputSTrategy menu MUST give a single number.
            // list indices MUST start at 0
            current = nodesByPosition.get(Integer.parseInt(nodeInputHistory.get(current)));
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


            controllerDecider.DecideSubControllerAndRunMethod(
                    username,
                    current.getTaskPermission(),
                    this.getInputHistory());
        }
    }


    public MenuNode getCurrent(){
        return this.current;
    }

    public void addToInputHistory(String inputToAdd){
        this.nodeInputHistory.put(current, inputToAdd);
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

    public HashMap<InputStrategy, String> getInputHistory(){
        HashMap<InputStrategy, String> inputHistory = new HashMap<>();
        nodeInputHistory.forEach((n, s) -> inputHistory.put(n.getInputStrategy(), s));
        return inputHistory;
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
        while(current.getParent() != null){
            current = current.getParent();
        }
    }

}
