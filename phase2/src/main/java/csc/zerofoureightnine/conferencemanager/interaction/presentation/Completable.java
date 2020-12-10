package csc.zerofoureightnine.conferencemanager.interaction.presentation;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.Action;

public interface Completable { // Presenter
    /**
     * A final message that is displayed to the user on the action completion.
     * @param nextNode the next node the user is moving to.
     * @return A {@link String} representing a message to indicate an {@link Action}
     *         was performed.
     */
    String getCompleteMessage(MenuNode nextNode);
}
