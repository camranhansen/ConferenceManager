package csc.zerofoureightnine.conferencemanager.interaction.menus;

import java.util.ArrayList;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.SectionController;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;

public class MenuBuilder {
    private MenuNodeBuilder mainMenu;
    ArrayList<MenuNode> sectionNodes = new ArrayList<>();

    public MenuBuilder(MenuNodeBuilder rootMenuNodeBuilder) {
        this.mainMenu = rootMenuNodeBuilder;

    }

    public void addSectionControllers(SectionController... sectionControllers) {
        for (SectionController sectionController : sectionControllers) {
            sectionController.buildMenuNodes();
            MenuNodeBuilder sectionNode = new MenuNodeBuilder();
            sectionNode.setBasicPresentation(sectionController.getSectionListing());
            sectionNode.addOptions(sectionController.getEntryMenuNodes());
            sectionNodes.add(sectionNode.build());
        }
    }

    public MenuNode build() {
        mainMenu.addOptions(sectionNodes);
        return mainMenu.build();
    }
    
}
