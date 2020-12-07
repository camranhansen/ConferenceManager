package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequest;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestManager;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SpecialRequestManagerTest {

    private DummyPersistentMap<UUID, SpecialRequestData> generateRequestMap(){
        DummyPersistentMap<UUID, SpecialRequestData> populator = new DummyPersistentMap<>();

        SpecialRequestData request1 = new SpecialRequestData("bob","Dietary","I can't eat water or air", false);
        SpecialRequestData request2 = new SpecialRequestData("bob","Physical","My brazilian macaw, Pringles, must attend events with me", false);
        SpecialRequestData request3 = new SpecialRequestData("tim","Dietary","I only eat orange foods", false);
        SpecialRequestData request4 = new SpecialRequestData("linda","Physical","I can only sit at chairs 90 degrees or less", true);

        populator.put(request1.getId(), request1);
        populator.put(request2.getId(), request2);
        populator.put(request3.getId(), request3);
        populator.put(request4.getId(), request4);
        return populator;
    }

    @Test
    public void getRequests() {
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Populate bobList with the UUIDs of the SpecialRequests
        List<UUID> bobList = new ArrayList<>();
        for (SpecialRequestData s: map.loadForSame("requestingUser", "bob")) {
            bobList.add(s.getId());
        }
        //Proved equal since both are subsets of each other
        assertTrue(bobList.containsAll(srm.getRequests("bob")));
        assertTrue(srm.getRequests("bob").containsAll(bobList));
        //Populate timList with single UUID of the SpecialRequestData
        List<UUID> timList = new ArrayList<>();
        for (SpecialRequestData s: map.loadForSame("requestingUser", "tim")) {
            timList.add(s.getId());
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
        assertTrue(lst.containsAll(srm.getRequestingUsers()));
        assertTrue(srm.getRequestingUsers().containsAll(lst));
    }

    @Test
    public void addRequest() {
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
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
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Ensure the correct initial SpecialRequests are in place
        List<UUID> boblist = srm.getRequests("bob");
        assertEquals(2, boblist.size());
        //Remove all SpecialRequests
        UUID r1 = boblist.get(0);
        UUID r2 = boblist.get(1);
        srm.removeRequest(r1);
        assertEquals(1, srm.getRequests("bob").size());
        srm.removeRequest(r2);
        assertEquals(0, srm.getRequests("bob").size());
        //Remove non-existent SpecialRequests. The number of items in each of the
        //SpecialRequest lists should remain the same since no changes is performed.
        UUID r3 = UUID.randomUUID();
        srm.removeRequest(r3);
        assertEquals(0, srm.getRequests("bob").size());
        assertEquals(1, srm.getRequests("linda").size());
        assertEquals(1, srm.getRequests("tim").size());
    }

    @Test
    public void addressRequest() {
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Check current status
        SpecialRequestData r1 = srm.getRequestDataAsList("tim").get(0);
        assertFalse(r1.getAddressed());
        //Change to true
        srm.addressRequest(r1.getId());
        assertTrue(r1.getAddressed());
    }


    @Test
    public void getPendingRequests() {
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Ensures all UUIDs correspond to Pending Requests
        for (UUID id: srm.getPendingRequests()) {
            SpecialRequestData sr = srm.getRequestFromID(id);
            assertFalse(sr.getAddressed());
        }
        assertEquals(3, srm.getPendingRequests().size());
        //Addressing a Request reduces size of list of Pending Requests
        srm.addressRequest(srm.getPendingRequests().get(0));
        assertEquals(2, srm.getPendingRequests().size());
    }

    @Test
    public void getAddressedRequests() {
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        //Ensures all UUIDs correspond to Addressed Requests
        for (UUID id: srm.getAddressedRequests()) {
            SpecialRequestData sr = srm.getRequestFromID(id);
            assertTrue(sr.getAddressed());
        }
        assertEquals(1, srm.getAddressedRequests().size());
        //Addressing a Request increases size of list of Addressed Requests
        srm.addressRequest(srm.getPendingRequests().get(0));
        assertEquals(2, srm.getAddressedRequests().size());
    }

    @Test
    public void getRequestDetails(){
        DummyPersistentMap<UUID, SpecialRequestData> map = generateRequestMap();
        SpecialRequestManager srm = new SpecialRequestManager(map);
        List<UUID> timList = srm.getRequests("tim");
        UUID requestID = timList.get(0);
        LinkedHashMap<String, String> timData = srm.getRequestDetails(requestID);
        assertEquals(requestID.toString(), timData.get("id"));
        assertEquals("tim", timData.get("requestingUser"));
        assertEquals("Dietary", timData.get("header"));
        assertEquals("I only eat orange foods", timData.get("description"));
        assertEquals("false", timData.get("addressed"));
    }

}