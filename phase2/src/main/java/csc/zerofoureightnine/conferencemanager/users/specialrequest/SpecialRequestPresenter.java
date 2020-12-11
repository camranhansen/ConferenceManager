package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpecialRequestPresenter {

    private SpecialRequestManager manager;
    private HashMap<String, String> inputMap;

    /**
     * Initiates MessagePresenter
     * @param manager  SpecialRequestManager
     * @param inputMap Hashmap mapping strings to associating user inputs
     */
    public SpecialRequestPresenter(SpecialRequestManager manager, HashMap<String, String> inputMap) {
        this.manager = manager;
        this.inputMap = inputMap;
    }

    public String getRequests(){
        StringBuilder requests = new StringBuilder();
        List<UUID> lst = manager.getRequests(inputMap.get("username"));
        for (UUID id: lst){
            Map<String, String> map = manager.getRequestDetails(id);
            requests.append(map.get("id")).append(", ").append(map.get("header")).append(", ").
                    append(map.get("description")).append(", ").append(map.get("addressed")).append("\n");
        }
        return requests.toString();
    }

    public String getPendingRequests(){
        StringBuilder requests = new StringBuilder();
        List<UUID> lst = manager.getPendingRequests();
        for (UUID id: lst){
            Map<String, String> map = manager.getRequestDetails(id);
            requests.append(map.get("id")).append(", ").append(map.get("requestingUser")).append(", ").
                    append(map.get("header")).append(", ").append(map.get("description"));
        }
        return requests.toString();
    }

    public String getAddressedRequests(){
        StringBuilder requests = new StringBuilder();
        List<UUID> lst = manager.getAddressedRequests();
        for (UUID id: lst){
            Map<String, String> map = manager.getRequestDetails(id);
            requests.append(map.get("id")).append(", ").append(map.get("requestingUser")).append(", ").
                    append(map.get("header")).append(", ").append(map.get("description"));
        }
        return requests.toString();
    }


}
