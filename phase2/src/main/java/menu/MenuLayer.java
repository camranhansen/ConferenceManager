package menu;


public class MenuLayer {
    public MenuFlag flag;

    public MenuLayer(){
        this.flag = MenuFlag.CONTINUE;
    }

    public MenuLayer(MenuFlag flag){
        this.flag = flag;

    }


    public MenuLayer run(){
        //Note that this has to be overridden
        return new MenuLayer(MenuFlag.EXIT);
    }


    public MenuFlag getFlag(){
        return flag;
    }

}



