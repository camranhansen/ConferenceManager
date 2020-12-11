package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.ArrayList;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionUI implements UISection, SessionObserver {
    private SessionController sessionController;
    private SessionPresenter sessionPresenter;
    private List<MenuNode> entryNodes;
    private MenuNode loginUserNode;
    private MenuNode CreateUserNode;
    private MenuNode logoutUserNode;
    private MenuNode changePersonalPasswordNode;


    public SessionUI(SessionController sessionController, SessionPresenter sessionPresenter) {
        this.sessionPresenter = sessionPresenter;
        this.sessionController = sessionController;
        this.sessionController.addObserver(sessionPresenter);
        this.sessionController.addObserver(this);
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryNodes != null)
            return entryNodes;
        entryNodes = new ArrayList<>();

        entryNodes.add((loginUserNode = buildAuthenticationNode()));
        entryNodes.add((CreateUserNode = buildUserCreationNode()));
        entryNodes.add((changePersonalPasswordNode = buildChangePersonalPasswordNode()));
        entryNodes.add((logoutUserNode = buildLogoutNode()));
        logoutUserNode.setDisabled(true);

        return entryNodes;
    }

    private MenuNode buildAuthenticationNode() {
        LinkedMenuNodeBuilder authenticationSeq = new LinkedMenuNodeBuilder("Login", sessionController.getInputMap());
        authenticationSeq.addStep("user", sessionPresenter::requestUsername, null, null);
        authenticationSeq.addStep("password", sessionPresenter::requestPassword, null, null);
        MenuNodeBuilder authEnd = new MenuNodeBuilder("Login", sessionController::loginUser);
        authEnd.setCompletable(sessionPresenter::authenticationAttemptedMessage);
        authEnd.backStepCount(3);
        return authenticationSeq.build(authEnd.build());
    }

    private MenuNode buildUserCreationNode() {
        String userCreation = "Create Attendee Account";
        LinkedMenuNodeBuilder userCreationSeq = new LinkedMenuNodeBuilder(userCreation, sessionController.getInputMap());
        userCreationSeq.addStep("user", sessionPresenter::requestUsername, sessionController::checkUserNotExist, sessionPresenter::userExistsError);
        userCreationSeq.addStep("password", sessionPresenter::requestPassword, null, null);
        MenuNodeBuilder createUserEnd = new MenuNodeBuilder(userCreation, sessionController::createUser);
        createUserEnd.setCompletable(sessionPresenter::accountCreated);
        createUserEnd.backStepCount(3);
        return userCreationSeq.build(createUserEnd.build());
    }

    private MenuNode buildLogoutNode() {
        MenuNodeBuilder logoutNode = new MenuNodeBuilder("Logout", sessionController::logOutUser);
        logoutNode.setCompletable(sessionPresenter::loggedOut);
        return logoutNode.build();
    }

    private MenuNode buildChangePersonalPasswordNode() {
        MenuNodeBuilder changeNode = new MenuNodeBuilder("Change your password", sessionController::changePassword);
        changeNode.setPromptable(sessionPresenter::requestPassword);
        changeNode.setCompletable(sessionPresenter::passwordChanged);

        return changeNode.build();
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
            changePersonalPasswordNode.setDisabled(false);
        } else {
            loginUserNode.setDisabled(false);
            CreateUserNode.setDisabled(false);
            logoutUserNode.setDisabled(true);
            changePersonalPasswordNode.setDisabled(true);
        }
    }

    
}
