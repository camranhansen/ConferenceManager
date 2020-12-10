package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.ArrayList;

import csc.zerofoureightnine.conferencemanager.interaction.GeneralMenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;

public class MenuBuilder {
    private MenuNodeBuilder mainMenu;
    ArrayList<MenuNode> sectionNodes = new ArrayList<>();

    public MenuBuilder(MenuNodeBuilder rootMenuNodeBuilder) {
        this.mainMenu = rootMenuNodeBuilder;

    }

    public void addSectionUI(UISection... sectionControllers) {
        for (UISection sectionController : sectionControllers) {
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
