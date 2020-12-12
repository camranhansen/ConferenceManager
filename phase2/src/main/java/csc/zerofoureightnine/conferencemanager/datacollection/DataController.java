package csc.zerofoureightnine.conferencemanager.datacollection;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.HashMap;
import java.util.List;

public class DataController {
    private HashMap<String, String> inputMap;

    public DataController(HashMap<String, String> inputMap) {
        this.inputMap = inputMap;
    }

    public HashMap<String, String> getInputMap() {
        return inputMap;
    }


    public int viewMethod(String username, String input, List<TopicPresentable> selectableOptions) {
        return 0;
    }

}
