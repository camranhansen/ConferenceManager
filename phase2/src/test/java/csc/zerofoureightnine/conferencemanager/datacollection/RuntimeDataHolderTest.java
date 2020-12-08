package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.gateway.DummyPersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.RuntimeData;
import junit.framework.TestCase;

public class RuntimeDataHolderTest extends TestCase {

    public void testIncrementStat() {
        DummyPersistentMap<String, RuntimeData> dummyPersistentMap = new DummyPersistentMap<>();
        String username = "bob";
        RuntimeData runtimeData = new RuntimeData();
        dummyPersistentMap.put(username, runtimeData);
        RuntimeDataHolder data = new RuntimeDataHolder(username, dummyPersistentMap);
        data.incrementStat(RuntimeStats.BAD_INPUT);
        data.incrementStat(RuntimeStats.BAD_INPUT);
        data.incrementStat(RuntimeStats.TIME_SPENT);
        assertEquals(2, data.getMap().get(username).getStatValue(RuntimeStats.BAD_INPUT));
        assertEquals(1, data.getMap().get(username).getStatValue(RuntimeStats.TIME_SPENT));
    }

}