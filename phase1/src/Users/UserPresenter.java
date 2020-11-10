package Users;

import java.util.Arrays;
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
        String options = "";
        for (Template p: templates) {
            options += templates.indexOf(p)+ ": " +
                    p.toString().replace("_", " ") + "\n";
        }

        System.out.println(prompt);
        System.out.println(options);;}

}

