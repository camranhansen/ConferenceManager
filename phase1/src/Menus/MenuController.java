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

    public MenuController(String username, List<Permission> permissions,
                          HashMap<String, SubController> subcontrollers) {
            this.mp = new MenuPresenter();
            this.username = username;
            this.permissions = permissions;
            this.subcontrollers = subcontrollers;

    }


    public void selectSubcontroller(){
        //Selects the subcontroller necessary for the given permission, and then tells it what permission is being used.


        if (permissions.size() < 8){

            Permission permissionSelected = selectPermission(this.permissions);
            String category = permissionSelected.getCategory();
            this.subcontrollers.get(category).performSelectedAction(this.username, permissionSelected);


        }
        else{
            List<String> possibleCategories = this.mp.presentCategories(permissions);
            //TODO: Don't expect just a number. use IsNumeric to parse whether number input or text
            Scanner userInput = new Scanner(System.in);
            int categoryInput = userInput.nextInt();
            String categoryChoice = possibleCategories.get(categoryInput);
            List<Permission> selectedPermissions = new ArrayList<Permission>();
            for (Permission p: permissions) {
                if (p.getCategory() == categoryChoice){
                    selectedPermissions.add(p);
                }
            }

            Permission permissionSelected = selectPermission(selectedPermissions);
            this.subcontrollers.get(categoryChoice).performSelectedAction(this.username, permissionSelected);
        }
    }

    public Permission selectPermission(List<Permission> permissionsToShow){

        this.mp.presentOptions(permissionsToShow);
        Scanner userInput = new Scanner(System.in);
        String option = userInput.nextLine();
        //TODO: Don't expect just a number. use IsNumeric to parse whether number input or text
        Permission permissionSelected = permissionsToShow.get(Integer.parseInt(option));

        return permissionSelected;


    }





}
