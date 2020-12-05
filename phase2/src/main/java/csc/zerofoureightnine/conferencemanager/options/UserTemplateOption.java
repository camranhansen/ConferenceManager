package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.ArrayList;
import java.util.List;

public class UserTemplateOption implements Optionable{
    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        Template[] templates = Template.values();
        for (Template t : templates) {
            options.add(new Option(t.toString(), t));
        }
        return options;
    }
}
