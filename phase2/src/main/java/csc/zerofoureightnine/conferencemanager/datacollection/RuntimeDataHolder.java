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
     * Instantiates the RuntimeDataHolder.
     * @param pMap A PersistentMap with usernames as keys and the corresponding RuntimeData as the values.
     */
    public RuntimeDataHolder(PersistentMap<String, RuntimeData> pMap) {
        this.pMap = pMap;
    }

    /**
     * Increments the value of a given RuntimeStat.
     * @param runtimeStat The RuntimeStat to be incremented.
     */
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

    /**
     * Returns the PersistentMap pMap.
     */
    public PersistentMap<String, RuntimeData> getMap() { return pMap; }

    /**
     * Returns the stored username.
     */
    public String getUsername() { return username; }

    /**
     * Sets the username.
     * @param username The username to be stored.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Checks if the given username exists in pMap. If not, creates a new RuntimeData entity and maps the username to it.
     */
    private void usernameCheck(){
        if (!pMap.containsKey(username)){
        RuntimeData runtimeData = new RuntimeData();
        pMap.put(username, runtimeData);
        }
    }
}
