package csc.zerofoureightnine.conferencemanager.interaction.general;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.control.Validatable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

/**
 * A general {@link Action} and {@link Validatable} meant for choosing between the children
 * to jump to.
 */
public class OptionSelector implements Action, Validatable {

    @Override
    public boolean validateInput(String input, List<TopicPresentable> options) {
        return input.matches("^[0-9]+$") && 
        Integer.parseInt(input) < options.size() && 
        options.get(Integer.parseInt(input)) != null;
    }

    @Override
    public int complete(String username, String input, List<TopicPresentable> selectableOptions) {
        return Integer.parseInt(input);
    }

}
