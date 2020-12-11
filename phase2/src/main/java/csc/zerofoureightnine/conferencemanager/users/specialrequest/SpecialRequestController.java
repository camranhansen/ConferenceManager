package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

import java.util.HashMap;
import java.util.List;

public class SpecialRequestController {

    private SpecialRequestManager requestManager;
    private UserManager userManager;
    private HashMap<String, String> inputMap = new HashMap<>();


    /**
     * Initiates SpecialRequestController
     * @param requestManager RequestManager
     * @param userManager UserManager
     */
    public SpecialRequestController(SpecialRequestManager requestManager, UserManager userManager) {
        this.requestManager = requestManager;
        this.userManager = userManager;
    }


    /**
     * Remove the request with the given request id. Returns 0.
     * @param username username of the user
     * @param input user input
     * @param opts options
     * @return int 0
     */
    public int removeRequest(String username, String input, List<TopicPresentable> opts){
        //requestManager.removeRequest(inputMap.get());
        return 0;
    }

    /**
     * Add a new user request. Returns 0.
     * @param username username of the user
     * @param input user input
     * @param opts options
     * @return int 0
     */
    public int addRequest(String username, String input, List<TopicPresentable> opts){
        requestManager.addRequest(username, inputMap.get("header"), inputMap.get("description"), false);
        return 0;
    }

    /**
     * Address the request. Returns 0.
     * @param username username of the user
     * @param input user input
     * @param opts options
     * @return int 0
     */
    public int addressRequest(String username, String input, List<TopicPresentable> opts){
        //requestManager.addressRequest(inputMap.get());
        return 0;
    }

    /**
     * Returns whether the given username is valid or not.
     * @param id username
     * @param options options
     * @return true if {@code id} exists, otherwise return false
     */
    public boolean isValidID(String id, List<TopicPresentable> options) {
        return this.userManager.userExists(id);
    }


}
    //    public boolean validatorTemplate(String input, List<TopicPresentable> options) {
//      Return true if the validation check is sucessful
//    }

//        public int editPassword(String username, String input, List<TopicPresentable> opts) {
//        um.setPassword(username, inputMap.get("password"));
//        return 0;
//    }

