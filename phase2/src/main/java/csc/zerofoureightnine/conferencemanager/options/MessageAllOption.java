package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class MessageAllOption implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Message all attendees", Permission.MESSAGE_ALL_USERS));
        options.add(new Option("Message all speakers", Permission.MESSAGE_ALL_USERS));
        return options;
    }
}
