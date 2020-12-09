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
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class SessionController implements SectionController { //UI
    private UserManager userManager;
    private Set<SessionObserver> observers = new HashSet<>();
    private List<MenuNode> entryNodes = new ArrayList<>();
    private MenuNode mainMenu;
    private MenuNode authenticate;
    private MenuNode collectUsername;
    private String currentUser;
    private boolean loggedIn;
    

    public SessionController(PersistentMap<String, UserData> userData) {
        this.userManager = new UserManager(userData);
    }

    public boolean validationPass(String username, List<MenuNode> options) {
        return true;
    }

    public MenuNode attemptAuthentication(String username, String input, Map<Integer, MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions) {
        if (userManager.userExists(currentUser) && userManager.getPassword(currentUser).equals(input)) {
            loggedIn = true;
        }
        onAuthAttempted();
        return mainMenu;
    }

    public MenuNode collectUsername(String username, String input, Map<Integer, MenuNode> selectableOptions, Map<Permission, MenuNode> selectablePermissions) {
        this.currentUser = input;
        return authenticate;
    }

    private void setupAuthenticate() {
        //Build username auth.
        MenuNodeBuilder builder = new MenuNodeBuilder();
        LoginPresenter loginPresenter = new LoginPresenter(false);
        this.addObserver(loginPresenter);
        builder.setPresentable(loginPresenter);
        builder.setAction(this::attemptAuthentication, this::validationPass);
        this.authenticate = builder.build();
    }

    private void setupCollectUsername() {
        MenuNodeBuilder builder = new MenuNodeBuilder();
        builder.setPresentable(new LoginPresenter(true));
        builder.setAction(this::collectUsername, this::validationPass);
        builder.addOptions(authenticate);
        this.collectUsername = builder.build();
        this.entryNodes.add(collectUsername);
    }

    private void onAuthAttempted() {
        for (SessionObserver observer : observers) {
            observer.authenticationAttempted(currentUser, loggedIn);
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
}
