package csc.zerofoureightnine.conferencemanager.interaction.general;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.Action;

public class AutoSelector implements Action {

    @Override
    public MenuNode complete(String username, String input, List<MenuNode> selectableOptions) {
        return selectableOptions.get(3);
    }
    
}
