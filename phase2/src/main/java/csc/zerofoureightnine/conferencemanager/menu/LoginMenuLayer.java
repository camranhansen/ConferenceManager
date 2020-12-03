package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.input.InputPrompter;

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
