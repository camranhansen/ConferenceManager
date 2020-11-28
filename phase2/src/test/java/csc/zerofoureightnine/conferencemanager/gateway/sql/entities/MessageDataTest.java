package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

public class MessageDataTest {

    public void testAddingRecipient() {
        MessageData messageData = new MessageData();
        UserData userData = new UserData();
        messageData.addRecipient(userData);
        
        assertTrue("Should contain the added userdata representing the recipient.", messageData.getRecipients().contains(userData));
    }

    @Test
    public void testBidirectionalityForRecipients() {
        MessageData messageData = new MessageData();
        UserData userData = new UserData();
        messageData.addRecipient(userData);

        assertTrue("Message should contain the added userdata representing the recipient", messageData.getRecipients().contains(userData));
        assertTrue("userData should contain the message in its inbox.", userData.getMessages().contains(messageData));
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
