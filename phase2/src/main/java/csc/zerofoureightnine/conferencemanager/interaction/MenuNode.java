package csc.zerofoureightnine.conferencemanager.interaction;

import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.control.Validatable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.*;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public interface MenuNode {
    MenuNode executeNode(String username, Scanner scanner, List<Permission> userPermissions, MenuNode mainMenu);

    void setDisabled(boolean disabled);

    Permission getPermission();

    EnumMap<Permission, MenuNode> getPermissionOptions();

    Set<MenuNode> getChildren();

    MenuNode getParent();

    Validatable getValidatable();

    Nameable getNameable();

    Action getAction();

    Completable getCompletable();

    Promptable getPromptable();

    Listable getListable();

    Reattemptable getReattemptable();

    boolean isDisabled();

    void setParent(MenuNode parent);
}
