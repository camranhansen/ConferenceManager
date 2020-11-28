package csc.zerofoureightnine.conferencemanager.gateway.sql;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

public class MessageSQLGatewayTests {
    SQLMapping mapping;

    @BeforeClass
    public void setup() {
        mapping = new SQLMapping();
    }

    @Test
    public void SaveAndLoadTest() {
        MessageSQLGateway mSqlGateway = new MessageSQLGateway(mapping);
        String key = "absolutely.";
        MessageData expectedData = new MessageData();
        mSqlGateway.save(key, expectedData);
        MessageData data = mSqlGateway.load(key);

        assertEquals(expectedData, data);
    }
}
