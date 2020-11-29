package csc.zerofoureightnine.conferencemanager.specialrequests;

import csc.zerofoureightnine.conferencemanager.users.SpecialRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpecialRequestManagerTest {

    private HashMap<String, List<SpecialRequest>> generateRequestMap(){
        HashMap<String,List<SpecialRequest>> populator = new HashMap<>();
        SpecialRequest request1 = new SpecialRequest("bob","Dietary","I can't eat water or air", false);
        SpecialRequest request2 = new SpecialRequest("bob","Physical","My brazilian maccaw Springles must attend csc.zerofoureightnine.conferencemanager.events with me", false);
        SpecialRequest request3 = new SpecialRequest("tim","Dietary","I only eat orange foods", false);
        SpecialRequest request4 = new SpecialRequest("Linda","Physical","I can only sit at chairs 90 degrees or less", true);
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
    public void getUserRequests() {

        //Assert that bob's requests are equal to premade requests
    }

    @Test
    public void getRequestingUsers() {
        //assert return is bob, tim, linda
    }

    @Test
    public void getPendingRequests() {
        //assert returns request1, 2, 3
    }

    @Test
    public void getAddressedRequests() {
        //assert returns request4
    }

    @Test
    public void addRequest() {
        //add new request. assert before does not contain, assert later it does
    }

    @Test
    public void removeRequest() {
        //assert before contains, assert after does not
    }

    @Test
    public void addressRequest() {
        //assert that pendingRequests contains a request, and adressedRequests does not. Do method, then assert the opposite.
    }
}