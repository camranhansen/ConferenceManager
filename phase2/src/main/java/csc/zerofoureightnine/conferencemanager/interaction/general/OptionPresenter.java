package csc.zerofoureightnine.conferencemanager.interaction.general;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.InfoPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.RetryPromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.List;

/**
 * A general Presentable that lists the children of the
 * {@link MenuNode}. Prompts user for an integer to select one of the
 * listed children options. Does not have a completion message.
 */
public class OptionPresenter implements TopicPresentable, PromptPresentable, InfoPresentable, RetryPromptPresentable {
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
    public String getPrompt(String username) {
        return prompt;
    }

    @Override
    public String getInfo(String username, List<TopicPresentable> options) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            sb.append(i + ") ");
            TopicPresentable presentable = options.get(i);
            if (presentable == null) {
                sb.append("[Unavailable]");
            } else if (i == 1) {
                if (presentable.getIdentifier().equals("Exit")) {
                    sb.append("(" + presentable.getIdentifier() + ")");
                } else {
                    sb.append("Back to (" + presentable.getIdentifier() + ")");

                }
            } else {
                sb.append(presentable.getIdentifier());
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public String getRetryMessage() {
        return retryMsg;
    }
}
