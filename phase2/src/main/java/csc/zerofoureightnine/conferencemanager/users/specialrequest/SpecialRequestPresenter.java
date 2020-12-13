package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpecialRequestPresenter {

    private SpecialRequestManager manager;
    private Map<String, String> inputMap;

    /**
     * Initiates SpecialRequestPresenter
     * @param manager  SpecialRequestManager
     * @param inputMap Hashmap mapping strings to associating user inputs
     */
    public SpecialRequestPresenter(SpecialRequestManager manager, Map<String, String> inputMap) {
        this.manager = manager;
        this.inputMap = inputMap;
    }

    /**
     * Returns all special requests from a specific users. Each request is in the format of "id, header,
     * description, addressed".
     * @return the string representation of all special requests of a given user
     */
    public String getRequests(String username){
        List<UUID> lst = manager.getRequests(username);
        return renderRequestList(lst);
    }

    /**
     * Returns all special requests that are pending. Each request is in the format of "id, requestingUser, header,
     * description".
     * @return the string representation of all pending special requests
     */
    public String getPendingRequests(String username){
        List<UUID> lst = manager.getPendingRequests();
        return renderRequestList(lst);
    }

    /**
     * Returns all special requests that are addressed. Each request is in the format of "id, requestingUser, header,
     * description".
     *
     * @return the string representation of all addressed special requests
     */
    public String getAddressedRequests(String username) {
        List<UUID> lst = manager.getAddressedRequests();

        return renderRequestList(lst);
    }


    /**
     * Returns the prompt for users to enter the header of their request.
     *
     * @return the string "Please enter the header of your request".
     */
    public String enterHeader(String username){
        return "Please enter the header of your request";
    }


    /**
     * Returns the prompt for users to enter the description of their request.
     * @return the string "Please enter the description of your request".
     */
    public String enterDescription(String username){
        return "Please enter the description of your request";
    }


    /**
     * Returns the prompt for users to enter the requestID.
     * @return the string "please enter the request_id".
     */
    public String enterRequestID(String username){
        return "Please enter the request id";
    }


    /**
     * Returns the prompt when the requestID is invalid.
     * @return the string "This request_id is invalid, please try again".
     */
    public String invalidRequestID() {
        return "This request_id is invalid, please try again";
    }

    /**
     * Returns a string to confirm to the user a Request has been created
     *
     * @param username the username of the User
     * @param p        a flag
     * @return a string for confirmation
     */
    public String requestCreateConfirmation(String username, TopicPresentable p) {
        return "Request created!";
    }

    /**
     * Render a special request (i.e. process the text prettily)
     *
     * @return the rendered string.
     */
    private String renderRequestList(List<UUID> uuid_List) {
        StringBuilder requestsRendered = new StringBuilder();
        for (UUID id : uuid_List) {
            requestsRendered.append("************************************************").append(System.lineSeparator());

            manager.getRequestDetails(id).forEach((category, dataPoint) ->
                    requestsRendered.
                            append(category).append(": ").
                            append(dataPoint).append(".").
                            append(System.lineSeparator()));

        }
        return requestsRendered.toString();

    }
}

