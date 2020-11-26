package menu;


public class MenuLayer {
    public GoalFlag goalFlag;
    public CurrentStateFlag currentStateFlag;

    /**
     * Default MenuLayer constructor for some branch menu layer
     */
    public MenuLayer(){
        this.goalFlag = GoalFlag.CONTINUE;
        this.currentStateFlag = CurrentStateFlag.BRANCH;
    }

    /**
     * Used when the user wants to move to a "lower" menu layer.
     * Will result in Menu popping the stack until the state denoted by GoalFlag is reached.
     * @param goalFlag The goal state
     */
    public MenuLayer(GoalFlag goalFlag){
        this.goalFlag = goalFlag;
        this.currentStateFlag = CurrentStateFlag.BRANCH;
    }

    /**
     * Used in the two bottom-most MenuLayers:
     * LOGIN, and MAIN
     * When Menu is constructed, LOGIN must first be put in, and then run,
     * which will add the MAIN layer.
     * @param currentStateFlag denotes the specific state of this MenuLayer.
     *                         Relevant for login and main menu.
     */
    public MenuLayer(CurrentStateFlag currentStateFlag){

        this.goalFlag = GoalFlag.CONTINUE;
        this.currentStateFlag = currentStateFlag;
    }

    /**
     * Run the MenuLayer. If not overridden, the program will exit in Menu
     * Therefore, if you want anything to be done that is not exiting,
     * Please specify.
     * @return MenuLayer
     * The new Menulayer to return. Has goalFlag exit by default.
     */
    public MenuLayer run(){
        //Note that this has to be overridden
        return new MenuLayer(GoalFlag.EXIT);
    }


    public GoalFlag getGoalFlag(){
        return goalFlag;
    }

}



