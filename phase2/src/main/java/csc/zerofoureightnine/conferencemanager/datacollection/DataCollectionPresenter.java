package csc.zerofoureightnine.conferencemanager.datacollection;

public class DataCollectionPresenter {
    public void printStatsPage(RuntimeDataHolder RuntimeDataHolder, StoredDataGetter storedDataGetter){
        System.out.println( RuntimeDataHolder.toString() + storedDataGetter.toString());
    }

}
