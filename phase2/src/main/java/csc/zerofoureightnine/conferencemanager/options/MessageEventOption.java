package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class MessageEventOption implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Message all your events", Permission.MESSAGE_EVENT_USERS));
        options.add(new Option("Message one of your events", Permission.MESSAGE_EVENT_USERS));
        return options;
    }
}
