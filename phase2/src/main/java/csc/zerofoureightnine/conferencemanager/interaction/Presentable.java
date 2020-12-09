package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;

public interface Presentable { //Presenter
    /**
     * An identifier for this presenter.
     * This is displayed to the user while listing as a child option of another {@see MenuNode}.
     * This cannot be null or empty.
     * @return A string that is displayed to the user when listing.
     */
    String getIdentifier();

    /**
     * The prompt the user recieves when landing on the {@see MenuNode} associated with this presenter.
     * The prompt cannot be null or empty.
     * @return A string containing the prompt for the user.
     */
    String getPrompt();

    /**
     * The retry message. If there is no retry message (null or empty), nothing will be displayed.
     * @return A {@link String} querying the user to retry, or nothing.
     */
    String getRetryMessage();

    /**
     * A final message that is displayed to the user on the action completion.
     * May be null or empty, in which nothing is displayed.
     * @return A {@link String} representing a message to indicate a successful {@link Action} was performed.
     */
    String getCompleteMessage();

    /**
     * An informative {@link String} formatted that displays potential options for the user currently on this {@see MenuNode}.
     * May return null, in which case nothing is displayed when landing on this node.
     * @param options presentables associated and in the same order as the possible selections of {@see MenuNode}'s to traverse to.
     * @return The formatted {@link String} with all options displayed to the user or nothing.
     */
    String getPresentation(List<Presentable> options);
}
