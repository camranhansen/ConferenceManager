package Menus;

import Users.Permission;

public class Option {
    private String optionText;
    private Permission permissionHeld;


    public Option(String optionText){
        this.optionText = optionText;
    }

    public Option(String optionText, Permission permission){
        this.optionText = optionText;
        this.permissionHeld = permission;
    }

    public Permission getPermissionHeld(){
        return this.permissionHeld;
    }

    @Override
    public String toString() {
        return optionText;
    }
    public void run(){
        //This is to be overriden
    }


}
