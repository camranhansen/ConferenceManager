package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import java.util.HashMap;

public class SpecialRequestController {

    private SpecialRequestManager requestManager;
    private HashMap<String, String> inputMap = new HashMap<>();


    public SpecialRequestController(SpecialRequestManager requestManager) {
        this.requestManager = requestManager;
    }

    //    public boolean validatorTemplate(String input, List<TopicPresentable> options) {
//      Return true if the validation check is sucessful
//    }

//        public int editPassword(String username, String input, List<TopicPresentable> opts) {
//        um.setPassword(username, inputMap.get("password"));
//        return 0;
//    }
}
