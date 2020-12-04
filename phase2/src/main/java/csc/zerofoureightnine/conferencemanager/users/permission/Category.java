package csc.zerofoureightnine.conferencemanager.users.permission;

import csc.zerofoureightnine.conferencemanager.menu.SubController;

public enum Category {
    //TODO make more descriptive 
    USER("Manage user data"),
    MESSAGE("Read and write messages"),
    EVENT("Manage events"),
    DATA("View data collected"),
    SPECIAL_REQUEST("Manage special requests"),;
    
    
    private final String renderableText;
    //theoretically.. could add PairedSubController here.
    private SubController relatedSub;
    Category(String renderableText){
        this.renderableText = renderableText;
    }
    
    public String getrenderableText(){
        return this.renderableText;
    }


    public void setRelatedSub(SubController relatedSub){
        this.relatedSub = relatedSub;
    }

    public SubController getRelatedSub(){
        return this.relatedSub;
    }
}
