package csc.zerofoureightnine.conferencemanager.interaction.general;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.completePresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.InfoPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.ReattemptPromptPresentable;

/**
 * A general {@link Presentable} that lists the children of the
 * {@link GeneralMenuNode}. Prompts user for an integer to select one of the
 * listed children options. Does not have a completion message.
 */
public class OptionPresenter implements TopicPresentable, PromptPresentable, InfoPresentable, ReattemptPromptPresentable, completePresentable {
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
    public String getInfo(List<TopicPresentable> options) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            sb.append(i + ") ");
            TopicPresentable presentable = options.get(i);
            if (i == 1) {
                sb.append(presentable == null ? "Cannot go back." : "back (" + presentable.getIdentifier() + ")");
            } else {
                sb.append(presentable.getIdentifier());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getRetryMessage() {
        return retryMsg;
    }

    @Override
    public String getCompleteMessage(TopicPresentable nameable) {
        return "";
    }
}
