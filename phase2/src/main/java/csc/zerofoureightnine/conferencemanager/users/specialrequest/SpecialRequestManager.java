package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;

public class SpecialRequestManager {
    /**
     * requestMap stores a hashmap where the keys are usernames and the corresponding value is a list of SpecialRequests.
     */
    HashMap<String, List<SpecialRequest>> requestMap;

    /**
     * Instantiates the SpecialRequestManager
     * @param requestMap an empty or pre-constructed hashmap that relates a username to the appropriate entity.
     */
    public SpecialRequestManager(HashMap<String, List<SpecialRequest>> requestMap) {
        this.requestMap = requestMap;
    }

    /**
     * Returns a List of usernames corresponding Users have made SpecialRequest objects.
     * @return the List of usernames of the User objects who made SpecialRequest objects
     */
    public List<String> getRequestingUsers(){
        return new ArrayList<>(requestMap.keySet());
    }

    /**
     * Returns a List of UUIDs which correspond to a SpecialRequest object, for a given username.
     * @param username the username of the User object whose SpecialRequests should be retrieved
     * @return the UUIDs of the SpecialRequest objects corresponding to the username
     */
    public List<UUID> getRequests(String username){
        List<UUID> lst = new ArrayList<>();
        for (SpecialRequest s: this.requestMap.get(username)) {
            lst.add(s.getRequestID());
        }
        return lst;
    }

    /**
     * FOR TESTING: Returns a List of SpecialRequest objects, for a given username.
     * @param username the username of the User object whose SpecialRequests should be retrieved
     * @return the SpecialRequest objects corresponding to the username
     */
    public List<SpecialRequest> getRequestsList(String username){
        return this.requestMap.get(username);
    }

    /**
     * Takes in a List of SpecialRequests to replace the current list of SpecialRequests a user has.
     * If user exists in requestMap, replaces value to input specialRequests.
     * Otherwise, creates new key and sets value to input specialRequests.
     * @param username the username of the User object who's SpecialRequests should be set
     * @param specialRequests a new list of SpecialRequests to set for this user
     */
    public void setRequests(String username, List<SpecialRequest> specialRequests){
        this.requestMap.put(username, specialRequests);
    }

    /**
     * Adds a new SpecialRequest object to the list corresponding to the requestingUser
     * @param requestingUser the username of the User to tie the SpecialRequest to
     * @param header the general category for the SpecialRequest (e.g. Dietary, Accessibility, etc.)
     * @param description a specific requirement (e.g Allergies to nuts, Wheelchair service, etc.)
     * @param addressed flags whether the SpecialRequest has been taken care of.
     */
    public void addRequest(String requestingUser, String header, String description, boolean addressed){
        SpecialRequest specialRequest = new SpecialRequest(requestingUser, header, description, addressed);
        this.addRequest(requestingUser, specialRequest);
    }

    /**
     * Adds a new SpecialRequest object to the list corresponding to the requestingUser.
     * @param username the username of the User to tie the SpecialRequest to
     * @param specialRequest the SpecialRequest object to add
     */
    public void addRequest(String username, SpecialRequest specialRequest){
        List<SpecialRequest> lst = new ArrayList<>();
        if (this.requestMap.containsKey(username)){
            lst.addAll(this.requestMap.get(username));
        }
        lst.add(specialRequest);
        this.setRequests(username, lst);
    }

    /**
     * Takes in a single SpecialRequest to remove from the current list of SpecialRequests a user has.
     * Does nothing if no more items can be removed or if the username does not exist
     * @param username the username of the User object who's SpecialRequest should be removed
     * @param request the SpecialRequest to remove
     */
    public void removeRequest(String username, SpecialRequest request){
        List<SpecialRequest> lst = new ArrayList<>();
        if (this.requestMap.containsKey(username)) {
            lst.addAll(this.requestMap.get(username));
        }
        lst.remove(request);
        this.setRequests(username, lst);
    }

    /**
     * Takes in a single UUID, corresponding to an existing SpecialRequest.
     * Removes this SpecialRequest from the current list of SpecialRequests a user has
     * @param requestID the username of the User object who's SpecialRequest should be removed
     */
    public void removeRequest(UUID requestID){
        SpecialRequest request = getRequestFromID(requestID);
        if (request != null){
            this.removeRequest(request.getRequestingUser(), request);
        }

    }

    /**
     * Takes in a single UUID, corresponding to an existing SpecialRequest.
     * This SpecialRequest is then marked as addressed.
     * @param requestID the username of the User object who's SpecialRequest should be addressed
     */
    public void addressRequest(UUID requestID){
        SpecialRequest request = getRequestFromID(requestID);
        if (request != null){
            request.setAddressed(true);
        }
    }

    /**
     * Returns a List of UUIDs which correspond to a SpecialRequest object that have NOT been addressed.
     * @return the List of UUIDs corresponding to pending SpecialRequests
     */
    public List<UUID> getPendingRequests(){
        List<UUID> lst = new ArrayList<>();
        for (List<SpecialRequest> requests: this.requestMap.values()) {
            for (SpecialRequest r : requests) {
                if (!r.isAddressed()) lst.add(r.getRequestID());
            }
        }
        return lst;
    }

    /**
     * Returns a List of UUIDs which correspond to a SpecialRequest object that have been addressed.
     * @return the List of UUIDs corresponding to addressed SpecialRequests
     */
    public List<UUID> getAddressedRequests(){
        List<UUID> lst = new ArrayList<>();
        for (List<SpecialRequest> requests: this.requestMap.values()) {
            for (SpecialRequest r : requests) {
                if (r.isAddressed()) lst.add(r.getRequestID());
            }
        }
        return lst;
    }

    /**
     * FOR TESTING: Returns a SpecialRequest object based on its corresponding UUID.
     * If such a UUID is not found, returns null.
     * @param requestID a UUID corresponding to a SpecialRequest
     * @return the required SpecialRequest, or if it does not exist, null
     */
    public SpecialRequest getRequestFromID(UUID requestID) {
        for (List<SpecialRequest> requests: this.requestMap.values()) {
            for (SpecialRequest r : requests) {
                if (r.getRequestID().equals(requestID)) return r;
            }
        }
        return null;
    }

    /**
     * Returns a List of strings where:
     * index 0 = requestID of the SpecialRequest
     * index 1 = username of the requesting User
     * index 2 = header of the SpecialRequest (e.g. "Dietary", "Physical", etc.)
     * index 3 = specific details of the SpecialRequest (e.g. "i am lactose intolerant", etc.)
     * @param requestID the UUID of the SpecialRequest
     * @return a List of Strings with the contents defined above
     */
    public List<String> getRequestDetails(UUID requestID){
        List<String> details = new ArrayList<>();
        SpecialRequest r = this.getRequestFromID(requestID);
        details.add(r.getRequestID().toString());
        details.add(r.getRequestingUser());
        details.add(r.getHeader());
        details.add(r.getDescription());
        return details;
    }


}
