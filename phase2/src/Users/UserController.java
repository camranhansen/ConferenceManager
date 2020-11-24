package Users;

import Menus.InputPrompter;
import Menus.Option;
import Menus.SubController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserController implements SubController {
    private UserManager um;
    private UserPresenter up;
    private InputPrompter prompter;
    private boolean exiting;

    /**
     * Instantiates the UserController object by inputting a created UserManager.
     * This also instantiates and stores relevant classes
     * @param um a pre-constructed UserManager object
     */
    public UserController(UserManager um){
        this.um = um;
        this.up = new UserPresenter();
        this.prompter = new InputPrompter();
        this.prompter.attach(this);
        this.exiting = false;
    }


    /**
     * For a given user, calls the appropriate method representing an action,
     * based on the Permission passed in
     * @param username the username of the User attempting to perform an action
     * @param permissionSelected a Permission corresponding to an action that the user intends to perform
     */
    public void performSelectedAction(String username, Permission permissionSelected) {
        if (permissionSelected == Permission.USER_CREATE_ACCOUNT){
            this.createAccount(this.selectTemplate());
        }
        else if(permissionSelected == Permission.USER_CREATE_SPEAKER_ACCOUNT){
            this.createAccount(Template.SPEAKER);
        }
        else if (permissionSelected == Permission.USER_DELETE_ACCOUNT){
            this.deleteAccount();
        }
        else if (permissionSelected == Permission.USER_SELF_EDIT_PASSWORD){
            this.editPassword(username);
        }
        else if (permissionSelected == Permission.USER_OTHER_EDIT_PASSWORD){
            this.editOtherPassword();
        }
        else if (permissionSelected == Permission.USER_ALL_EDIT_PERMISSION){
            this.editPermissions();
        }
    }

    //DPE observer!
    public void exitEarly(){
        this.exiting = true;
    }

    /**
     * Prompts the user to select a Template for account creation.
     * @return the Template selected by the user
     */
    public Template selectTemplate(){
        ArrayList<Option> optionList = new ArrayList<>();
        List<Template> templates = Arrays.asList(Template.values());
        for(Template t: templates) {
            optionList.add(new Option(t.toString(), t));
            //DPE DESIGN PATTERN HERE. USING OPTION AS
            // ADAPTOR!!!!
        }

        Option templateSelected = prompter.menuOption(optionList);

        return templateSelected.getTemplateHeld();

    }

    /**
     * Creates a new user account using the input Template to define the list of Permissions,
     * based on the user input.
     * @param template a Template to initially set the Permissions for the user
     */
    public void createAccount(Template template){
        String inputUsername = getNewUsername();
        if (!this.exiting){
            String inputPassword = prompter.getResponse("Enter password");
            if (!this.exiting) {
                this.um.createUser(inputUsername, inputPassword, template.getPermissions());
            }else{
                this.exiting = false;
            }
        } else{
            this.exiting = false;
        }

    }

    /**
     * Removes the username and User object corresponding to the user input.
     */
    public void deleteAccount(){
        String inputUsername = getExistingUsername();
        if (!this.exiting) {
            this.um.removeUser(inputUsername);
        }else{
            this.exiting = false;
        }

    }

    /**
     * Change the password of the User object corresponding to the input username.
     * @param username the username of the User whose password needs to be changed
     */
    public void editPassword(String username){
        String inputPassword = prompter.getResponse("Enter the new password");
        if (!this.exiting) {
            this.um.setPassword(username,inputPassword);
        }else{
            this.exiting = false;
        }
    }

    /**
     * Change the password of another user, based on the user input.
     * Only for select User types!
     */
    public void editOtherPassword(){
        String inputUsername = getExistingUsername();
        if (!this.exiting) {
            this.editPassword(inputUsername);
        }else{
            this.exiting = false;
        }
    }

    public void editPermissions(){
    //TODO: Implement when actually relevant :)
        System.out.println("This method has not been implemented yet, since it is not in the Phase 1 specifications. :)");
    }

    /**
     * Returns a new username as given by user input.
     * @return string corresponding to the input username
     */
    public String getNewUsername(){
        String userName = prompter.getResponse("Enter username");

        while(um.uNameExists(userName)&&!this.exiting){
            userName = prompter.getResponse("The username you entered already exists."+System.lineSeparator()+"Please enter a new username");
        }

        return userName;
    }

    /**
     * Returns an existing username as input by the user.
     * @return string corresponding to the input username
     */
    public String getExistingUsername(){
        String userName = prompter.getResponse("Enter username");
        while(!um.uNameExists(userName)&&!this.exiting){
            userName = prompter.getResponse("The username you entered does not exist."+System.lineSeparator()+"Please enter a new username");
        }
        return userName;
    }

}
