package menu;

import users.Permission;
import users.Template;

public class Option {
    private String optionText;
    private Permission permissionHeld;
    private Template templateHeld;

    public Option(String optionText){
        this.optionText = optionText;
    }

    public Option(String optionText, Permission permission){
        this.optionText = optionText;
        this.permissionHeld = permission;
    }

    public Option(String optionText, Template template){
        this.optionText = optionText;
        this.templateHeld = template;
    }


    public Permission getPermissionHeld(){
        return this.permissionHeld;
    }

    public Template getTemplateHeld(){
        return this.templateHeld;
    }


    @Override
    public String toString() {
        return optionText;
    }
    public void run(){
        //This is to be overriden
    }


}
