package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

import java.util.EnumMap;
import java.util.List;

public class RuntimeDataHolder implements RuntimeStatModifier, SessionObserver {

    private EnumMap<RuntimeStat, Integer> statMap;

    /**
     * Instantiates the RuntimeDataHolder.
     */
    public RuntimeDataHolder() {
        this.statMap = new EnumMap<>(RuntimeStat.class);
        resetMap();
    }

    //private, so no need for javadoc. but if you are curious.
    //this method will reset the map for the new user, by setting all values to 0.
    private void resetMap() {
        for (RuntimeStat stat : RuntimeStat.values()
        ) {
            statMap.put(stat, 0);
        }
    }

    /**
     * Increments the value of a given {@link RuntimeStat}.
     *
     * @param runtimeStat The RuntimeStat to be incremented.
     */
    @Override
    public void incrementStat(RuntimeStat runtimeStat) {
        Integer runtimeStatValue = statMap.get(runtimeStat) + 1;
        statMap.put(runtimeStat, runtimeStatValue);
    }

    /**
     * Get the representation of this user's runtime statistics
     *
     * @return an EnumMap with key {@link RuntimeStat}, and value {@link Integer},
     * representing number of occurences.
     */
    public EnumMap<RuntimeStat, Integer> getMap() {
        return statMap;
    }


    /**
     * Change the authentication state.
     *
     * @param username    the newly logged or out user, represented by their username.
     * @param permissions a list of the user's {@link Permission}.
     * @param loggedIn    true if someone is logged in, false if otherwise.
     */
    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        resetMap();
    }
}
