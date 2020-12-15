package csc.zerofoureightnine.conferencemanager.interaction.control;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;

import java.util.List;

/**
 * A section of the UI. See {@link csc.zerofoureightnine.conferencemanager.datacollection.DataUI},
 * {@link csc.zerofoureightnine.conferencemanager.messaging.MessageUI},
 * {@link csc.zerofoureightnine.conferencemanager.events.EventUI}.
 * {@link csc.zerofoureightnine.conferencemanager.users.UserUI},
 * {@link csc.zerofoureightnine.conferencemanager.users.session.SessionUI}
 */
public interface UISection {

    /**
     * @return a list of entry menu nodes for the functionality of this controller.
     */
    List<MenuNode> getEntryMenuNodes();

    /**
     * @return the "listing" string for the section,
     * or in other words, what is rendered to the screen when
     * the user has a choice of going to this section of the UI
     */
    String getSectionListing();
}
