package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;

/**
 * Currently does not do much, but can be extended to do different things in the future.
 * For example - set data collection type.
 */
public class DataController {
    private HashMap<String, String> inputMap;

    /**
     * Instantiate a new DataController
     */
    public DataController() {
        this.inputMap = new HashMap<>();
    }

    /**
     * Get the input map
     *
     * @return the input map.
     */
    public HashMap<String, String> getInputMap() {
        return inputMap;
    }

    /**
     * Method called whenever a view method is called in presenter.
     *
     * @param username          The username of the person who calls this method
     * @param input             The input associated when the user calls this method. In this case, it would be null.
     * @param selectableOptions Selectable options at this layer. In this case, there are none, since this is a view method. Lol
     * @return the menu to go to. Returns to main menu, since returning 0, much like most controller methods.
     */
    public int viewMethod(String username, String input, List<TopicPresentable> selectableOptions) {
        return 0;
    }

}
