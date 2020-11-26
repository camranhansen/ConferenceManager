package menu;

import java.util.Stack;

public class Menu {

    public Stack<MenuLayer> menuLayerStack;
    public static MenuLayer exitMenuLayer;

    public Menu(MenuLayer bottomLayer){
        this.menuLayerStack = new Stack<>();
        this.menuLayerStack.push(bottomLayer);
        exitMenuLayer = new MenuLayer(GoalFlag.EXIT);
    }

    public void runTopMenuLayer(){

        MenuLayer newLayer = menuLayerStack.peek();
        switch (newLayer.getGoalFlag()){

            case EXIT:
                System.out.println("program should exit now?");
                break;
            case LOGIN:
                //
            case MAIN:
                //
            case BACK:
                //
            case CONTINUE:
                menuLayerStack.push(newLayer.run());
                runTopMenuLayer();

        }

    }

    public void goBackToMainScreen(){

    }


}
