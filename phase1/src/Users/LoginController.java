package Users;
import java.util.Scanner;

public class LoginController {
    private LoginPresenter screen;


    public LoginController(){
        this.screen = new LoginPresenter();
    }

    // DPE: This is an example of the Dependency Injection because we take
    // in a class as a parameter instead of storing it
    public String loginUser(UserManager um){
        Scanner userInput = new Scanner(System.in);
        //TODO: Actually implement the validity check
        this.screen.promptUsername();
        String username = userInput.nextLine();

        this.screen.promptPassword();
        String password = userInput.nextLine();

        //TODO: only return when sure it is valid, else bad things happen...
        return username;
    }

}
