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
        switch(this.menuGoal){
            case CONTINUE:
                exploreNode();
            case BACK:
                goBackOneNode();
            case MAIN:
                goBackMenuNode();
            case LOGOUT:
                System.out.println("logging out!");
                //Note that the above print statement is a placeholder.
                // Essentially.. this node should break out.
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
            case BACK:
                this.menuGoal = MenuGoal.BACK;
            case MAIN:
                this.menuGoal = MenuGoal.MAIN;
            case LOGOUT:
                this.menuGoal = MenuGoal.LOGOUT;
        }
    }

    private void exploreNode(){

        //Dealing with InputStrategy
        // (see representation invariant in MenuNode that inputStrategy must exist for a given node)
//        int nodeIdentifier = 0;
//        if (current.getInputStrategy().equals(InputStrategy.MENU)){
//            nodeIdentifier = prompter.askUserForNextMenuNode(current.getInputStrategy(), current.getChildren());
//            this.inputHistory.put(current, Integer.toString(nodeIdentifier));
//        }else{
//            //In this case, by the representation invariants set in MenuNode, we know that
//            // There must be only one child node.
//            this.inputHistory.put(current, prompter.getStringByStrat(current.getInputStrategy()));
//        }


        //Dealing with taskPermission

        if (current.getTaskPermission() != null){
            //
            actionHolder.get(current.getTaskPermission()).run(inputHistory, username);
        }


        List<MenuNode> nodesByPosition = new ArrayList<MenuNode>(current.getChildren().values());
//        current = nodesByPosition.get(nodeIdentifier);

        traverseMenu();
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
