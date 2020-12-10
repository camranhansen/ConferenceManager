package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.ArrayList;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.SectionController;

public class MenuBuilder {
    private MenuNodeBuilder mainMenu;
    ArrayList<MenuNode> sectionNodes = new ArrayList<>();

    public MenuBuilder(MenuNodeBuilder rootMenuNodeBuilder) {
        this.mainMenu = rootMenuNodeBuilder;

    }

    public void addSectionControllers(SectionController... sectionControllers) {
        for (SectionController sectionController : sectionControllers) {
            MenuNodeBuilder sectionNode = new MenuNodeBuilder(sectionController::getSectionListing);
            sectionNode.addChildren(sectionController.getEntryMenuNodes());
            sectionNodes.add(sectionNode.build());
        }
    }

    public MenuNode build() {
        mainMenu.addChildren(sectionNodes);
        return mainMenu.build();
    }
    
}
