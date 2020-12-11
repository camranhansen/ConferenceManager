package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.RuntimeData;

import java.time.Duration;
import java.time.Instant;

public class RuntimeDataHolder {

    private PersistentMap<String, RuntimeData> pMap;
    private String username;
    private Instant lastTimeCalled;

    /**
     * Constructor
     * @param pMap
     */
    public RuntimeDataHolder(PersistentMap<String, RuntimeData> pMap) {
        this.pMap = pMap;
    }

    public void incrementStat(RuntimeStats runtimeStat) {
        usernameCheck();
        if (runtimeStat == RuntimeStats.TIME_SPENT) {
            Instant time = pMap.get(username).getInstantValue(runtimeStat);
            Instant oldTime = pMap.get(username).getInstantValue(RuntimeStats.BAD_INPUT);
            Instant currentTime = Instant.now();
            Duration timeDifference = Duration.between(currentTime, lastTimeCalled);
            pMap.get(username).setInstantValue(oldTime.plus(timeDifference));
            lastTimeCalled = currentTime;
        }
        Integer runtimeStatValue = pMap.get(username).getStatValue(runtimeStat) + 1;
        pMap.get(username).setStatValue(runtimeStat, runtimeStatValue);
    }

    public PersistentMap<String, RuntimeData> getMap() { return pMap; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    private void usernameCheck(){
        if (!pMap.containsKey(username)){
        RuntimeData runtimeData = new RuntimeData();
        pMap.put(username, runtimeData);
        }
    }
}
