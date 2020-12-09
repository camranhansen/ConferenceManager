package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;

public class OptionPresenter implements Presentable {
    private final String identifier;
    private final String prompt = "Select by entering the associated integer";
    private final String retryMsg = "Please enter a valid option";

    public OptionPresenter(String listing) {
        this.identifier = listing;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public String getPresentation(List<Presentable> options) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            sb.append(i + ") ");
            Presentable presentable = options.get(i);
            sb.append(presentable == null ? "No back available." : options.get(i).getIdentifier());
            sb.append("\n");
        }
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
