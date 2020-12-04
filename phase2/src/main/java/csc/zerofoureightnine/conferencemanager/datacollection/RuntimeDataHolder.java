package csc.zerofoureightnine.conferencemanager.datacollection;

import java.util.EnumMap;
import java.util.Map;

public class RuntimeDataHolder {

    private EnumMap<RuntimeStats, Integer> statMap;

    public RuntimeDataHolder() {
        if (statMap == null) {
            this.statMap = new EnumMap<>(RuntimeStats.class);
            for (RuntimeStats x: RuntimeStats.values()) { statMap.put(x, 0); }
        }
    }

    void incrementStat(RuntimeStats runtimeStat) { statMap.merge(runtimeStat, 1, Integer::sum); }

    public int getStatValue (RuntimeStats runtimeStats) { return statMap.get(runtimeStats); } // For testing

    @Override
    //TODO get rid of this function.
    public String toString() {
        String s = "";
        for (RuntimeStats x: RuntimeStats.values()) {
            s += x.toString() + ": " + statMap.get(x).toString() + System.lineSeparator();
        }
        return s;
    }

    public Map<RuntimeStats, Integer> getStatMap() {
        return statMap;
    }

}
