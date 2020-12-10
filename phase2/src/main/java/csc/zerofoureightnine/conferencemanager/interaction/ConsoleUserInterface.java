package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;
import java.util.Scanner;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

public class ConsoleUserInterface implements SessionObserver { //UI
    private MenuNode mainMenu;
    private MenuNode currentNode;
    private String currentUser;
    private List<Permission> userPermissions;
    private Scanner scanner;

    public ConsoleUserInterface(MenuNode startingNode) {
        this.mainMenu = startingNode;
        this.currentNode = startingNode;
        this.scanner = new Scanner(System.in);
    }

    public void interact() {
        while(true) {
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

    
}
