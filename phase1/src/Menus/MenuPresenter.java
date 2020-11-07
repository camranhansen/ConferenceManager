package Menus;

import Users.Permission;

import java.util.ArrayList;
import java.util.List;


public class MenuPresenter {

    //TODO: Consider starting the option list from 1 instead of 0.
    public void presentOptions(List<Permission> permissions) {
        String prompt = "Which action would you like to perform?";
        String options = "";
        for (Permission p: permissions) {
            options += permissions.indexOf(p)+ ": " +
                    p.toString().replace("_", " ") + "\n";
        }

        System.out.println(prompt);
        System.out.println(options);
    }


    public void presentCategories(List<Permission> permissions) {
        String prompt = "Which category would you like to see?";
        List<String> categories = new ArrayList<>();
        for (Permission p: permissions) {
            if (!categories.contains(p.getCategory()))
            categories.add(p.getCategory());
        }
        String options = "";
        for (String c: categories) {
            options += categories.indexOf(c)+ ": " + c + "\n";
        }
        System.out.println(prompt);
        System.out.println(options);
    }


}
