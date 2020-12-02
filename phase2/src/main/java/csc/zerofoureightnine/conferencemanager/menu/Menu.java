package csc.zerofoureightnine.conferencemanager.menu;

import java.util.Stack;


/**
 * Menu....
 */
public class Menu {

    public Stack<MenuLayer> menuLayerStack;
    public static MenuLayer exitMenuLayer = new MenuLayer(GoalFlag.EXIT);
    //NOTE: see the command object

    /**
     * A menu is...
     * @param bottomLayer the bottom layer of this menu stack.
     *                    During runtime, <Code>bottomLayer</Code></> will be instantiated with {@link StateFlag} LOGIN
     */
    public Menu(MenuLayer bottomLayer){
        this.menuLayerStack = new Stack<>();
        this.menuLayerStack.push(bottomLayer);
    }

    public void runTopMenuLayer(){

        MenuLayer newLayer = menuLayerStack.peek();
        switch (newLayer.getGoalFlag()){

            case EXIT:
//                exitOut();
                //TODO actually do exiting stuff.
                break;
            case LOGIN:
                goBackToState(StateFlag.LOGIN);
            case MAIN:
                goBackToState(StateFlag.MAIN);
                //TODO talk to someone about how to test that the currentstateflag is exactly this at this time..
                //Perhaps using logger?
            case BACK:
                goBackOneState();
            case CONTINUE:
                menuLayerStack.push(newLayer.run());
                runTopMenuLayer();

        }

    }

    public void goBackToState(StateFlag stateFlag){
        while(menuLayerStack.peek().getCurrentStateFlag() != stateFlag){
            menuLayerStack.pop();
        }

    }

    public void goBackOneState(){
        menuLayerStack.pop();
    }
    public void exitOut(){
        menuLayerStack.removeAllElements();
    }

    public StateFlag getCurrentStateFlag(){
        if(menuLayerStack.isEmpty()){
            return StateFlag.EMPTY;
        }else{
            return menuLayerStack.peek().getCurrentStateFlag();
        }
    }


}
