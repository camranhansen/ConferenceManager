package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.Permission;

import java.util.EnumMap;
import java.util.List;

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
