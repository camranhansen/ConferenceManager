package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpecialRequestController {

    private SpecialRequestManager requestManager;
    private Map<String, String> inputMap = new HashMap<>();


    /**
     * Initiates SpecialRequestController
     * @param requestManager {@link SpecialRequestManager}
     */
    public SpecialRequestController(SpecialRequestManager requestManager) {
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
     * Returns whether the given requestID is valid or not.
     *
     * @param id requestID
     * @param options the options available to user
     * @return true if {@code id} exists, otherwise return false
     */
    public boolean isValidID(String id, List<TopicPresentable> options) {
        return this.requestManager.getPendingRequests().contains(UUID.fromString(id)) ||
                this.requestManager.getAddressedRequests().contains(UUID.fromString(id));
    }
}


