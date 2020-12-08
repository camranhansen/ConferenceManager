package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Category;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionMenuOption implements Optionable{
    private Map<InputStrategy, String> inputHistory;
    private UserManager userManager;
    private PermissionManager pm;
    private String username;
    
    public PermissionMenuOption(Map<InputStrategy, String> inputHistory, UserManager userManager, String username) {
        this.userManager = userManager;
        this.inputHistory = inputHistory;
        this.username = username;

    }
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        List<Permission> permissions = getUserPermissions();
        for (Permission permission:permissions){
            options.add(new Option(permission.toString(), permission));
        }
        return options;
    }

    private List<Permission> getUserPermissions(){
        String category = inputHistory.get(InputStrategy.CATEGORY_MENU);
        Category categorySelected = Category.valueOf(category);
        List<Permission> permissions = pm.getPermissions(username);
        List<Permission> categoryPermissions = new ArrayList<>();
        for (Permission permission:permissions){
            if(permission.getCategory().equals(categorySelected)){
                categoryPermissions.add(permission);
            }
        }
        return categoryPermissions;
    }
}
