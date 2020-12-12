package csc.zerofoureightnine.conferencemanager.datacollection;

/**
 * Interface to denote a class which will increment statistics during runtime
 * See {@link RuntimeDataHolder} for an example of implementation
 */
public interface RuntimeStatModifier {

    /**
     * Increment the relevant statistic by 1.
     *
     * @param runtimeStat the relevant statistic.
     */
    void incrementStat(RuntimeStat runtimeStat);
}
