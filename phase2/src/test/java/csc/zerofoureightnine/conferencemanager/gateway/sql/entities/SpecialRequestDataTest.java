package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class SpecialRequestDataTest {

    @Test
    public void setId() {
        UUID u1 = UUID.randomUUID();
        SpecialRequestData request1 = new SpecialRequestData("bob","Dietary","I can't eat water or air", false);
        assertNotEquals(u1, request1.getId());
        request1.setId(u1);
        assertEquals(u1, request1.getId());
        SpecialRequestData request2 = new SpecialRequestData();
        assertNotEquals(request1.getId(), request2.getId());
    }

    @Test
    public void setRequestingUser() {
        SpecialRequestData request1 = new SpecialRequestData("bob","Dietary","I can't eat water or air", false);
        assertEquals("bob", request1.getRequestingUser());
        SpecialRequestData request2 = new SpecialRequestData();
        assertNull(request2.getRequestingUser());
        request2.setRequestingUser("linda");
        assertEquals("linda", request2.getRequestingUser());

    }

    @Test
    public void getHeader() {
        SpecialRequestData request1 = new SpecialRequestData("bob","Dietary","I can't eat water or air", false);
        assertEquals("Dietary", request1.getHeader());
    }

    @Test
    public void setHeader() {
        SpecialRequestData request1 = new SpecialRequestData();
        assertNull(request1.getHeader());
        request1.setHeader("Physical");
        assertEquals("Physical", request1.getHeader());
    }

    @Test
    public void getDescription() {
        SpecialRequestData request1 = new SpecialRequestData("bob","Dietary","I can't eat water or air", false);
        assertEquals("I can't eat water or air", request1.getDescription());
    }

    @Test
    public void setDescription() {
        SpecialRequestData request1 = new SpecialRequestData();
        assertNull(request1.getDescription());
        request1.setDescription("doesn't really matter what goes here");
        assertEquals("doesn't really matter what goes here", request1.getDescription());
    }

    @Test
    public void getAddressed() {
        SpecialRequestData request1 = new SpecialRequestData("bob","Dietary","I can't eat water or air", false);
        assertFalse(request1.getAddressed());
    }

    @Test
    public void setAddressed() {
        SpecialRequestData request1 = new SpecialRequestData();
        request1.setAddressed(true);
        assertTrue(request1.getAddressed());
        request1.setAddressed(false);
        assertFalse(request1.getAddressed());
    }
}