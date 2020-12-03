package csc.zerofoureightnine.conferencemanager.menu;


import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.EnumMap;

public class MenuNode {


    private MenuNode parent;
    private EnumMap<Permission, MenuNode> children;
    private StateFlag stateflag;

    public MenuNode(MenuNode parent, EnumMap<Permission,
                    MenuNode> children,
                    StateFlag flag) {
        this.parent = parent;
        this.children = children;
        this.stateflag = flag;
    }

    public MenuNode getParent() {
        return parent;
    }

    public EnumMap<Permission, MenuNode> getChildren() {
        return children;
    }

    public StateFlag getStateFlag() {
        return stateflag;
    }
}
