package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.HashMap;
import java.util.List;

public class UserController {

    private UserManager um;
    private PermissionManager pm;
    private HashMap<String, String> inputMap = new HashMap<>();

    /**
     * A constructor takes in userManager and PermissionManager
     * @param um UserManager
     * @param pm PermissionManager
     */
    public UserController(UserManager um, PermissionManager pm) {
        this.um = um;
        this.pm = pm;
    }

    /**
     * check if the input name is a existed username
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public boolean isValidUser(String input, List<TopicPresentable> options){
        return um.userExists(input);
    }

    /**
     * check if the input name is not a existed username
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public boolean isNotValidUser(String input, List<TopicPresentable> options){
        return !um.userExists(input);
    }

    /**
     * Calls {@link UserManager#setPassword(String, String)} to change password
     * @param username user who is using the program.
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int editPassword(String username, String input, List<TopicPresentable> options) {
        um.setPassword(username, inputMap.get("password"));
        return 0;
    }

    /**
     * Calls {@link UserManager#setPassword(String, String)} to change password (administrator only)
     * @param username user who is using the program.
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int editOtherPassword(String username,String input, List<TopicPresentable> options){
        String name = inputMap.get("target");
        um.setPassword(name, inputMap.get("password"));
        return 0;
    }

    /**
     * Calls {@link UserManager#createUser(String, String, List)} to create a new account
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int createAccount(String input, List<TopicPresentable> options){
        um.createUser(inputMap.get("name"), inputMap.get("password"),Template.values()[Integer.parseInt(inputMap.get("template"))].getPermissions() );
        return 0;
    }

    /**
     * Calls {@link UserManager#createUser(String, String, List)} to create a new speaker account
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int createSpkAccount(String input, List<TopicPresentable> options){
        um.createUser(inputMap.get("name"), inputMap.get("password"), Template.SPEAKER.getPermissions());
        return 0;
    }

    /**
     * Calls {@link UserManager#removeUser(String)} to remove the user's
     * account specified in inputMap, with tag "name".
     * @param input Input specifically in to this node. In this case, it is not relevant.
     * @param options Options available at this node. In this case, it is not relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int deleteAccount(String input, List<TopicPresentable> options){
        um.removeUser(inputMap.get("name"));
        return 0;
    }


    public HashMap<String, String> getInputMap() {
        return inputMap;
    }
}
