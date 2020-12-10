package csc.zerofoureightnine.conferencemanager.interaction.general;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.Presentable;

public class AutoPresenter implements Presentable {
    private final String identifier;
    private final String completionMsg;
    public AutoPresenter(String identifier, String completionMessage) {
        this.identifier = identifier;
        this.completionMsg = completionMessage;
    }
    

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getPrompt() {
        return null;
    }

    @Override
    public String getRetryMessage() {
        return null;
    }

    @Override
    public String getCompleteMessage() {
        return completionMsg;
    }

    @Override
    public String getPresentation(List<Presentable> options) {
        return null;
    }
    
}
