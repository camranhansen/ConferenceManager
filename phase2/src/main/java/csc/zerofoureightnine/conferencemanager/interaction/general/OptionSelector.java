package csc.zerofoureightnine.conferencemanager.interaction.general;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.control.Validatable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Nameable;

/**
 * A general {@link Action} and {@link Validatable} meant for choosing between the children
 * to jump to.
 */
public class OptionSelector implements Action, Validatable {

    @Override
    public boolean validateInput(String input, List<Nameable> options) {
        return input.matches("^[0-9]+$") && 
        Integer.parseInt(input) < options.size() && 
        options.get(Integer.parseInt(input)) != null;
    }

    @Override
    public Nameable complete(String username, String input, List<Nameable> selectableOptions) {
        return selectableOptions.get(Integer.parseInt(input));
    }

}
