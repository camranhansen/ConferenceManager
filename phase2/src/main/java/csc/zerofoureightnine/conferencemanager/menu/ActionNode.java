package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.EnumMap;

public class ActionNode extends MenuNode {

    private Permission associatedPermission;

    public ActionNode(MenuNode parent,
                      EnumMap<Permission, MenuNode> children,
                      StateFlag flag,
                      Permission associatedPermission) {
        super(parent, children, flag);
        this.associatedPermission = associatedPermission;
    }

    public Permission getAssociatedPermission() {
        return associatedPermission;
    }
}
