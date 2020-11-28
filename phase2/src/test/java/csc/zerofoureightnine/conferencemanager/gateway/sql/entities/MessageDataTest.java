package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import static org.junit.Assert.*;

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
}
