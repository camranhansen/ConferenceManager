package csc.zerofoureightnine.conferencemanager.gateway.csv;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
@Deprecated
public class EventCSVGatewayTest {
    EventCSVGateway eGateway;
    private static final String testFilePath = "testfiles/event_data.csv";
    private EventManager eventManager;
    private static DummyPersistentMap<String, EventData> pMap;

    @Before
    public void prepareGateway() throws NoSuchFieldException, IllegalAccessException {
        eGateway = new EventCSVGateway();
        Field filePath = eGateway.getClass().getSuperclass().getDeclaredField("filePath");
        filePath.setAccessible(true);
        filePath.set(eGateway, testFilePath);
        eventManager = new EventManager(pMap);
        // TODO: implement changes.
        /*
        eventManager.createEvent("John", Instant.ofEpochSecond(1000), "CN Tower", "103", 10);
        eventManager.createEvent("John", Instant.ofEpochSecond(2000), "CN Tower", "103", 10);
        eventManager.createEvent("John", Instant.ofEpochSecond(3000), "CN Tower", "103", 10);
        */
    }

    @Test
    public void testSaveEvents() throws IOException {
        eGateway.saveEvents(eventManager);
        assertEquals(Files.readAllLines(Paths.get(getExpected(eGateway.getFilePath()))),
                Files.readAllLines(Paths.get(eGateway.getFilePath())));
    }

    @Test
    public void testReadEvents() throws IOException {
        EventManager cleanEventManager = new EventManager(pMap);
        eGateway.saveEvents(eventManager);
        eGateway.clearAll();
        eGateway.readEventsFromGateway(cleanEventManager);
        assertEquals(eventManager, cleanEventManager);
    }

    private String getExpected(String path) {
        return path.replace(".csv", "") + "_expected.csv";
    }

    @AfterClass
    public static void cleanGeneratedFiles() {
        File testFile = new File(testFilePath);
        testFile.delete();
    }
}
