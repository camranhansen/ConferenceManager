package csc.zerofoureightnine.conferencemanager.users.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.interaction.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.SectionController;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class SessionController implements SectionController { // UI
    private List<MenuNode> entryNodes;
    private UserManager userManager;
    private String loggedInUser;

    public SessionController(PersistentMap<String, UserData> userData) {
        this.userManager = new UserManager(userData);
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryNodes != null) return entryNodes;
        entryNodes = new ArrayList<>();
        final HashMap<String, String> inputMap = new HashMap<>();
        //Login sequence
        String name = "Login";
        MenuNodeBuilder authenticationBuilder = new MenuNodeBuilder(name, (u, in, o) -> {
            String user = inputMap.get("user");
            if (userManager.userExists(user) && userManager.getPassword(user).equals(in)) loggedInUser = user;
            return o.get(0);
        }, (n) -> {
            return loggedInUser != null ? "Login success. Welcome " + loggedInUser : "Login failed. Please try again.";
        });
        authenticationBuilder.setPromptable(() -> "Password");
        MenuNode authEnd = authenticationBuilder.build();

        LinkedMenuNodeBuilder linkedMenuNodeBuilder = new LinkedMenuNodeBuilder("Login", inputMap);
        linkedMenuNodeBuilder.addStep("user", () -> "Username", null, () -> "");
        entryNodes.add(linkedMenuNodeBuilder.build(authEnd));
        return entryNodes;
    }

    @Override
    public String getSectionListing() {
        if (loggedInUser != null)
            return loggedInUser + "'s Account";
        return "Create Account / Login";
    }
}
