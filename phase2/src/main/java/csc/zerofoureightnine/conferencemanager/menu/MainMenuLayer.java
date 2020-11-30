package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.SpecialRequestManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class MainMenuLayer extends MenuLayer {
    private EventManager em;
    private UserManager um;
    private MessageManager mm;
    private PermissionManager pm;
    private SpecialRequestManager rm;

    public MainMenuLayer(EventManager em,
                         UserManager um,
                         MessageManager mm,
                         PermissionManager pm,
                         SpecialRequestManager rm){
        em = em;
        um = um;
        mm = mm;
        pm = pm;
        rm = rm;
        this.currentStateFlag = CurrentStateFlag.MAIN;
        this.goalFlag = GoalFlag.CONTINUE;
    }

    @Override
    public MenuLayer run(){

        //Prompt user for category choice.
        // return a new SubMenuLayer corresponding with the choice
        // E.g. return new UserMenuLayer(um);

        //Use builder pattern to get some sort of AllManager
        return new MenuLayer();
    }
}
