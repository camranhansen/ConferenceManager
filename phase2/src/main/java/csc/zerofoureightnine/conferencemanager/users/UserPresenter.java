package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

public class UserPresenter {

    /**
     * Asks the user to enter in a username, corresponding to a User account
     * @return a String containing the question to be asked
     */
    public String enterUsername(String username){
        return ("Please enter the relevant username");
    }

    /**
     * Asks the user to enter in a password, corresponding to a User account
     * @return a String containing the question to be asked
     */
    public String enterPassword(String username){
        return ("Please enter the relevant password");
    }

    /**
     * Asks the user to enter in a new password for corresponding to a User account
     * @return a {@link String} containing the question to be asked
     */
    public String newPassword(String username){
        return "Please enter new password";
    }

    /**
     * Informs the User that the username already exists
     * @return a String telling the user of the issue
     */
    public String userExists(){
        return ("Username already exists!");
    }

    /**
     * Informs the User that the password is not valid
     * @return a String telling the user of the issue
     */
    public String wrongPassword(){
        return ("Password is incorrect!");
    }

    /**
     * Informs the User that something they inputted is invalid
     * @return a String telling the user of the issue
     */
    public String wrongInput() {
        return ("Invalid Input");
    }

    public String accountCreationSuccess(String username, TopicPresentable next) {
        return "account created successfully! Returning to: " + next.getIdentifier();
    }

    public String passwordChangedSuccess(String username, TopicPresentable next) {
        return "Password updated successfully! Returning to: " + next.getIdentifier();
    }

    public String accountDeleted(String username, TopicPresentable next) {
        return "Account deleted. Returning to " + next;
    }

    public String usernameInvalid() {
        return "username invalid, please try again";
    }

}

