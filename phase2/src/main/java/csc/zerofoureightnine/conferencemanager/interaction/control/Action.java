package csc.zerofoureightnine.conferencemanager.interaction.control;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.Nameable;

public interface Action {

    /**
     * The user is said to have completed an action when this returns. These actions may be simple
     * menu traversing, or more complex such as sending messages.
     * @param username the currently logged in users username. May be null if nobody is logged in.
     * @param input the user input after being validated.
     * @param selectableOptions The options the user has available to them at this point. 0 will 
     *                          always be main menu, 1 will always be parent. Additionally, parent
     *                          may be null if the node does not have a parent.
     * @return The next node from the list of options to move to.
     */
    public Nameable complete(String username, String input, List<Nameable> selectableOptions);
}
