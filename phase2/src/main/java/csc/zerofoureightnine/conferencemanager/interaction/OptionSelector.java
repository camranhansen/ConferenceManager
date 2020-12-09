package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;
import java.util.Map;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class OptionSelector implements Action, Validatable {

    @Override
    public boolean validateInput(String input, List<MenuNode> options) {
        return !input.matches("^[0-9]+$") || Integer.parseInt(input) >= options.size();
    }

    @Override
    public MenuNode complete(String username, String input, Map<Integer, MenuNode> selectableOptions,
            Map<Permission, MenuNode> selectablePermissions) {
        return selectableOptions.get(Integer.parseInt(input));
    }

}
