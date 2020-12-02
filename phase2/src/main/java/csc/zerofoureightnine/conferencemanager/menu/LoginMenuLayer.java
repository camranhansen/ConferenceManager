package csc.zerofoureightnine.conferencemanager.menu;

public class LoginMenuLayer extends MenuLayer {
    InputPrompter prompter;
    public LoginMenuLayer(InputPrompter prompter){
        this.currentStateFlag = StateFlag.LOGIN;
        this.goalFlag = GoalFlag.CONTINUE;


    }
    public MainMenuLayer run(){

        return new MainMenuLayer();
    }
}
