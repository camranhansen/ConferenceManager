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

    public UserController(UserManager um){
        this.um = um;
        this.up = new UserPresenter();
        this.prompter = new InputPrompter();
        this.prompter.attach(this);
        this.exiting = false;
    }


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

    public void deleteAccount(){
        String inputUsername = getExistingUsername();
        if (!this.exiting) {
            this.um.removeUser(inputUsername);
        }else{
            this.exiting = false;
        }

    }

    public void editPassword(String username){
        String inputPassword = prompter.getResponse("Enter the new password");
        if (!this.exiting) {
            this.um.setPassword(username,inputPassword);
        }else{
            this.exiting = false;
        }
    }

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

    public String getNewUsername(){
        String userName = prompter.getResponse("Enter username");

        while(um.uNameExists(userName)&&!this.exiting){
            userName = prompter.getResponse("The username you entered already exists."+System.lineSeparator()+"Please enter a new username");
        }

        return userName;
    }

    public String getExistingUsername(){
        String userName = prompter.getResponse("Enter username");
        while(!um.uNameExists(userName)&&!this.exiting){
            userName = prompter.getResponse("The username you entered does not exist."+System.lineSeparator()+"Please enter a new username");
        }
        return userName;
    }

}
