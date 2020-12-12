package csc.zerofoureightnine.conferencemanager.datacollection;

import junit.framework.TestCase;

public class RuntimeDataHolderTest extends TestCase {

    public void foo() {
        System.out.println("Pending");
    }

//    public void testIncrementStat() {
//        DummyPersistentMap<String, RuntimeData> dummyPersistentMap = new DummyPersistentMap<>();
//        String username = "bob";
//        RuntimeData runtimeData = new RuntimeData();
//        dummyPersistentMap.put(username, runtimeData);
//        RuntimeDataHolder data = new RuntimeDataHolder(dummyPersistentMap);
//        data.setUsername(username);
//        data.incrementStat(RuntimeStats.BAD_INPUT);
//        data.incrementStat(RuntimeStats.BAD_INPUT);
//        assertEquals((Integer) 2, data.getMap().get(username).getStatValue(RuntimeStats.BAD_INPUT));
//    }

}