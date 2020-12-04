package csc.zerofoureightnine.conferencemanager.menu;


import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.EnumMap;
import java.util.LinkedHashMap;

public class MenuNode {

    // A decision has been made: the MenuNode structure no longer has login.
    // Login is done outside of this node structure
    private MenuNode parent;
    private LinkedHashMap<Permission, MenuNode> children;
    //If a node has two ore more children.. then InputStrategy must be menu.
    //If a node has one or less children.. then InputStrategy must not be menu.
    private InputStrategy inputStrategy;
    //Must exist no matter what when
    //MenuNode is instantiated.
    private String prompt;
    private Permission taskPermission;

    public MenuNode(MenuNode parent,
                    LinkedHashMap<Permission, MenuNode> children,
                    InputStrategy inputStrategy,
                    String prompt,
                    Permission taskPermission) {
        //For this constructor, it is the case that you have to go bottom-up
        //i.e. create terminating nodes first.
        this.parent = parent;
        this.children = children;
        this.inputStrategy = inputStrategy;
        this.prompt = prompt;
        this.taskPermission = taskPermission;
    }

    public MenuNode getParent() {
        return parent;
    }

    public LinkedHashMap<Permission, MenuNode> getChildren() {
        return children;
    }

    public InputStrategy getInputStrategy() {
        return inputStrategy;
    }

    public String getPrompt() {
        return prompt;
    }

    public Permission getTaskPermission() {
        return taskPermission;
    }


}
