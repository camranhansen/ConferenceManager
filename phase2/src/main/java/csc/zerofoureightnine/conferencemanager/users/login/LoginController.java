package csc.zerofoureightnine.conferencemanager.users.login;

import csc.zerofoureightnine.conferencemanager.input.InputPrompter;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class LoginController {

    private LoginPresenter screen;
    private InputPrompter prompter;
    private UserManager um;


    /**
     * @param um the UserManager.
     */
    public LoginController(UserManager um){
        // DPE: This is an example of the Dependency Injection because we take
        // in a class as a parameter instead of storing it
        this.screen = new LoginPresenter();
        this.prompter = new InputPrompter();
        this.um = um;

    }

    /**
     * Prompts the user for a valid username and password
     * @return username of the user that has logged in with correct credentials
     */

    public String loginUser(){
        String username = prompter.getResponse("Please enter your username");
        while(!um.userExists(username)){
            username = prompter.getResponse("That username does not exist." +
                    System.lineSeparator() + "Please enter a username that exists, or create an account");
        }
        System.out.println(username);

        String password = prompter.getResponse("Please enter your password");
        System.out.println("Test");
        while(!um.getPassword(username).equals(password)){
            password = prompter.getResponse("That password is incorrect." +
                    System.lineSeparator() + "Please enter the correct password");
        }

        return username;
    }

}
