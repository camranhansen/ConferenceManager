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

    public UserController(UserManager um){
        this.um = um;
        this.up = new UserPresenter();
        this.prompter = new InputPrompter();
    }

    @Override
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

    public Template selectTemplate(){
        ArrayList<Option> optionList = new ArrayList<>();
        List<Template> templates = Arrays.asList(Template.values());
        for(Template t: templates){
            optionList.add(new Option(t.toString(),t));
            //DPE DESIGN PATTERN HERE. USING OPTION AS
            // ADAPTOR!!!!
        }

        Option templateSelected = prompter.menuOption(optionList);

        return templateSelected.getTemplateHeld();

    }

    public void createAccount(Template template){
        String inputUsername = getNewUsername();
        String inputPassword = prompter.getResponse("Enter password");
        //TODO determine whether we need to validate passwords
        this.um.createUser(inputUsername, inputPassword, template.getPermissions());
    }

    public void deleteAccount(){
        String inputUsername = getExistingUsername();
        this.um.removeUser(inputUsername);

    }

    public void editPassword(String username){
        String inputPassword = prompter.getResponse("Enter the new password");
        this.um.setPassword(username,inputPassword);
    }

    public void editOtherPassword(){
        String inputUsername = getExistingUsername();
        this.editPassword(inputUsername);
    }

    public void editPermissions(){
    //TODO: Implement when actually relevant :)
    }

    public String getNewUsername(){
        String userName = prompter.getResponse("Enter username");

        while(um.uNameExists(userName)){
            userName = prompter.getResponse("The username you entered already exists."+System.lineSeparator()+"Please enter a new username");
        }

        return userName;
    }

    public String getExistingUsername(){
        String userName = prompter.getResponse("Enter username");
        while(!um.uNameExists(userName)){
            userName = prompter.getResponse("The username you entered does not exist."+System.lineSeparator()+"Please enter a new username");
        }
        return userName;
    }

}
