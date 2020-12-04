package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

import java.util.LinkedHashMap;

public interface MenuAction {

    public void run(LinkedHashMap<MenuNode, String> inputHistory, String username);
    //HashMap<String, String> where key = MenuNode prompt. and value is what the user entered.


}
