package csc.zerofoureightnine.conferencemanager.users;

import org.junit.Test;

import javax.xml.ws.soap.Addressing;
import java.util.*;

import static org.junit.Assert.*;

public class SpecialRequestManagerTest {

    private HashMap<String, List<SpecialRequest>> generateRequestMap(){
        HashMap<String,List<SpecialRequest>> populator = new HashMap<>();
        SpecialRequest request1 = new SpecialRequest("bob","Dietary","I can't eat water or air", false);
        SpecialRequest request2 = new SpecialRequest("bob","Physical","My brazilian macaw, Pringles, must attend events with me", false);
        SpecialRequest request3 = new SpecialRequest("tim","Dietary","I only eat orange foods", false);
        SpecialRequest request4 = new SpecialRequest("linda","Physical","I can only sit at chairs 90 degrees or less", true);

        List<SpecialRequest> bobrequests = new ArrayList<>();
        bobrequests.add(request1);
        bobrequests.add(request2);

        List<SpecialRequest> timrequests = new ArrayList<>();
        timrequests.add(request3);

        List<SpecialRequest> lindarequests = new ArrayList<>();
        lindarequests.add(request4);

        populator.put("bob",bobrequests);
        populator.put("tim",timrequests);
        populator.put("linda",lindarequests);

        return populator;
    }

    @Test
    public void getRequests() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Populate bobList with the UUIDs of the SpecialRequests
        List<UUID> bobList = new ArrayList<>();
        for (SpecialRequest s: map.get("bob")) {
            bobList.add(s.getRequestID());
        }
        //Proved equal since both are subsets of each other
        assertTrue(bobList.containsAll(srm.getRequests("bob")));
        assertTrue(srm.getRequests("bob").containsAll(bobList));
        //Populate timList with single UUID of the SpecialRequest
        List<UUID> timList = new ArrayList<>();
        for (SpecialRequest s: map.get("tim")) {
            timList.add(s.getRequestID());
        }
        assertEquals(1, timList.size());
        //Proved equal since both are subsets of each other
        assertTrue(timList.containsAll(srm.getRequests("tim")));
        assertTrue(srm.getRequests("tim").containsAll(timList));
    }

    @Test
    public void getRequestingUsers() {
        SpecialRequestManager srm = new SpecialRequestManager(this.generateRequestMap());
        List<String> lst = Arrays.asList("bob", "tim", "linda");
        assertEquals(lst, srm.getRequestingUsers());
    }

    @Test
    public void setRequests() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Set for a new user
        assertFalse(srm.getRequestingUsers().contains("toby"));
        SpecialRequest r1 = new SpecialRequest("toby", "Misc.", "I am scared of clowns", false);
        List<SpecialRequest> l1 = new ArrayList<>();
        l1.add(r1);
        srm.setRequests("toby", l1);
        assertEquals(l1.get(0).getRequestID(), srm.getRequests("toby").get(0));
        //Set for existing user. Use bob's SpecialRequests for toby
        assertTrue(srm.getRequestingUsers().contains("toby"));
        List<SpecialRequest> l2 = srm.getRequestsList("bob");
        srm.setRequests("toby", l2);
        assertNotEquals(l1.get(0).getRequestID(), srm.getRequests("toby").get(0));
        assertEquals(srm.getRequests("bob"), srm.getRequests("toby"));
    }

    @Test
    public void addRequest() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Set for a new user
        assertFalse(srm.getRequestingUsers().contains("tammy"));
        srm.addRequest("tammy", "Physical", "I have Technophobia", false);
        assertEquals(1, srm.getRequests("tammy").size());
        //Set for an existing user
        srm.addRequest("tammy", "Physical", "I have social anxiety", false);
        assertEquals(2, srm.getRequests("tammy").size());
    }

    @Test
    public void removeRequest() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Ensure the correct initial SpecialRequests are in place
        assertEquals(2, srm.getRequests("bob").size());
        //Remove all SpecialRequests
        UUID r1 = map.get("bob").get(0).getRequestID();
        UUID r2 = map.get("bob").get(1).getRequestID();
        srm.removeRequest(r1);
        assertEquals(1, srm.getRequests("bob").size());
        srm.removeRequest(r2);
        assertEquals(0, srm.getRequests("bob").size());
        //Remove non-existent SpecialRequest. The number of items in each of the
        //SpecialRequest lists should remain the same since no changes is performed.
        UUID r3 = UUID.randomUUID();
        srm.removeRequest(r3);
        assertEquals(0, srm.getRequests("bob").size());
        assertEquals(1, srm.getRequests("linda").size());
        assertEquals(1, srm.getRequests("tim").size());
    }

    @Test
    public void addressRequest() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Check current status
        SpecialRequest r1 = srm.getRequestsList("tim").get(0);
        assertFalse(r1.isAddressed());
        //Change to true
        srm.addressRequest(r1.getRequestID());
        assertTrue(r1.isAddressed());
    }


    @Test
    public void getPendingRequests() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Ensures all UUIDs correspond to Pending Requests
        for (UUID id: srm.getPendingRequests()) {
            SpecialRequest sr = srm.getRequestFromID(id);
            assertFalse(sr.isAddressed());
        }
        assertEquals(3, srm.getPendingRequests().size());
        //Addressing a Request reduces size of list of Pending Requests
        srm.addressRequest(srm.getPendingRequests().get(0));
        assertEquals(2, srm.getPendingRequests().size());
    }

    @Test
    public void getAddressedRequests() {
        HashMap<String, List<SpecialRequest>> map = this.generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Ensures all UUIDs correspond to Addressed Requests
        for (UUID id: srm.getAddressedRequests()) {
            SpecialRequest sr = srm.getRequestFromID(id);
            assertTrue(sr.isAddressed());
        }
        assertEquals(1, srm.getAddressedRequests().size());
        //Addressing a Request increases size of list of Addressed Requests
        srm.addressRequest(srm.getPendingRequests().get(0));
        assertEquals(2, srm.getAddressedRequests().size());
    }



}