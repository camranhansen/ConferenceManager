package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpecialRequestController {

    private SpecialRequestManager requestManager;
    private HashMap<String, String> inputMap = new HashMap<>();


    /**
     * Initiates SpecialRequestController
     * @param requestManager RequestManager
     */
    public SpecialRequestController(SpecialRequestManager requestManager) {
        this.requestManager = requestManager;
    }


    /**
     * Remove the request with the given request id. Returns 0.
     *
     * @param username username of the user
     * @param input    user input
     * @param opts     options
     * @return int 0
     */
    public int removeRequest(String username, String input, List<TopicPresentable> opts) {
        requestManager.removeRequest(UUID.fromString(inputMap.get("request_id")));
        return 0;
    }

    /**
     * Add a new user request. Returns 0.
     *
     * @param username username of the user
     * @param input    user input
     * @param opts     options
     * @return int 0
     */
    public int addRequest(String username, String input, List<TopicPresentable> opts) {
        requestManager.addRequest(username, inputMap.get("header"), inputMap.get("description"), false);
        return 0;
    }

    /**
     * Address the request. Returns 0.
     *
     * @param username username of the user
     * @param input    user input
     * @param opts     options
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
     * @param options options
     * @return true if {@code id} exists, otherwise return false
     */
    public boolean isValidID(String id, List<TopicPresentable> options) {
        return this.requestManager.getPendingRequests().contains(UUID.fromString(id)) ||
                this.requestManager.getAddressedRequests().contains(UUID.fromString(id));
    }
}


