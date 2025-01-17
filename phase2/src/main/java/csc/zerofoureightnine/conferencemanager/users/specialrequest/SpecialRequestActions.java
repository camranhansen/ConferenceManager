package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Presenter-level (in MVP) class for holding methods that follow the {@link Action} interface, for modifying the model
 * responsible for special request-related data.
 */
public class SpecialRequestActions {

    private SpecialRequestManager requestManager;
    private Map<String, String> inputMap = new HashMap<>();


    /**
     * Initiates SpecialRequestController
     *
     * @param requestManager {@link SpecialRequestManager}
     */
    public SpecialRequestActions(SpecialRequestManager requestManager) {
        this.requestManager = requestManager;
    }

    /**
     * Remove the request with the given request id.
     *
     * @param username username of the user
     * @param input user input
     * @param opts the options available to user
     * @return int 0
     */
    public int removeRequest(String username, String input, List<TopicPresentable> opts) {
        requestManager.removeRequest(UUID.fromString(inputMap.get("request_id")));
        return 0;
    }

    /**
     * Add a new user request.
     *
     * @param username username of the user
     * @param input user input
     * @param opts the options available to user
     * @return int 0
     */
    public int addRequest(String username, String input, List<TopicPresentable> opts) {
        requestManager.addRequest(username, inputMap.get("header"), inputMap.get("description"), false);
        return 0;
    }

    /**
     * Address the request.
     *
     * @param username username of the user
     * @param input user input
     * @param opts the options available to user
     * @return int 0
     */
    public int addressRequest(String username, String input, List<TopicPresentable> opts) {
        requestManager.addressRequest(UUID.fromString(inputMap.get("request_id")));
        return 0;
    }


    /**
     * Returns the input map of this SpecialRequestController.
     * @return a HashMap of String to String of the input
     */
    public Map<String, String> getInputMap() {
        return inputMap;
    }

    /**
     * A method to connect with the {@link SpecialRequestPresenter} view methods.
     * @param username the username of the current session User
     * @param input the input by the user
     * @param selectableOptions a list of options the user can select
     * @return 0 always to work with the menu system
     */
    public int viewMethod(String username, String input, List<TopicPresentable> selectableOptions){
        return 0;
    }

}


