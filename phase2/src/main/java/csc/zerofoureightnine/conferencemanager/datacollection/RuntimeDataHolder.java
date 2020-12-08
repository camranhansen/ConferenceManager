package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.RuntimeData;

public class RuntimeDataHolder {

    private PersistentMap<String, RuntimeData> pMap;
    private String username;

    public RuntimeDataHolder(String username, PersistentMap<String, RuntimeData> pMap) {
        this.pMap = pMap;
        this.username = username;
    }

    public void incrementStat(RuntimeStats runtimeStat) {
        usernameCheck();
        int runtimeStatValue = pMap.get(username).getStatValue(runtimeStat) + 1;
        pMap.get(username).setStatValue(runtimeStat, runtimeStatValue);
    }

    public PersistentMap<String, RuntimeData> getMap() { return pMap; }

    private void usernameCheck(){
        if (!pMap.containsKey(username)){
        RuntimeData runtimeData = new RuntimeData();
        pMap.put(username, runtimeData);
        }
    }
}
