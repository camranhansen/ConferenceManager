package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;
import java.util.Scanner;

import csc.zerofoureightnine.conferencemanager.interaction.utils.ExitObserver;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

public class ConsoleUserInterface implements SessionObserver, ExitObserver { //UI
    private MenuNode mainMenu;
    private MenuNode currentNode;
    private String currentUser;
    private List<Permission> userPermissions;
    private Scanner scanner;
    private boolean running;

    public ConsoleUserInterface(MenuNode startingNode) {
        this.mainMenu = startingNode;
        this.currentNode = startingNode;
        this.scanner = new Scanner(System.in);
    }

    public void interact() {
        running = true;
        while(running) {
            currentNode = currentNode.executeNode(currentUser, scanner, userPermissions, mainMenu);
        }
    }

    public void stop() {
        scanner.close();
    }

    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        if (loggedIn) {
            this.userPermissions = permissions;
            this.currentUser = username;
        } else {
            this.currentUser = null;
            this.userPermissions = null;
        }
    }

    @Override
    public void exitting() {
        running = false;
    }

    
}
