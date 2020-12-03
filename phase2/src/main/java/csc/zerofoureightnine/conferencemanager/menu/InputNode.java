package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.EnumMap;

public class InputNode extends  MenuNode{
    private InputStrategy verificationCheck;
    private String prompt;

    public InputNode(MenuNode parent,
                     EnumMap<Permission, MenuNode> children,
                     StateFlag flag,
                     InputStrategy verificationCheck,
                     String prompt) {
        super(parent, children, flag);
        this.verificationCheck = verificationCheck;
        this.prompt = prompt;
    }
}
