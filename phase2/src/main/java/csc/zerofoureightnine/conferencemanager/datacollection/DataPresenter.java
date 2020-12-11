package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.RuntimeData;

public class DataPresenter {

    private String username;
    private RuntimeDataHolder runtimeDataHolder;
    private StoredDataGetter storedDataGetter;

    public DataPresenter(RuntimeDataHolder runtimeDataHolder, StoredDataGetter storedDataGetter) {
        this.runtimeDataHolder = runtimeDataHolder;
        this.storedDataGetter = storedDataGetter;
    }

    public String getRuntimeStats() {
        RuntimeData runtimeData = runtimeDataHolder.getMap().get(username);
        StringBuilder stringBuilder = new StringBuilder();
        for (RuntimeStats key : RuntimeStats.values()) {
            stringBuilder.append(key).append(": ").append(runtimeData.getStatValue(key)).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public String getStoredData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Messages in inbox: ").append(storedDataGetter.getRetrieveMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Unread messages: ").append(storedDataGetter.getUnreadMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Read messages: ").append(storedDataGetter.getReadMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Average event capacity: ").append(storedDataGetter.getAverageCapacity()).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
