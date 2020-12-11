package csc.zerofoureightnine.conferencemanager.interaction.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

public class ExitAction implements Action {
    private Set<ExitObserver> observers = new HashSet<>();

    private void onExit() {
        for (ExitObserver exitObserver : observers) {
            exitObserver.exitting();
        }
    }

    @Override
    public int complete(String username, String input, List<TopicPresentable> selectableOptions) {
        onExit();
        return 0;
    }

    public void addObserver(ExitObserver exitObserver) {
        observers.add(exitObserver);
    }

    public void removeObserver(ExitObserver exitObserver) {
        observers.remove(exitObserver);
    }
}
