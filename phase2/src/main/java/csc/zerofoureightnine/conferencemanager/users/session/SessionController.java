package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.SectionController;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.general.AutoPresenter;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionController implements SectionController { //UI
    private UserManager userManager;
    private Set<SessionObserver> observers = new HashSet<>();
    private List<MenuNode> entryNodes = new ArrayList<>();
    private MenuNode loginEntryNode;
    private MenuNode logoutEntryNode;
    private String currentUser;
    private boolean loggedIn;
    

    public SessionController(PersistentMap<String, UserData> userData) {
        this.userManager = new UserManager(userData);
    }

    public MenuNode attemptAuthentication(String username, String input, List<MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions) {
        if (userManager.userExists(currentUser) && userManager.getPassword(currentUser).equals(input)) {
            loggedIn = true;
            loginEntryNode.setDisabled(true);
            logoutEntryNode.setDisabled(false);
        }
        onAuthAttempted();
        return selectableOptions.get(0);
    }

    public MenuNode collectUsername(String username, String input, List<MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions) {
        this.currentUser = input;
        return selectableOptions.get(2);
    }

    public MenuNode logout(String username, String input, List<MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions) {
        loggedIn = false;
        username = null;
        onLogout();
        loginEntryNode.setDisabled(false);
        logoutEntryNode.setDisabled(true);
        return selectableOptions.get(0);
    }

    private void onAuthAttempted() {
        for (SessionObserver observer : observers) {
            observer.authenticationAttempted(currentUser, loggedIn);
        }
    }

    private void onLogout() {
        for (SessionObserver observer : observers) {
            observer.loggedOut(currentUser);
        }
    }

    public void addObserver(SessionObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(SessionObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        return entryNodes;
    }

    @Override
    public void buildMenuNodes() {
        MenuNodeBuilder builder = new MenuNodeBuilder();
        LoginPresenter loginPresenter = new LoginPresenter(false);
        this.addObserver(loginPresenter);
        builder.setPresentable(loginPresenter);
        builder.setActionAndValidator(this::attemptAuthentication, null);
        MenuNode authenticate = builder.build();

        builder = new MenuNodeBuilder();
        builder.setPresentable(new LoginPresenter(true));
        builder.setActionAndValidator(this::collectUsername, null);
        builder.addOptions(authenticate);
        loginEntryNode = builder.build();
        this.entryNodes.add(loginEntryNode);

        builder = new MenuNodeBuilder();
        builder.setActionAndValidator(this::logout, null);
        builder.setPresentable(new AutoPresenter("Logout", "Successfully logged out."));
        logoutEntryNode = builder.build();
        logoutEntryNode.setDisabled(true);
        this.entryNodes.add(logoutEntryNode);
    }

    @Override
    public String getSectionListing() {
        if (loggedIn) return "Account";
        return "Create Account / Login";
    }
}
