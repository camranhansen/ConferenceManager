package csc.zerofoureightnine.conferencemanager.datacollection;


import java.util.EnumMap;


public class DataPresenter {

    private RuntimeDataHolder runtimeDataHolder;
    private StoredDataGetter storedDataGetter;

    public DataPresenter(RuntimeDataHolder runtimeDataHolder, StoredDataGetter storedDataGetter) {
        this.runtimeDataHolder = runtimeDataHolder;
        this.storedDataGetter = storedDataGetter;
    }

    public String getRuntimeStats() {
        EnumMap<RuntimeStat, Integer> runtimeData = runtimeDataHolder.getMap();
        StringBuilder stringBuilder = new StringBuilder();
        for (RuntimeStat key : RuntimeStat.values()) {
            stringBuilder.append(key.getRenderableText()).append(": ").append(runtimeData.get(key)).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public String getStoredData(String username) {
        String fancyText = "--------------";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fancyText).append("Message Stats").append(fancyText).append(System.lineSeparator());
        stringBuilder.append("Total Messages Sent Overall: ").append(storedDataGetter.getTotalMessagesSent()).append(System.lineSeparator());
        stringBuilder.append("Messages in inbox: ").append(storedDataGetter.getRetrieveMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Unread messages: ").append(storedDataGetter.getUnreadMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Read messages: ").append(storedDataGetter.getReadMessages(username)).append(System.lineSeparator());
        stringBuilder.append(fancyText).append("Conference User Stats").append(fancyText).append(System.lineSeparator());
        stringBuilder.append("Total Number of Users: ").append(storedDataGetter.getTotalusers()).append(System.lineSeparator());
        stringBuilder.append(fancyText).append("Special Request Stats").append(fancyText).append(System.lineSeparator());
        stringBuilder.append("Total Requests: ").append(storedDataGetter.getTotalSpecialRequests()).append(System.lineSeparator());
        stringBuilder.append("Addressed Requests: ").append(storedDataGetter.getTotalAddressedRequests()).append(System.lineSeparator());
        stringBuilder.append("Pending Requests: ").append(storedDataGetter.getTotalPendingRequests()).append(System.lineSeparator());
        stringBuilder.append("Percentage of Requests Fulfilled: ").append(storedDataGetter.getPercentAdressedRequests()).append(System.lineSeparator());
//        stringBuilder.append(fancyText).append("").append(fancyText).append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
