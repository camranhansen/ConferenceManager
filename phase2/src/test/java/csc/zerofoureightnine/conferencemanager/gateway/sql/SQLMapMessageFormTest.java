package csc.zerofoureightnine.conferencemanager.gateway.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.HashMap;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

public class SQLMapMessageFormTest {
    private static SQLConfiguration config;
    
    private static SQLMap<String, MessageData> sqlMap;

    @BeforeClass
    public static void setup() {
        config = new SQLConfiguration("testfiles/db/data");
        sqlMap = new SQLMap<>(config, MessageData.class);
    }

    @After
    public void cleanUp() {
        sqlMap.clear();
    }
    
    @Test
    public void saveAndLoadTest() {
        MessageData expected = new MessageData();
        String key = "a good, reasonable, key.";
        expected.setContent("Howdy");
        expected.setSender("Johny");
        expected.setTimeSent(Instant.ofEpochMilli(10223));
        expected.addRecipients("U");
        
        sqlMap.save(key, expected);

        assertEquals(expected, sqlMap.get(key));
    }

    @Test
    public void sizeTest() {
        int expected = 100;
        for (int i = 0; i < expected; i++) {
            MessageData msgData = new MessageData();
            sqlMap.save(String.valueOf(i), msgData);
        }

        assertEquals(expected, sqlMap.size());
    }

    @Test
    public void isEmptyTest() {
        assertTrue("Nothing stored, therefore, should be empty.", sqlMap.isEmpty());
    }

    @Test
    public void containsKeyTest() {
        int amount = 100;
        for (int i = 0; i < amount; i++) {
            MessageData msgData = new MessageData();
            sqlMap.save(String.valueOf(i), msgData);
        }

        for (int i = 0; i < amount; i++) {
            assertTrue("Message data with id " + i + " should exist.", sqlMap.containsKey(String.valueOf(i)));
        }
    }

    @Test
    public void containsValueTest() {
        int amount = 100;
        MessageData[] data = new MessageData[amount];
        for (int i = 0; i < amount; i++) {
            MessageData msgData = new MessageData();
            data[i] = msgData;
            sqlMap.save(String.valueOf(i), msgData);
        }

        for (int i = 0; i < amount; i++) {
            assertTrue("Message data with id " + i + " should exist by value.", sqlMap.containsValue(data[i]));
        }
    }

    @Test
    public void getTest() {
        int amount = 100;
        MessageData[] data = new MessageData[amount];
        for (int i = 0; i < amount; i++) {
            MessageData msgData = new MessageData();
            data[i] = msgData;
            sqlMap.save(String.valueOf(i), msgData);
        }

        for (int i = 0; i < amount; i++) {
            assertEquals(data[i], sqlMap.get(String.valueOf(i)));
        }
    }

    @Test
    public void putTest() {
        int amount = 100;
        MessageData[] data = new MessageData[amount];
        for (int i = 0; i < amount; i++) {
            MessageData msgData = new MessageData();
            data[i] = msgData;
            sqlMap.put(String.valueOf(i), msgData);
        }

        for (int i = 0; i < amount; i++) {
            assertEquals(data[i], sqlMap.load(String.valueOf(i)));
        }
    }

    @Test
    public void removeTest() {
        int amount = 100;
        MessageData[] data = new MessageData[amount];
        for (int i = 0; i < amount; i++) {
            MessageData msgData = new MessageData();
            data[i] = msgData;
            sqlMap.save(String.valueOf(i), msgData);
        }

        for (int i = 0; i < amount/2; i++) {
            sqlMap.remove(String.valueOf(i));
        }

        for (int i = amount/2; i < amount; i++) {
            assertEquals(data[i], sqlMap.load(String.valueOf(i)));
        }
    }

    @Test
    public void putAllTest() {
        int amount = 100;
        HashMap<String, MessageData> dataMap = new HashMap<>();

        for (int i = 0; i < amount; i++) {
            MessageData msgData = new MessageData();
            dataMap.put(String.valueOf(i), msgData);
        }
        sqlMap.putAll(dataMap);

        for (MessageData data : dataMap.values()) {
            assertTrue("Should contain data with ID: " + data.getId(), sqlMap.containsValue(data));
        }
    }
}
