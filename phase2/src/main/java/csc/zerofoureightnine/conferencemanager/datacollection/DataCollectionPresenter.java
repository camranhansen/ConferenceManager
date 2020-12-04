package csc.zerofoureightnine.conferencemanager.datacollection;

public class DataCollectionPresenter {
    public void printStatsPage(RuntimeDataHolder runtimeDataHolder, StoredDataGetter storedDataGetter){
        System.out.println(runtimeDataHolder.toString() + storedDataGetter.toString());
    }

}
