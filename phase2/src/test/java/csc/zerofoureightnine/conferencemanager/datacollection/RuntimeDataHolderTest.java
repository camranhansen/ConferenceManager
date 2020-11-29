package csc.zerofoureightnine.conferencemanager.datacollection;

import junit.framework.TestCase;

public class RuntimeDataHolderTest extends TestCase {

    public void testIncrementStat() {
        RuntimeDataHolder data = new RuntimeDataHolder();
        data.incrementStat(RuntimeStats.BAD_INPUT);
        data.incrementStat(RuntimeStats.BAD_INPUT);
        data.incrementStat(RuntimeStats.TIME_SPENT);
        assertEquals(2, data.getStatValue(RuntimeStats.BAD_INPUT));
        assertEquals(1, data.getStatValue(RuntimeStats.TIME_SPENT));
    }

    public void testTestToString() {
        RuntimeDataHolder data = new RuntimeDataHolder();
        String s = "BAD_INPUT: 0" + System.lineSeparator() + "MENUS_VISITED: 0" + System.lineSeparator() + "LINES_INPUTTED: 0" + System.lineSeparator() + "MOST_INVOKED_PERMISSION: 0" + System.lineSeparator() + "TIME_SPENT: 0" + System.lineSeparator();
        assertEquals(s, data.toString());
    }
}