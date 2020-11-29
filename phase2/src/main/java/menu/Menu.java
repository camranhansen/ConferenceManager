package menu;

import java.util.Stack;

public class Menu {

    public Stack<MenuLayer> menuLayerStack;
    public static MenuLayer exitMenuLayer = new MenuLayer(GoalFlag.EXIT);
    //NOTE: see the command object
    public Menu(MenuLayer bottomLayer){
        this.menuLayerStack = new Stack<>();
        this.menuLayerStack.push(bottomLayer);
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
        while(menuLayerStack.peek().getCurrentStateFlag() != CurrentStateFlag.MAIN){
            menuLayerStack.pop();
        }
    }

    public void goBackToLoginScreen(){

    }

    public void goBackOneLayer(){

    }

    public void exitOut(){

    }

    public CurrentStateFlag getCurrentStateFlag(){
        return menuLayerStack.peek().getCurrentStateFlag();
    }


}
