package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MenuPresenter {

    //TODO: Consider starting the option list from 1 instead of 0.
    public void presentOptions(List<Permission> permissions) {
        String prompt = "Which action would you like to perform?";
        StringBuilder options = new StringBuilder();
        for (Permission p: permissions) {
            options.append(permissions.indexOf(p)+ ": " +
            p.toString().replace("_", " ") + "\n");
        }

        System.out.println(prompt);
        System.out.println(options);
    }


    public List<String> presentCategories(List<Permission> permissions) {
        String prompt = "Which category would you like to see?";
        List<String> categories = new ArrayList<>();
        for (Permission p: permissions) {
            if (!categories.contains(p.getCategory()))
            categories.add(p.getCategory().getrenderableText());
        }
        StringBuilder options = new StringBuilder();
        for (String c: categories) {
            options.append(categories.indexOf(c)+ ": " + c + "\n");
        }

        System.out.println(prompt);
        System.out.println(options);
        return categories;
    }


}
