package csc.zerofoureightnine.conferencemanager.gateway.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

public class SQLMapMessageFormTest {
    private static SQLConfiguration config;
    
    private static PersistentMap<String, MessageData> sqlMap;

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
    public void sizeCacheTest() {
        int expectedBase = 100;
        for (int i = 0; i < expectedBase; i++) {
            MessageData msgData = new MessageData();
            sqlMap.save(String.valueOf(i), msgData);
        }

        assertEquals(expectedBase, sqlMap.size());
        assertEquals(expectedBase, sqlMap.size());

        sqlMap.save("special", new MessageData());
        assertEquals(expectedBase + 1, sqlMap.size());
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

    @Test
    public void searchTest() {
        MessageData[] jData = new MessageData[50];
        MessageData[] bData = new MessageData[50];
        for (int i = 0; i < jData.length; i++) {
            jData[i] = new MessageData();
            jData[i].setSender("John" + i);
            sqlMap.save("j" + i, jData[i]);
            
            bData[i] = new MessageData();
            bData[i].setSender("Bob" + i);
            sqlMap.save("b" + i, bData[i]);
        }

        List<MessageData> jActual = sqlMap.search("sender", "J%");
        List<MessageData> bActual = sqlMap.search("sender", "B%");
        
        for (int i = 0; i < jData.length; i++) {
            assertTrue("List should contain data with id: j" + i, jActual.contains(jData[i]));
            assertFalse("List should not contain data with id: b" + i, jActual.contains(bData[i]));

            assertTrue("List should contain data with id: b" + i, bActual.contains(bData[i]));
            assertFalse("List should not contain data with id: j" + i, bActual.contains(jData[i]));
        }
    }

    @Test
    public void loadForSameTest() {
        MessageData[] aData = new MessageData[50];
        MessageData[] bData = new MessageData[50];
        for (int i = 0; i < aData.length; i++) {
            aData[i] = new MessageData();
            aData[i].setSender("John");
            sqlMap.save("a" + i, aData[i]);
            
            bData[i] = new MessageData();
            bData[i].setSender("Johny");
            sqlMap.save("b" + i, bData[i]);
        }

        List<MessageData> aActual = sqlMap.loadForSame("sender", "John");
        List<MessageData> bActual = sqlMap.loadForSame("sender", "Johny");
        
        for (int i = 0; i < aData.length; i++) {
            assertTrue("List should contain data with id: a" + i, aActual.contains(aData[i]));
            assertFalse("List should not contain data with id: b" + i, aActual.contains(bData[i]));

            assertTrue("List should contain data with id: b" + i, bActual.contains(bData[i]));
            assertFalse("List should not contain data with id: j" + i, bActual.contains(aData[i]));
        }
    }

    @Test
    public void loadInCollectionTest() {
        MessageData[] aData = new MessageData[50];
        MessageData[] bData = new MessageData[50];

        for (int i = 0; i < aData.length; i++) {
            aData[i] = new MessageData();
            aData[i].addRecipients("John", "Bob");
            sqlMap.save("a" + i, aData[i]);
            
            bData[i] = new MessageData();
            bData[i].addRecipients("Johny");
            sqlMap.save("b" + i, bData[i]);
        }

        List<MessageData> aActual = sqlMap.loadInCollection("recipients", "John");
        List<MessageData> bActual = sqlMap.loadInCollection("recipients", "Johny");

        for (int i = 0; i < aData.length; i++) {
            assertTrue("List should contain data with id: a" + i, aActual.contains(aData[i]));
            assertFalse("List should not contain data with id: b" + i, aActual.contains(bData[i]));

            assertTrue("List should contain data with id: b" + i, bActual.contains(bData[i]));
            assertFalse("List should not contain data with id: j" + i, bActual.contains(aData[i]));
        }
    }
}
