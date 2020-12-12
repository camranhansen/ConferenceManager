package csc.zerofoureightnine.conferencemanager.datacollection;

import java.time.Instant;
import java.util.EnumMap;

public class RuntimeDataHolder implements RuntimeStatModifier {

    private EnumMap<RuntimeStats, Integer> statMap;
    private String username = "guest";
    private Instant lastTimeCalled;

    /**
     * Instantiates the RuntimeDataHolder.
     */
    public RuntimeDataHolder() {
        this.statMap = new EnumMap<>(RuntimeStats.class);
        resetMap();
    }

    private void resetMap() {
        for (RuntimeStats stat : RuntimeStats.values()
        ) {
            statMap.put(stat, 0);
        }
    }

    /**
     * Increments the value of a given RuntimeStat.
     *
     * @param runtimeStat The RuntimeStat to be incremented.
     */
    @Override
    public void incrementStat(RuntimeStats runtimeStat) {
        Integer runtimeStatValue = statMap.get(runtimeStat) + 1;
        statMap.put(runtimeStat, runtimeStatValue);
        System.out.println(statMap.toString());
    }

    /**
     * Returns the PersistentMap pMap.
     */
    public EnumMap<RuntimeStats, Integer> getMap() {
        return statMap; }

    /**
     * Returns the stored username.
     */
    public String getUsername() { return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username to be stored.
     */
    public void setUsername(String username) {
        this.username = username;
        resetMap();
    }


}
