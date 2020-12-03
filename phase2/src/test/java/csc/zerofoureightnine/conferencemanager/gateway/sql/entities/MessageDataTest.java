package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MessageDataTest {

    @Test
    public void testAddingRecipient() {
        MessageData messageData = new MessageData();
        messageData.getRecipients().add("Bob");
        assertTrue("Should contain the added userdata representing the recipient.", messageData.getRecipients().contains("Bob"));
    }

    @Test
    public void testMessageContentData() {
        MessageData messageData = new MessageData();
        messageData.setContent("Hey there.");
        assertEquals("Hey there.", messageData.getContent());
    }

    @Test
    public void testMessageTimeSentData() {
        MessageData messageData = new MessageData();
        Instant timeSent = Instant.ofEpochMilli(1024);
        messageData.setTimeSent(timeSent);

        assertEquals(timeSent, messageData.getTimeSent());
    }

    @Test
    public void testSenderData() {
        MessageData messageData = new MessageData();
        messageData.setSender("Johnathan");
        
        assertEquals("Johnathan", messageData.getSender());
    }
}
