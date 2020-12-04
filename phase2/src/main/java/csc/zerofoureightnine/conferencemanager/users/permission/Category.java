package csc.zerofoureightnine.conferencemanager.users.permission;

public enum Category {
    //TODO make more descriptive 
    USER("Manage "),
    MESSAGE("Read and write messages"),
    EVENT("Manage events"),
    DATA("View data collected"),
    SPECIAL_REQUEST("Manage special requests"),;
    
    
    private final String renderableText;
    
    Category(String renderableText){
        this.renderableText = renderableText;
    }
    
    public String getrenderableText(){
        return this.renderableText;
    }
}
