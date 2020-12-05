package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class EventViewOptions implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("View all events", Permission.VIEW_ALL_EVENTS));
        options.add(new Option("View events which you can register for", Permission.VIEW_ALL_EVENTS));
        options.add(new Option("View my events", Permission.VIEW_ALL_EVENTS));
        return options;
    }
}
