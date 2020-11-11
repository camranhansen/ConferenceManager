package Users;

import Menus.SubController;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserController implements SubController {
    private UserManager um;
    private UserPresenter up;

    public UserController(UserManager um){
        this.um = um;
        this.up = new UserPresenter();
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
        Scanner userInput = new Scanner(System.in);
        List<Template> templates = Arrays.asList(Template.values());
        this.up.promptTemplate(templates);
        String option = userInput.nextLine();
        //TODO: Don't expect just a number. use IsNumeric to parse whether number input or text
        return templates.get(Integer.parseInt(option));

    }

    public void createAccount(Template template){
        Scanner userInput = new Scanner(System.in);
        this.up.promptUsername();
        String inputUsername = userInput.nextLine();
        this.up.promptPassword();
        String inputPassword = userInput.nextLine();
        this.um.createUser(inputUsername, inputPassword, template.getPermissions());

    }

    public void deleteAccount(){
        Scanner userInput = new Scanner(System.in);
        this.up.promptUsername();
        String inputUsername = userInput.nextLine();
        this.um.removeUser(inputUsername);

    }

    public void editPassword(String username){
        Scanner userInput = new Scanner(System.in);
        this.up.promptPassword();
        String inputPassword = userInput.nextLine();
        this.um.setPassword(username,inputPassword);
    }

    public void editOtherPassword(){
        Scanner userInput = new Scanner(System.in);
        this.up.promptUsername();
        String inputUsername = userInput.nextLine();
        this.editPassword(inputUsername);
    }

    public void editPermissions(){
    //TODO: Implement when actually relevant :)
    }


}
