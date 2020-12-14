package csc.zerofoureightnine.conferencemanager.datacollection;


import java.util.Map;

/**
 * Data Presenter.
 */
public class DataPresenter {

    private RuntimeDataHolder runtimeDataHolder;
    private StoredDataGetter storedDataGetter;


    /**
     * Instantiates the Data Presenter
     *
     * @param runtimeDataHolder {@link RuntimeDataHolder} stores the data collected during runtime (i.e. each user session)
     * @param storedDataGetter  {@link RuntimeDataHolder} stores the data collected from the entire dataset - i.e. all users
     */
    public DataPresenter(RuntimeDataHolder runtimeDataHolder, StoredDataGetter storedDataGetter) {
        this.runtimeDataHolder = runtimeDataHolder;
        this.storedDataGetter = storedDataGetter;
    }

    /**
     * Get the runtime stats for this session (user login)
     *
     * @param username username
     * @return The formatted stats
     */
    public String getRuntimeStats(String username) {
        Map<RuntimeStat, Integer> runtimeData = runtimeDataHolder.getMap();
        StringBuilder stringBuilder = new StringBuilder();
        for (RuntimeStat key : RuntimeStat.values()) {
            stringBuilder.append(key).append(": ").append(runtimeData.get(key)).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    /**
     * Get the stored data stats
     *
     * @param username username to get specific stats for
     * @return The formatted stats
     */
    public String getStoredData(String username) {
        StringBuilder stringBuilder = new StringBuilder();
        String specialMarker = "---------";
        stringBuilder.append(specialMarker).append("Message Statistics").append(specialMarker).append(System.lineSeparator());
        stringBuilder.append("Total number of messages sent: ").append(storedDataGetter.getTotalMessagesSent()).append(System.lineSeparator());
        stringBuilder.append("Messages in your inbox: ").append(storedDataGetter.getRetrieveMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Unread messages: ").append(storedDataGetter.getUnreadMessages(username)).append(System.lineSeparator());
        stringBuilder.append("Read messages: ").append(storedDataGetter.getReadMessages(username)).append(System.lineSeparator());


        stringBuilder.append(specialMarker).append("Event Statistics").append(specialMarker).append(System.lineSeparator());
        stringBuilder.append("Total Events: ").append(storedDataGetter.getTotalEvents()).append(System.lineSeparator());
        stringBuilder.append("Total Parties: ").append(storedDataGetter.getTotalParties()).append(System.lineSeparator());
        stringBuilder.append("Total Single-Speaker Events: ").append(storedDataGetter.getTotalSingles()).append(System.lineSeparator());
        stringBuilder.append("Total Multi-Speaker Events: ").append(storedDataGetter.getTotalMulties()).append(System.lineSeparator());
        stringBuilder.append("Most Popular Event Type: ").append(storedDataGetter.getMostPopularEventType()).append(System.lineSeparator());
        stringBuilder.append("Average Capacity: ").append(storedDataGetter.getAverageCapacity()).append(System.lineSeparator());

        stringBuilder.append(specialMarker).append("User-Related Statistics").append(specialMarker).append(System.lineSeparator());
        stringBuilder.append("Total Number of Users: ").append(storedDataGetter.getTotalusers()).append(System.lineSeparator());
        stringBuilder.append("Total Number of Requests: ").append(storedDataGetter.getTotalSpecialRequests()).append(System.lineSeparator());
        stringBuilder.append("Total Number of Addressed Requests: ").append(storedDataGetter.getTotalAddressedRequests()).append(System.lineSeparator());
        stringBuilder.append("Total Number of Pending Requests: ").append(storedDataGetter.getTotalPendingRequests()).append(System.lineSeparator());
        stringBuilder.append("Percentage of Addressed Requests: ").append(storedDataGetter.getTotalAddressedRequests()).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
