package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class EventEnrollOption implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Enroll in event", Permission.EVENT_SELF_ENROLL));
        options.add(new Option("Withdraw from event", Permission.EVENT_SELF_ENROLL));
        return options;
    }
}
