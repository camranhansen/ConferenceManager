package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MessageDataTest {

    public void testAddingRecipient() {
        MessageData messageData = new MessageData();
        UserData userData = new UserData();
        messageData.addRecipient(userData);
        assertTrue("Should contain the added userdata representing the recipient.", messageData.getRecipients().contains(userData));
    }

    @Test
    public void testBidirectionalityForRecipients() throws NoSuchFieldException, IllegalAccessException {
        MessageData messageData = new MessageData();
        Field fieldMSGID = MessageData.class.getDeclaredField("id");
        fieldMSGID.setAccessible(true);
        fieldMSGID.set(messageData, "ID");

        UserData userData = new UserData();
        Field fieldUserID = UserData.class.getDeclaredField("id");
        fieldUserID.setAccessible(true);
        fieldUserID.set(userData, 1);
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
