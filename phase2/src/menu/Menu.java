package menu;

import java.util.Stack;

public class Menu {

    public Stack<MenuLayer> menuLayerStack;

    public void runTopMenuLayer(){
        //TODO flag logic.
        //if flag is back1...
        // if flag is exit_to_main...
        MenuLayer newLayer = menuLayerStack.peek().run();
        if (newLayer == null){
            goBackToMainScreen();
        }else{
            menuLayerStack.push(newLayer);
            runTopMenuLayer();
        }
    }

    public void goBackToMainScreen(){
        //Get rid of all elements in menuLayerStack except for main screen and login.
    }


}
