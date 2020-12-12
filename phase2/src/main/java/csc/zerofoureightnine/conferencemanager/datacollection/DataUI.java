package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

import java.util.ArrayList;
import java.util.List;


/**
 * User Interface for viewing data collected both during runtime, and also across sessions
 */
public class DataUI implements UISection, SessionObserver {

    private DataPresenter dataPresenter;
    private DataController dataController;
    private String username;
    private List<MenuNode> entryPoints;

    /**
     * Create a new DataUI
     *
     * @param dataPresenter  the {@link DataPresenter}. Primarily responsible for all features in this UI section
     * @param dataController the {@link DataController}.
     */
    public DataUI(DataPresenter dataPresenter, DataController dataController) {
        this.dataPresenter = dataPresenter;
        this.dataController = dataController;
        generateSeeRuntimeStatsNodes();
    }

    /**
     * Gets the entry nodes for this data layer
     *
     * @return the entry nodes. Currently returns two nodes, one for stored data, one for runtime-collected data
     */
    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();
        generateSeeRuntimeStatsNodes();
        generateSeeStoredDataStatsNodes();
        return entryPoints;
    }

    /**
     * Get the section listing
     *
     * @return the section listing
     */
    @Override
    public String getSectionListing() {
        return "Statistics collection";
    }

    /**
     * Notifies the DataUI if someone new has logged in or out. Modifies the username stored in DataUI.
     *
     * @param username    the newly logged or out user, represented by their username.
     * @param permissions a list of the user's {@link Permission}.
     * @param loggedIn    true if someone is logged in, false if otherwise.
     */
    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        this.username = username;
    }


    //Helper methods to generate nodes...
    private void generateSeeRuntimeStatsNodes() {
        String seqTitle = "View Runtime Stats";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, dataController.getInputMap());
        seq.addStep(null, dataPresenter::getRuntimeStats, null, null);
        MenuNode.MenuNodeBuilder showRuntimeDataNode = new MenuNode.MenuNodeBuilder(seqTitle, dataController::viewMethod);
        entryPoints.add(seq.build(showRuntimeDataNode.build(), Permission.VIEW_SELF_STATISTICS));
    }

    private void generateSeeStoredDataStatsNodes() {
        String seqTitle = "View Conference Stats";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, dataController.getInputMap());
        seq.addStep(null, dataPresenter::getStoredData, null, null);
        MenuNode.MenuNodeBuilder showRuntimeDataNode = new MenuNode.MenuNodeBuilder(seqTitle, dataController::viewMethod);
        entryPoints.add(seq.build(showRuntimeDataNode.build(), Permission.VIEW_ALL_STATISTICS));
    }

}
