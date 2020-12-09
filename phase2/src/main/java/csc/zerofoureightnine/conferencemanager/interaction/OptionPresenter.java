package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;

public class OptionPresenter implements Presentable {
    private final String listing;
    private final String prompt = "Select by entering the associated integer:";
    private final String retryMsg = "Please enter a valid option: ";

    public OptionPresenter(String listing) {
        this.listing = listing;
    }

    @Override
    public String getIdentifier() {
        return listing;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public String getPresentation(List<Presentable> options) {
        StringBuilder sb = new StringBuilder();
        for (Presentable presentable : options) {
            sb.append(presentable.getIdentifier());
            sb.append("\n");
        }
        sb.append(prompt + ":");
        return sb.toString();
    }

    @Override
    public String getRetryMessage() {
        return retryMsg;
    }

    @Override
    public String getCompleteMessage() {
        return null;
    }
    
}
