package menu;


public abstract class MenuLayer {
    public String flag;


    abstract public MenuLayer run();

    abstract public String toString();

    public String getFlag(){
        return flag;
    }

}
