package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class MessageViewOption implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("View all messages", Permission.VIEW_SELF_MESSAGES));
        options.add(new Option("View message from specific username", Permission.VIEW_SELF_MESSAGES));
        options.add(new Option("View archived messages", Permission.VIEW_SELF_MESSAGES));
        options.add(new Option("View unread messages", Permission.VIEW_SELF_MESSAGES));
        return options;
    }
}
