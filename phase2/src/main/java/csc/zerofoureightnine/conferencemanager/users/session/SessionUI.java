package csc.zerofoureightnine.conferencemanager.users.session;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * User interface for session.
 */
public class SessionUI implements UISection, SessionObserver {
    private SessionController sessionController;
    private SessionPresenter sessionPresenter;
    private List<MenuNode> entryNodes;
    private MenuNode loginUserNode;
    private MenuNode CreateUserNode;
    private MenuNode logoutUserNode;
    private MenuNode changePersonalPasswordNode;


    /**
     * Initiates SessionUI
     * @param sessionController {@link SessionController}
     * @param sessionPresenter {@link SessionPresenter}
     */
    public SessionUI(SessionController sessionController, SessionPresenter sessionPresenter) {
        this.sessionPresenter = sessionPresenter;
        this.sessionController = sessionController;
        this.sessionController.addObserver(sessionPresenter);
        this.sessionController.addObserver(this);
    }

    /**
     * Returns all entry {@link MenuNode} required for the functionality of {@link SessionController}.
     * @return a list of entry {@link MenuNode}
     */
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

    /**
     * Build the authentication node
     *
     * @return {@link MenuNode} representing the authentication node
     */
    private MenuNode buildAuthenticationNode() {
        LinkedMenuNodeBuilder authenticationSeq = new LinkedMenuNodeBuilder("Login", sessionController.getInputMap());
        authenticationSeq.addStep("user", sessionPresenter::requestUsername, null, null);
        authenticationSeq.addStep("password", sessionPresenter::requestPassword, null, null);
        MenuNodeBuilder authEnd = new MenuNodeBuilder("Login", sessionController::loginUser);
        authEnd.setCompletable(sessionPresenter::authenticationAttemptedMessage);
        authEnd.backStepCount(3);
        return authenticationSeq.build(authEnd.build());
    }

    /**
     * Build the {@link csc.zerofoureightnine.conferencemanager.users.permission.Template#ATTENDEE} creation node
     *
     * @return {@link MenuNode} representing the {@link csc.zerofoureightnine.conferencemanager.users.permission.Template#ATTENDEE} creation node
     */
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

    /**
     * Build the logout node
     *
     * @return {@link MenuNode} representing the logout node
     */
    private MenuNode buildLogoutNode() {
        MenuNodeBuilder logoutNode = new MenuNodeBuilder("Logout", sessionController::logOutUser);
        logoutNode.setCompletable(sessionPresenter::loggedOut);
        return logoutNode.build();
    }

    /**
     * Build the change password node
     *
     * @return {@link MenuNode} representing the password change node.
     */
    private MenuNode buildChangePersonalPasswordNode() {
        MenuNodeBuilder changeNode = new MenuNodeBuilder("Change your password", sessionController::changePassword);
        changeNode.setPromptable(sessionPresenter::requestPassword);
        changeNode.setCompletable(sessionPresenter::passwordChanged);

        return changeNode.build();
    }

    /**
     * Check if the user is logged in and returns corresponding {@link String} prompts.
     * @return {@link String} "Login / Create account" if user is not logged in,
     * {@link SessionController#getLoggedInUser()} + "'s account" otherwise.
     */
    @Override
    public String getSectionListing() {
        if (sessionController.getLoggedInUser() != null)
            return sessionController.getLoggedInUser() + "'s account";
        return "Login / Create account";
    }

    /**
     * Enable and disable the corresponding functionalities by user's login status
     * @param username username of the user
     * @param permissions list of {@link Permission} that the user has
     * @param loggedIn login status of the user
     */
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
