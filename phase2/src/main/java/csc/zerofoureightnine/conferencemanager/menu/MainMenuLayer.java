package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class MainMenuLayer extends MenuLayer {
    private EventManager em;
    private UserManager um;
    private MessageManager mm;
    private PermissionManager pm;
    private SpecialRequestManager rm;

    public MainMenuLayer(){

    }

    @Override
    public MenuLayer run(){


        return new MenuLayer();
    }
}
