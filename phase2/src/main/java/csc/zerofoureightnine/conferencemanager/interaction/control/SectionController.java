package csc.zerofoureightnine.conferencemanager.interaction.control;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;

public interface SectionController {

    /**
     * 
     * @return a list of entry menu nodes for the functionality of this controller.
     */
    public List<MenuNode> getEntryMenuNodes();

    public String getSectionListing();
}
