package csc.zerofoureightnine.conferencemanager.gateway.sql;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;
import java.util.List;

public class MessageSQLGatewayTest {
    private static SQLConfiguration config;

    @BeforeClass
    public static void setup() {
        config = new SQLConfiguration("testfiles/db/data");
    }

    @Test
    public void SaveAndLoadTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(config);

        String key = "absolutely.";
        MessageData expectedData = new MessageData();
        expectedData.setContent("Hello.");
        expectedData.setTimeSent(Instant.ofEpochMilli(1024));
        expectedData.setSender("Bob");
        expectedData.getRecipients().add("John");
        mSqlGateway.save(key, expectedData);
        MessageData data = mSqlGateway.load(key);

        assertEquals(expectedData, data);
    }

    @Test
    public void retrieveByFieldTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(config);
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
