package Messaging;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class MessageTest {

    @Test(timeout = 50)
    public void constructMessageTest() {
        String[] attendees = {"att1", "att2", "att3"};
        Message m1 = new Message("org1", attendees, "Hey Guys!");
        Message m2 = new Message("potter", new String[]{"scooby"}, "Get your hogwarts acceptance yet?");
    }

    @Test(timeout = 50)
    public void messageGetContentTest() {
        Message m2 = new Message("potter", new String[]{"scooby"}, "Get your hogwarts acceptance yet?");
        assertEquals("Get your hogwarts acceptance yet?", m2.getContent());
    }

    @Test()
    public void messageGetTimeTest() {
        Message m2 = new Message("potter", new String[]{"scooby"}, "Get your hogwarts acceptance yet?");
        Instant time = m2.getTimeSent();
    }

    @Test(timeout = 50)
    public void messageGetSenderTest(){
        Message m2 = new Message("potter", new String[]{"scooby"}, "Get your hogwarts acceptance yet?");
        assertEquals("potter", m2.getSender());
    }

    @Test(timeout = 50)
    public void messageGetRecipientTest(){
        String[] attendees = {"att1", "att2", "att3"};
        Message m1 = new Message("org1", attendees, "Hey Guys!");
        assertEquals(new String[] {"att1", "att2", "att3"}, m1.getRecipients());
        assertEquals("att1", m1.getRecipients()[0]);
    }

    public void testMessageSender() {
        Message message = new Message("Genie", new String[]{"Aladdin"}, "Mr. Aladdin sir, what will your pleasure be?");
        assertEquals("Check sender.", "Genie", message.getSender());
    }

    public void testMessageReceiver() {
        Message message = new Message("Genie", new String[]{"Aladdin"}, "Mr. Aladdin sir, what will your pleasure be?");
        assertArrayEquals("Check receiver.", new String[]{"Aladdin"}, message.getRecipients());
    }

    public void testMessageContent() {
        Message message = new Message("Genie", new String[]{"Aladdin"}, "Mr. Aladdin sir, what will your pleasure be?");
        assertEquals("Check message content.", "Mr. Aladdin sir, what will your pleasure be?", message.getContent());
    }
}
