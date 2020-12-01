package csc.zerofoureightnine.conferencemanager.gateway.sql;

import static org.junit.Assert.assertEquals;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;
import java.util.List;

public class MessageSQLGatewayTest {
    private static SQLMapping mapping;

    @BeforeClass
    public static void setup() {
        mapping = new SQLMapping();
    }

    @Test
    public void SaveAndLoadTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        UserData recipient = new UserData();
        Session sess = mapping.getFactory().openSession();
        Transaction tx = sess.beginTransaction();
        sess.save(recipient);
        tx.commit();
        sess.close();

        String key = "absolutely.";
        MessageData expectedData = new MessageData();
        expectedData.setContent("Hello.");
        expectedData.setTimeSent(Instant.ofEpochMilli(1024));
        expectedData.setSender("Bob");
        expectedData.addRecipient(recipient);
        mSqlGateway.save(key, expectedData);
        MessageData data = mSqlGateway.load(key);

        assertEquals(expectedData, data);
    }

    @Test
    public void retrieveByFieldTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        MessageData expectedData = new MessageData();
        UserData recipient = new UserData();
        Session sess = mapping.getFactory().openSession();
        Transaction tx = sess.beginTransaction();
        sess.save(recipient);
        tx.commit();
        sess.close();
        expectedData.addRecipient(recipient);
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
