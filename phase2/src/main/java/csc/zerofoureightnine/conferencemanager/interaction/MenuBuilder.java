package csc.zerofoureightnine.conferencemanager.interaction;

import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeStatModifier;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;

import java.util.ArrayList;

public class MenuBuilder {
    private MenuNodeBuilder mainMenu;
    ArrayList<MenuNode> sectionNodes = new ArrayList<>();

    public MenuBuilder(MenuNodeBuilder rootMenuNodeBuilder) {
        this.mainMenu = rootMenuNodeBuilder;


    }

    public void addSectionUI(UISection... sectionUI) {
        for (UISection sectionController : sectionUI) {
            MenuNodeBuilder sectionNode = new MenuNodeBuilder(sectionController::getSectionListing);
            sectionNode.addChildren(sectionController.getEntryMenuNodes());
            sectionNodes.add(sectionNode.build());
        }
    }

    public MenuNode build(RuntimeStatModifier tracker) {
        mainMenu.addChildren(sectionNodes);
        MenuNode main = mainMenu.build();
        main.setTracker(tracker);
        return main;
    }
    
}
