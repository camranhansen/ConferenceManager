package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.ArrayList;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.GeneralMenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionUI implements UISection, SessionObserver {
    private SessionController sessionController;
    private List<MenuNode> entryNodes;
    private MenuNode loginUserNode;
    private MenuNode CreateUserNode;
    private MenuNode logoutUserNode;


    public SessionUI(SessionController sessionController) {
        this.sessionController = sessionController;
        this.sessionController.addObserver(this);
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryNodes != null)
            return entryNodes;
        entryNodes = new ArrayList<>();
        LinkedMenuNodeBuilder authenticationSeq = new LinkedMenuNodeBuilder("Login", sessionController.getInputMap());
        authenticationSeq.addStep("user", sessionController.getPresenter()::requestUsername, null, null);
        authenticationSeq.addStep("password", sessionController.getPresenter()::requestPassword, null, null);
        MenuNodeBuilder authEnd = new MenuNodeBuilder("Login", sessionController::loginUser, sessionController.getPresenter()::authenticationAttemptedMessage);
        entryNodes.add((loginUserNode = authenticationSeq.build(authEnd.build())));

        String userCreation = "Create Attendee Account";
        LinkedMenuNodeBuilder userCreationSeq = new LinkedMenuNodeBuilder(userCreation, sessionController.getInputMap());
        userCreationSeq.addStep("user", sessionController.getPresenter()::requestUsername, sessionController::checkUserNotExist, sessionController.getPresenter()::userExistsError);
        userCreationSeq.addStep("password", sessionController.getPresenter()::requestPassword, null, null);
        MenuNodeBuilder createUserEnd = new MenuNodeBuilder(userCreation, sessionController::createUser, sessionController.getPresenter()::accountCreated);
        entryNodes.add((CreateUserNode = userCreationSeq.build(createUserEnd.build())));


        MenuNodeBuilder logoutNode = new MenuNodeBuilder("Logout", sessionController::logOutUser, sessionController.getPresenter()::loggedOut);
        entryNodes.add((logoutUserNode = logoutNode.build()));
        logoutUserNode.setDisabled(true);

        return entryNodes;
    }

    @Override
    public String getSectionListing() {
        if (sessionController.getLoggedInUser() != null)
            return sessionController.getLoggedInUser() + "'s account";
        return "Login / Create account";
    }

    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        if (loggedIn) {
            loginUserNode.setDisabled(true);
            CreateUserNode.setDisabled(true);
            logoutUserNode.setDisabled(false);
        } else {
            loginUserNode.setDisabled(false);
            CreateUserNode.setDisabled(false);
            logoutUserNode.setDisabled(true);
        }
    }

    
}
