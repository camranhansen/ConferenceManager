package csc.zerofoureightnine.conferencemanager.menu;

import java.util.LinkedHashMap;

public interface MenuAction {

    public void run(LinkedHashMap<MenuNode, String> inputHistory, String username);
    //HashMap<String, String> where key = MenuNode prompt. and value is what the user entered.


}
