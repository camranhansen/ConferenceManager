package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.RuntimeData;

import java.util.EnumMap;
import java.util.Map;

public class RuntimeDataHolder {

    private EnumMap<RuntimeStats, Integer> statMap;
    private PersistentMap<String, RuntimeData> pMap;
    private String username;

    public RuntimeDataHolder(String username, PersistentMap<String, RuntimeData> pMap) {
        this.statMap = new EnumMap<>(RuntimeStats.class);
        for (RuntimeStats x: RuntimeStats.values()) { statMap.put(x, 0); }
        this.pMap = pMap;
        this.username = username;
    }

    void incrementStat(RuntimeStats runtimeStat) {
        statMap.merge(runtimeStat, 1, Integer::sum);
        pMap.get(username).setStatValue(runtimeStat, statMap.get(runtimeStat));
    }

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
