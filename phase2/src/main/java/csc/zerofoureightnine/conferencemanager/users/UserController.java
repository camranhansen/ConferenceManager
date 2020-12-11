package csc.zerofoureightnine.conferencemanager.users;

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
     * @param input String that represents username
     * @param options
     * @return true if the username existed otherwise false
     */
    public boolean isValidUser(String input, List<TopicPresentable> options){
        return um.userExists(input);
    }

    /**
     * check if the input name is not a existed username
     * @param input String that represents username
     * @param options
     * @return false if the username does not existed otherwise false
     */
    public boolean isNotValidUser(String input, List<TopicPresentable> options){
        return !um.userExists(input);
    }

    /**
     * Allow user to change password
     * @param username user who is using the program.
     * @param input
     * @param opts
     * @return
     */
    public int editPassword(String username, String input, List<TopicPresentable> opts) {
        um.setPassword(username, inputMap.get("password"));
        return 0;
    }

    /**
     * Allow administration only to change other users' password
     * @param username user who is using the program.
     * @param input
     * @param opts
     * @return 0
     */
    public int editOtherPassword(String username,String input, List<TopicPresentable> opts){
        String name = inputMap.get("target");
        um.setPassword(name, inputMap.get("password"));
        return 0;
    }

    /**
     * Used to create new account
     * @param input
     * @param options
     * @return
     */
    public int createAccount(String input, List<TopicPresentable> options){
        um.createUser(inputMap.get("name"), inputMap.get("password"),Template.values()[Integer.parseInt(inputMap.get("template"))].getPermissions() );
        return 0;
    }

    /**
     * Used to create a new account that has speaker's permission
     * @param input
     * @param options
     * @return
     */
    public int createSpkAccount(String input, List<TopicPresentable> options){
        um.createUser(inputMap.get("name"), inputMap.get("password"), Template.SPEAKER.getPermissions());
        return 0;
    }

    /**
     * Used to delete account
     * @param input
     * @param options
     * @return
     */
    public int deleteAccount(String input, List<TopicPresentable> options){
        um.removeUser(inputMap.get("name"));
        return 0;
    }


    public HashMap<String, String> getInputMap() {
        return inputMap;
    }
}
