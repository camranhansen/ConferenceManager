package csc.zerofoureightnine.conferencemanager.interaction.presentation;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;

public interface TopicPresentable {  //Presenter
    /**
     * An identifier for this presenter. This is displayed to the user while listing
     * as a child option of another {@link MenuNode}.
     *
     * @return A string that is displayed to the user when listing.
     */
    String getIdentifier();

}
