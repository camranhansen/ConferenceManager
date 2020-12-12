package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.session.SessionObserver;

import java.util.ArrayList;
import java.util.List;

public class DataUI implements UISection, SessionObserver {

    private DataPresenter dataPresenter;
    private DataController dataController;
    private String username;
    private List<MenuNode> entryPoints;


    public DataUI(DataPresenter dataPresenter, DataController dataController) {
        this.dataPresenter = dataPresenter;
        this.dataController = dataController;
        generateSeeRuntimeStatsNodes();
    }


    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();
        generateSeeRuntimeStatsNodes();
        return entryPoints;
    }

    @Override
    public String getSectionListing() {
        return "Statistics collection";
    }

    @Override
    public void authenticationStateChanged(String username, List<Permission> permissions, boolean loggedIn) {
        this.username = username;
    }

    private void generateSeeRuntimeStatsNodes() {
        String seqTitle = "View Runtime Stats";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, dataController.getInputMap());
        seq.addStep(null, dataPresenter::getRuntimeStats, null, null);
        MenuNode.MenuNodeBuilder showRuntimeDataNode = new MenuNode.MenuNodeBuilder(seqTitle, dataController::viewMethod);
        entryPoints.add(seq.build(showRuntimeDataNode.build(), Permission.VIEW_SELF_STATISTICS));
    }

}
