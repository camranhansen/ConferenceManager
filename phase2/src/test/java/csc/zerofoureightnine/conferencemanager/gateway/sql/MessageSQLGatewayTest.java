package csc.zerofoureightnine.conferencemanager.gateway.sql;

import static org.junit.Assert.assertEquals;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;
import csc.zerofoureightnine.conferencemanager.users.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.time.Instant;

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
}
