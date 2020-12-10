package csc.zerofoureightnine.conferencemanager.interaction.general;

import csc.zerofoureightnine.conferencemanager.interaction.GeneralMenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Completable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Nameable;

/**
 * A {@link Presentable} that doesn't have a prompt, retry, completion, or presentation
 * message. By not having a prompt, {@link GeneralMenuNode} will skip asking for input.
 */
public class AutoPresenter implements Nameable, Completable {
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
    public String getCompleteMessage(MenuNode menuNode) {
        return completionMsg;
    }
    
}
