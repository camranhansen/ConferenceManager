package csc.zerofoureightnine.conferencemanager.options;

import csc.zerofoureightnine.conferencemanager.users.permission.Category;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoryOption implements Optionable{

    public List<Option> generateOptions() {
        List<Option> options = new ArrayList<>();
        for (Category category: Category.values()){
            options.add(new Option(category.getrenderableText(), category));
        }
        return options;
    }
}
