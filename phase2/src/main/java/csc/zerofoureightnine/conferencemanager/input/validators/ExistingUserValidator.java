package csc.zerofoureightnine.conferencemanager.input.validators;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class ExistingUserValidator implements Validator{
    private UserManager userManager;

    public ExistingUserValidator(UserManager um){
        userManager = um;
    }

    public boolean validateInput(String userInput) {
        return userManager.userExists(userInput);
    }
}
