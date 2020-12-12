package csc.zerofoureightnine.conferencemanager.datacollection;


import java.util.EnumMap;

public class DataPresenter {

    private String username;
    private RuntimeDataHolder runtimeDataHolder;
    private StoredDataGetter storedDataGetter;

    public DataPresenter(RuntimeDataHolder runtimeDataHolder, StoredDataGetter storedDataGetter) {
        this.runtimeDataHolder = runtimeDataHolder;
        this.storedDataGetter = storedDataGetter;
    }

    public String getRuntimeStats() {
        EnumMap<RuntimeStats, Integer> runtimeData = runtimeDataHolder.getMap();
        StringBuilder stringBuilder = new StringBuilder();
        for (RuntimeStats key : RuntimeStats.values()) {
            stringBuilder.append(key).append(": ").append(runtimeData.get(key)).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public String getStoredData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Messages in inbox: ").append(storedDataGetter.getRetrieveMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Unread messages: ").append(storedDataGetter.getUnreadMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Read messages: ").append(storedDataGetter.getReadMessages(username)).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
