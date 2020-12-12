package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.Arrays;
import java.util.List;

public class UserPresenter {

    public String enterUsername(){
        return ("Please enter the relevant username:");
    }

    public String enterPassword(){
        return ("Please enter the relevant password:");
    }

    public String userExists(){
        return ("Username already exists!");
    }

    public String wrongPassword(){
        return ("Password is incorrect!");
    }

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

