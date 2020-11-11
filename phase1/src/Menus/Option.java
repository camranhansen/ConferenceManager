package Menus;

public class Option {
    private String optionText;
    private Object objectHeld; //This is inherently not a good idea.


    public Option(String optionText){
        this.optionText = optionText;
    }

    @Override
    public String toString() {
        return optionText;
    }


}
