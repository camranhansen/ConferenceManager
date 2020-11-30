package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.SpecialRequestManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class LoginMenuLayer extends MenuLayer {
    InputPrompter prompter;
    public LoginMenuLayer(InputPrompter prompter){
        this.currentStateFlag = CurrentStateFlag.LOGIN;
        this.goalFlag = GoalFlag.CONTINUE;


    }
    public MainMenuLayer run(EventManager em,
                             UserManager um,
                             MessageManager mm,
                             PermissionManager pm,
                             SpecialRequestManager rm){
        //NOTE: these names are intentionally short because the passing of use cases
        //is irrelevant to actually being read by a human.

        //if input == exit..
        //if input == .....
        //checkIfExiting()
        //if exiting is true return new MenuLayer(EXIT)
        //inputprompter.pair(this)
        // if exiting... then call paired menu function exitEarly()
        // which returns a new Menu with the exit flag
        return new MainMenuLayer(em, um, mm, pm, rm);
    }
}
