package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;

import java.util.List;

public class UserUI implements UISection {
    private UserController userController;
    private UserPresenter userPresenter;
    List<MenuNode> entryPoints;

    public UserUI(UserController userController, UserPresenter userPresenter) {
        this.userController = userController;
        this.userPresenter = userPresenter;
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;

        //put generators here.
        return entryPoints;
    }

    @Override
    public String getSectionListing() {
        return "User Management";
    }
}
