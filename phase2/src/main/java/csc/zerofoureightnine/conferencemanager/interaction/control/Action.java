package csc.zerofoureightnine.conferencemanager.interaction.control;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

public interface Action {

    /**
     * The user is said to have completed an action when this returns. These actions
     * may be simple menu traversing, or more complex such as sending messages.
     * 
     * @param username          the currently logged in users username. May be null
     *                          if nobody is logged in.
     * @param input             the user input after being validated. May be empty
     *                          if no input is requested, never null.
     * @param selectableOptions The options the user has available to them at this
     *                          point. 0 will always be main menu unless action is
     *                          being ran on main menu, in which case will be null.
     *                          1 will always go back. The actual node this points
     *                          to depends on
     *                          {@link MenuNodeBuilder#backStepCount(int)}.
     * @return An integer representing next node from the list of options to move
     *         to.
     */
    public int complete(String username, String input, List<TopicPresentable> selectableOptions);
}
