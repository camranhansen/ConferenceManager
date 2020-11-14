package Menus;

import Users.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MenuController {

    private MenuPresenter mp;
    private String username;
    private List<Permission> permissions;
    private HashMap<String,SubController> subcontrollers;
    private InputPrompter prompter;

    public MenuController(String username, List<Permission> permissions,
                          HashMap<String, SubController> subcontrollers) {
            this.mp = new MenuPresenter();
            this.username = username;
            this.permissions = permissions;
            this.subcontrollers = subcontrollers;
            this.prompter = new InputPrompter();

    }


    public void makeMenu(){
        boolean keepGoing = selectSubcontroller();
        while(keepGoing){
            keepGoing = selectSubcontroller();
        }
    }
    public boolean selectSubcontroller(){
        //Selects the subcontroller necessary for the given permission, and then tells it what permission is being used.


        if (permissions.size() < 8){
            Permission permissionSelected = selectPermission(permissions);
            subcontrollers.get(permissionSelected.getCategory()).performSelectedAction(username, permissionSelected);

        }
        else{
            List<String> categories = new ArrayList<>();
            for (Permission p: permissions) {
                if (!categories.contains(p.getCategory()))
                    categories.add(p.getCategory());
            }

            ArrayList<Option> categoryOptions = new ArrayList<>();
            for (String category: categories){
                categoryOptions.add(new Option(category));
            }
            Option choice =  prompter.menuOption(categoryOptions);
            if(choice.toString().equals("Exit")){

                return false;
            }
            String categoryChoice = choice.toString();
            List<Permission> selectedPermissions = new ArrayList<>();
            for (Permission p: permissions) {
                if (p.getCategory().equals(categoryChoice)){
                    selectedPermissions.add(p);
                }
            }

            Permission permissionSelected = selectPermission(selectedPermissions);
            this.subcontrollers.get(categoryChoice).performSelectedAction(this.username, permissionSelected);

        }
        return true;
    }


    public Permission selectPermission(List<Permission> permissionsToShow){

        ArrayList<Option> optionList = new ArrayList<>();

        for(Permission p: permissionsToShow){
            optionList.add(new Option(p.toString(),p));
        }

        Option optionSelected = prompter.menuOption(optionList);

        return optionSelected.getPermissionHeld();


    }





}
