package csc.zerofoureightnine.conferencemanager.gateway.sql;

import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;

public class MessageSQLGatewayTest {
    private static SQLConfiguration mapping;

    @BeforeClass
    public static void setup() {
        mapping = new SQLConfiguration();
    }

    @Test
    public void SaveAndLoadTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        String key = "absolutely.";
        MessageData expectedData = new MessageData();
        expectedData.setContent("Hello.");
        expectedData.setTimeSent(Instant.ofEpochMilli(1024));
        expectedData.setSender("Bob");
        expectedData.getRecipients().add("John");
        mSqlGateway.save(key, expectedData);
        assertTrue(mSqlGateway.containsKey(key));
        MessageData data = mSqlGateway.load(key);
        assertEquals(expectedData, data);

    }

    @Test
    public void removeTest(){
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        MessageData md = new MessageData();
        String key = "1";
        md.setContent("Hello");
        md.setTimeSent(Instant.ofEpochMilli(1024));
        md.setSender("sender");
        md.getRecipients().add("recipient");
        mSqlGateway.save(key, md);
        assertTrue(mSqlGateway.containsKey(key));
        mSqlGateway.remove(key);
        assertFalse(mSqlGateway.containsKey(key));

    }

    @Test
    public void changeDataTest(){
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        MessageData md = new MessageData();
        String key = "1";
        md.setContent("Hello");
        md.setTimeSent(Instant.ofEpochMilli(1024));
        md.setSender("sender");
        md.getRecipients().add("recipient");
        mSqlGateway.save(key, md);
        md.setContent("Hi");
        assertEquals("Hi", md.getContent());
        mSqlGateway.remove(key);
        mSqlGateway.save(key, md);
        MessageData md1 = mSqlGateway.load(key);
        assertEquals(md.getContent(), md1.getContent());

        md.setSender("sender2");
        mSqlGateway.remove(key);
        mSqlGateway.save(key, md);
        MessageData md2 = mSqlGateway.load(key);
        assertEquals(md.getSender(), md2.getSender());

        md.setArchived(true);
        mSqlGateway.remove(key);
        mSqlGateway.save(key, md);
        MessageData md3 = mSqlGateway.load(key);
        assertTrue(md3.getArchived());
        assertEquals(md.getArchived(), md3.getArchived());


    }

    @Test
    public void retrieveByFieldTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        MessageData expectedData = new MessageData();
        expectedData.getRecipients().add("Better John");
        String key = "A better key than the last one.";
        expectedData.setContent("Better content than the last one.");
        expectedData.setTimeSent(Instant.ofEpochMilli(1025));
        expectedData.setSender("Better Bob");
        mSqlGateway.save(key, expectedData);

        List<MessageData> data = mSqlGateway.retrieveByField("sender", "Better Bob");
        assertEquals(1, data.size());
        assertEquals(expectedData, data.get(0));
    }
}
