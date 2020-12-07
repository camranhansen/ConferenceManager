package csc.zerofoureightnine.conferencemanager.options;

import java.util.ArrayList;
import java.util.List;

public class MessageMoveOption implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Move to archived"));
        options.add(new Option("Move to unread"));
        return options;
    }
}
