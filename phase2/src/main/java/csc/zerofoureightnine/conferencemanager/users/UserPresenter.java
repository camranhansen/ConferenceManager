package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.List;

public class UserPresenter {

    public void promptUsername(){
        System.out.println("Please enter the relevant username:");
    }

    public void promptPassword(){
        System.out.println("Please enter the relevant password:");
    }


    //TODO: Generalize this logic to accept all objects
    // and then call their respective toString()
    public void promptTemplate(List<Template> templates){
        String prompt = "Which action would you like to perform?";
        StringBuilder sb = new StringBuilder();
        for (Template p: templates) {
            sb.append(templates.indexOf(p)+ ": " +
                    p.toString().replace("_", " ") + "\n");
        }

        System.out.println(prompt);
        System.out.println(sb.toString());
    }

}

