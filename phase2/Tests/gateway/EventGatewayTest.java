package gateway;

import events.EventManager;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import static org.junit.Assert.*;

public class EventGatewayTest {
    EventGateway eGateway;
    private static final String testFilePath = "testfiles/event_data.csv";
    private EventManager eventManager;

    @Before
    public void prepareGateway() throws NoSuchFieldException, IllegalAccessException {
        eGateway = new EventGateway();
        Field filePath = eGateway.getClass().getSuperclass().getDeclaredField("filePath");
        filePath.setAccessible(true);
        filePath.set(eGateway, testFilePath);
        eventManager = new EventManager();
        eventManager.createEvent("John", Instant.ofEpochSecond(1000), "CN Tower", "103", 10);
        eventManager.createEvent("John", Instant.ofEpochSecond(2000), "CN Tower", "103", 10);
        eventManager.createEvent("John", Instant.ofEpochSecond(3000), "CN Tower", "103", 10);
    }

    @Test
    public void testSaveEvents() throws IOException {
        eGateway.saveEvents(eventManager);
        assertEquals(Files.readAllLines(Paths.get(getExpected(eGateway.getFilePath()))),
                Files.readAllLines(Paths.get(eGateway.getFilePath())));
    }

    @Test
    public void testReadEvents() throws IOException {
        EventManager cleanEventManager = new EventManager();
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
