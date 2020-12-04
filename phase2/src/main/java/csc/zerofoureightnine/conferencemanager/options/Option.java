package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Category;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class Option {
    private String optionText;
    private Permission permissionHeld;
    private Template templateHeld;
    private Category categoryHeld;

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

    public Option(String optionText, Category category){
        this.optionText = optionText;
        this.categoryHeld = category;
    }


    public Permission getPermissionHeld(){
        return this.permissionHeld;
    }

    public Template getTemplateHeld(){
        return this.templateHeld;
    }

    public Category getCategoryHeld(){return this.categoryHeld;}


    @Override
    public String toString() {
        return optionText;
    }
    public void run(){
        //This is to be overriden
    }


}
