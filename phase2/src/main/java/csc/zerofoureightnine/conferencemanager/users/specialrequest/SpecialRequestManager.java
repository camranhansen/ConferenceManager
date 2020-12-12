package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;

import java.util.*;

public class SpecialRequestManager {
    /**
     * requestMap stores a hashmap where the keys are usernames and the corresponding value is a list of SpecialRequests.
     */
    PersistentMap<UUID, SpecialRequestData> requestDataMap;

    /**
     * Instantiates the SpecialRequestManager. Mostly for testing.
     * @param requestMap an empty or pre-constructed hashmap that relates a username to the appropriate entity.
     */
    public SpecialRequestManager(PersistentMap<UUID, SpecialRequestData> requestMap) {
        this.requestDataMap = requestMap;
    }

    /**
     * Returns a List of usernames corresponding to Users that have made SpecialRequest objects.
     * @return the List of usernames of the User objects who made SpecialRequest objects
     */
    public List<String> getRequestingUsers(){
        List<String> usernames = new ArrayList<>();
        for (SpecialRequestData request: this.requestDataMap.values()) {
            String username = request.getRequestingUser();
            if (!usernames.contains(username)){
                usernames.add(username);
            }
        }
        return usernames;
    }

    /**
     * Returns a List of UUIDs which correspond to a SpecialRequest object, for a given username.
     * @param username the username of the User object whose SpecialRequests should be retrieved
     * @return the UUIDs of the SpecialRequest objects corresponding to the username
     */
    public List<UUID> getRequests(String username){
        List<UUID> lst = new ArrayList<>();
        for (SpecialRequestData requestData: this.getRequestDataAsList(username)) {
            lst.add(requestData.getId());
        }
        return lst;
    }

    /**
     * For Testing! Returns a List of SpecialRequest objects, for a given username.
     * @param username the username of the User object whose SpecialRequests should be retrieved
     * @return the SpecialRequest objects corresponding to the username
     */
    public List<SpecialRequestData> getRequestDataAsList(String username){
        return this.requestDataMap.loadForSame("requestingUser", username);
    }


    /**
     * Adds a new SpecialRequest object to the list corresponding to the requestingUser
     * @param requestingUser the username of the User to tie the SpecialRequest to
     * @param header the general category for the SpecialRequest (e.g. Dietary, Accessibility, etc.)
     * @param description a specific requirement (e.g Allergies to nuts, Wheelchair service, etc.)
     * @param addressed flags whether the SpecialRequest has been taken care of.
     */
    public void addRequest(String requestingUser, String header, String description, boolean addressed){
        this.requestDataMap.beginInteraction();
        SpecialRequestData sr = new SpecialRequestData(requestingUser, header, description, addressed);
        this.requestDataMap.put(sr.getId(), sr);
        this.requestDataMap.endInteraction();
    }

    /**
     * Takes in a single SpecialRequest to remove from the current list of SpecialRequests a user has.
     * Does nothing if no more items can be removed or if the username does not exist
     * the username of the User object who's SpecialRequest should be removed
     * @param request the SpecialRequest to remove
     */
    public void removeRequest(SpecialRequestData request){
        this.removeRequest(request.getId());
    }

    /**
     * Takes in a single UUID, corresponding to an existing SpecialRequest.
     * Removes this SpecialRequest from the current list of SpecialRequests a user has
     * @param requestID the username of the User object who's SpecialRequest should be removed
     */
    public void removeRequest(UUID requestID){
        this.requestDataMap.beginInteraction();
        this.requestDataMap.remove(requestID);
        this.requestDataMap.endInteraction();
    }

    /**
     * Takes in a single UUID, corresponding to an existing SpecialRequest.
     * This SpecialRequest is then marked as addressed.
     * @param requestID the username of the User object who's SpecialRequest should be addressed
     */
    public void addressRequest(UUID requestID){
        this.requestDataMap.beginInteraction();
        this.requestDataMap.get(requestID).setAddressed(true);
        this.requestDataMap.endInteraction();
    }

    /**
     * Returns a List of UUIDs which correspond to a SpecialRequest object that have NOT been addressed.
     * @return the List of UUIDs corresponding to pending SpecialRequests
     */
    public List<UUID> getPendingRequests(){
        List<SpecialRequestData> data = this.requestDataMap.loadForSame("addressed", false);
        List<UUID> ids = new ArrayList<>();
        for (SpecialRequestData request: data) {
            ids.add(request.getId());
        }
        return ids;
    }

    /**
     * Returns a List of UUIDs which correspond to a SpecialRequest object that have been addressed.
     * @return the List of UUIDs corresponding to addressed SpecialRequests
     */
    public List<UUID> getAddressedRequests(){
        List<SpecialRequestData> data = this.requestDataMap.loadForSame("addressed", true);
        List<UUID> ids = new ArrayList<>();
        for (SpecialRequestData request: data) {
            ids.add(request.getId());
        }
        return ids;
    }

    /**
     * FOR TESTING: Returns a SpecialRequest object based on its corresponding UUID.
     * If such a UUID is not found, returns null.
     * @param requestID a UUID corresponding to a SpecialRequest
     * @return the required SpecialRequest, or if it does not exist, null
     */
    public SpecialRequestData getRequestFromID(UUID requestID) {
        return this.requestDataMap.get(requestID);
    }

    /**
     * Returns a List of strings where:
     * index 0 (id) = requestID of the SpecialRequest
     * index 1 (requestingUser) = username of the requesting User
     * index 2 (header) = header of the SpecialRequest (e.g. "Dietary", "Physical", etc.)
     * index 3 (description) = specific details of the SpecialRequest (e.g. "i am lactose intolerant", etc.)
     * index 4 (addressed) = the status of this SpecialRequest (i.e pending if false, addressed if true)
     * @param requestID the UUID of the SpecialRequest
     * @return a LinkedHashMap with Strings as keys and the corresponding String values
     * with the contents defined above
     */
    public Map<String, String> getRequestDetails(UUID requestID){
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        SpecialRequestData r = this.getRequestFromID(requestID);
        details.put("id", r.getId().toString());
        details.put("requestingUser", r.getRequestingUser());
        details.put("header", r.getHeader());
        details.put("description", r.getDescription());
        details.put("addressed", String.valueOf(r.getAddressed()));
        return details;
    }



}
