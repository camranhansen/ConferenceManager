package datacollection;

public class DataCollectionPresenter {

    public void printStatsPage(RuntimeDataHolder, StoredDataGetter){
        System.out.println(StoredDataGetter.toString() + RuntimeDataHolder.toString());
    }
}
