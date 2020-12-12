package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.Arrays;
import java.util.List;

public class UserPresenter {

    /**
     * Asks the user to enter in a username, corresponding to a User account
     * @return a String containing the question to be asked
     */
    public String enterUsername(){
        return ("Please enter the relevant username");
    }

    /**
     * Asks the user to enter in a password, corresponding to a User account
     * @return a String containing the question to be asked
     */
    public String enterPassword(){
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

//    public String enterTemplate() {
//        List<Template> templates = Arrays.asList(Template.values());
//        StringBuilder sb = new StringBuilder();
//        for (Template p: templates) {
//            sb.append(templates.indexOf(p)+ ": " +
//                    p.toString().replace("_", " ") + "\n");
//        }
//        return sb.toString();
//    }

}

