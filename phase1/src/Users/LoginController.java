package Users;

import Menus.InputPrompter;

import java.util.Scanner;

public class LoginController {

    private LoginPresenter screen;
    private InputPrompter prompter;
    private UserManager um;


    public LoginController(UserManager um){
        this.screen = new LoginPresenter();
        this.prompter = new InputPrompter();
        this.um = um;

    }

    // DPE: This is an example of the Dependency Injection because we take
    // in a class as a parameter instead of storing it
    public String loginUser(UserManager um){
        System.out.println("dleet this");
        String username = prompter.getResponse("Please enter your username");
        while(!um.uNameExists(username)){
            username = prompter.getResponse("That username does not exist." +
                    System.lineSeparator() + "Please enter a username that exists, or create an account");
        }
        System.out.println(username);

        String password = prompter.getResponse("Please enter your password");

        //TODO: only return when sure it is valid, else bad things happen...
        return username;
    }

}
