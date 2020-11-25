package specialrequests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;

public class SpecialRequestManager {
    HashMap<String, List<SpecialRequest>> requestMap;

    public SpecialRequestManager(HashMap<String, List<SpecialRequest>> requestMap) {
        this.requestMap = requestMap;
    }

    public List<UUID> getUserRequests(String username){
        return new ArrayList<>();

    }

    public List<String> getRequestingUsers(){
        return new ArrayList<>(requestMap.keySet());
    }

    public List<UUID> getPendingRequests(){
        return new ArrayList<>();
    }

    public List<UUID> getAddressedRequests(){
        return new ArrayList<>();

    }

    public void addRequest(String requestingUser, String header, String description, boolean addressed){

    }


    public void removeRequest(UUID requestID){

    }

    public void addressRequest(UUID requestID){

    }

}
