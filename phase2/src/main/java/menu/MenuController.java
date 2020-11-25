package menu;

import users.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuController {

    private MenuPresenter mp;
    private String username;
    private List<Permission> permissions;
    private HashMap<String,SubController> subcontrollers;
    private InputPrompter prompter;

    /**
     * @param username the username of the user that this menu is being generated for
     * @param permissions the list of permissions that the user has available to them
     * @param subcontrollers the subcontrollers that correspond with the total possible list of permissions
     */

    public MenuController(String username, List<Permission> permissions,
                          HashMap<String, SubController> subcontrollers) {
            this.mp = new MenuPresenter();
            this.username = username;
            this.permissions = permissions;
            this.subcontrollers = subcontrollers;
            this.prompter = new InputPrompter();

    }


    /** Continuously calls selectSubController until the user exits from the program by selecting the exit option
     * In selectSubcontroller
     */
    public void makeMenu(){
        boolean keepGoing = selectSubcontroller();
        while(keepGoing){
            keepGoing = selectSubcontroller();
        }
    }

    /**
     * Prompts the user for numeric input regarding the permission that they wish to invoke.
     * Calls on the @see SubController interface corresponding with the selected permission
     * To perform the selected action
     * @return Returns true if the user has selected to exit. Returns false if the user has selected something other than to exit.
     */
    public boolean selectSubcontroller(){

        if (permissions.size() < 8){
            Option optionSelected = selectPermission(permissions);
            if (optionSelected.toString().equals("EXIT")){
                return false;
            }
            Permission permissionSelected = optionSelected.getPermissionHeld();
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
            if(choice.toString().equals("EXIT")||choice.toString().equals("Exit")){

                return false;
            }
            String categoryChoice = choice.toString();
            List<Permission> selectedPermissions = new ArrayList<>();
            for (Permission p: permissions) {
                if (p.getCategory().equals(categoryChoice)){
                    selectedPermissions.add(p);
                }
            }

            Permission permissionSelected = selectPermission(selectedPermissions).getPermissionHeld();
            this.subcontrollers.get(categoryChoice).performSelectedAction(this.username, permissionSelected);

        }
        return true;
    }


    /**
     * The user selects a permission from an inputted list
     * @param permissionsToShow the list of permissions that the user can select from
     * @return the permission the user has selected
     */
    public Option selectPermission(List<Permission> permissionsToShow){

        ArrayList<Option> optionList = new ArrayList<>();

        for(Permission p: permissionsToShow){
            optionList.add(new Option(p.toString(),p));
        }

        return prompter.menuOption(optionList);
    }
}
