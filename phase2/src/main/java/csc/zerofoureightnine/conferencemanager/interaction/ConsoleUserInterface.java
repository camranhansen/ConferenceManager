package csc.zerofoureightnine.conferencemanager.interaction;

import csc.zerofoureightnine.conferencemanager.interaction.utils.ExitObserver;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

import java.util.List;
import java.util.Scanner;

/**
 * A console user interface. This can be swapped out with a non-console user interface (i.e. GUI),
 * due to our underlying architecture (i.e. the model, presenter) not knowing anything about the UI (view)
 */
public class ConsoleUserInterface implements SessionObserver, ExitObserver { //UI
    private MenuNode mainMenu;
    private MenuNode currentNode;
    private String currentUser;
    private List<Permission> userPermissions;
    private Scanner scanner;
    private boolean running;

    /**
     * Instantiate a console user interface
     *
     * @param startingNode the node to start at, which will be considered the "main menu"
     */
    public ConsoleUserInterface(MenuNode startingNode) {
        this.mainMenu = startingNode;
        this.currentNode = startingNode;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Interact with the nodes until not running
     * See {@link SessionObserver}
     */
    public void interact() {
        running = true;
        while (running) {
            currentNode = currentNode.executeNode(currentUser, scanner, userPermissions, mainMenu);
        }
    }

    /**
     * Stop the scanner.
     */
    public void stop() {
        scanner.close();
    }

    /**
     * Implements the SessionObserver UI
     *
     * @param username    the newly logged or out user, represented by their username.
     * @param permissions a list of the user's {@link Permission}.
     * @param loggedIn    true if someone is logged in, false if otherwise.
     */
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        if (loggedIn) {
            this.userPermissions = permissions;
            this.currentUser = username;
        } else {
            this.currentUser = null;
            this.userPermissions = null;
        }
    }

    /**
     * Denote that the UI is exiting, so that proper saving/closing methods can be done after it.
     */
    @Override
    public void exitting() {
        running = false;
    }


}
