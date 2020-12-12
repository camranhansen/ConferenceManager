package csc.zerofoureightnine.conferencemanager.users;

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

}

