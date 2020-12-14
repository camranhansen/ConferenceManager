package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActions {
    private String loggedInUser;
    private UserManager um;
    private PermissionManager pm;
    private Map<String, String> inputMap = new HashMap<>();

    /**
     * A constructor takes in userManager and PermissionManager
     *
     * @param um UserManager
     * @param pm PermissionManager
     */
    public UserActions(UserManager um, PermissionManager pm) {
        this.um = um;
        this.pm = pm;
    }


    /**
     * Calls {@link UserManager#setPassword(String, String)} to change password
     * 
     * @param username user who is using the program.
     * @param input    Input specifically in to this node. In this case, it is not
     *                 relevant.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int editPassword(String username, String input, List<TopicPresentable> options) {
        um.setPassword(username, inputMap.get("password"));
        return 0;
    }

    /**
     * Calls {@link UserManager#setPassword(String, String)} to change password
     * (administrator only)
     * 
     * @param username user who is using the program.
     * @param input    Input specifically in to this node. In this case, it is not
     *                 relevant.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int editOtherPassword(String username, String input, List<TopicPresentable> options) {
        String name = inputMap.get("username");
        um.setPassword(name, inputMap.get("password"));
        return 1;
    }

    /**
     * Calls {@link UserManager#createUser(String, String, List)} to create a new
     * account
     * 
     * @param username user who is using the program.
     * @param input    Input specifically in to this node. In this case, it is not
     *                 relevant.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int createAccount(String username, String input, List<TopicPresentable> options) {
        um.createUser(inputMap.get("username"), inputMap.get("password"), Template
                .valueOf(inputMap.get("template_value")).getPermissions());
        return 1;
    }

    /**
     * Calls {@link UserManager#createUser(String, String, List)} to create a new
     * speaker account
     * 
     * @param username user who is using the program.
     * @param input    Input specifically in to this node. In this case, it is not
     *                 relevant.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int createSpkAccount(String username, String input, List<TopicPresentable> options) {
        um.createUser(inputMap.get("username"), inputMap.get("password"), Template.SPEAKER.getPermissions());
        return 1;
    }

    /**
     * Calls {@link UserManager#removeUser(String)} to remove the user's account
     * specified in inputMap, with tag "name".
     * 
     * @param username user who is using the program.
     * @param input    Input specifically in to this node. In this case, it is not
     *                 relevant.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int deleteCurrentAccount(String username, String input, List<TopicPresentable> options) {
        um.removeUser(username);
        return 0;
    }

        /**
     * Calls {@link UserManager#removeUser(String)} to remove the user's account
     * specified by the user input.
     * 
     * @param username user who is using the program.
     * @param input    Input specifically in to this node.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int deleteOtherAccount(String username, String input, List<TopicPresentable> options) {
        um.removeUser(input);
        return 1;
    }


    /**
     * Adds a permission for a given user.
     *
     * @param username user whom the permission is being applied to.
     * @param input    Input specifically in to this node. In this case, it is not
     *                 relevant.
     * @param options  Options available at this node. In this case, it is not
     *                 relevant.
     * @return the node to return to. See {@link MenuNode} for clarification
     */
    public int addPermission(String username, String input, List<TopicPresentable> options) {
        pm.addPermission(username, Permission.valueOf(inputMap.get("permission")));
        return 0;
    }

    /**
     *
     * @return the inputMap.
     */
    public Map<String, String> getInputMap() {
        return inputMap;
    }

}
