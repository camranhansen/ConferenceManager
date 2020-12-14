package csc.zerofoureightnine.conferencemanager.users.permission;


public enum Category {
    //TODO make more descriptive 
    USER("Manage user data"),
    MESSAGE("Read and write messages"),
    EVENT("Manage events"),
    DATA("View data collected"),
    SPECIAL_REQUEST("Manage special requests"),;
    
    
    private final String renderableText;
    //theoretically.. could add PairedSubController here.
    Category(String renderableText){
        this.renderableText = renderableText;
    }

    /**
     * @return a user-friendly {@link String} of what each category manages.
     */
    public String getRenderableText() {
        return renderableText;
    }
}
