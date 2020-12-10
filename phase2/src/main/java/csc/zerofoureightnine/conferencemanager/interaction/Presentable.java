package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;

public interface Presentable { //Presenter
    /**
     * An identifier for this presenter.
     * This is displayed to the user while listing as a child option of another {@link MenuNode}.
     * This cannot be null or empty.
     * @return A string that is displayed to the user when listing.
     */
    String getIdentifier();

    /**
     * The prompt the user recieves when landing on the {@link MenuNode} associated with this presenter.
     * If the prompt is null or empty, input will not be taken, and 
     * {@link Action#complete(String, String, List, java.util.Map)} will be called directly.
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
     * The list will be identical to the list passed to {@link Action#complete(String, String, List, java.util.Map)} except instead of
     * {@link MenuNode}, it will be their respective {@link Presentable}s.
     * @param options presentables associated and in the same order as the possible selections of {@see MenuNode}'s to traverse to.
     * @return The formatted {@link String} with all options displayed to the user or nothing.
     */
    String getPresentation(List<Presentable> options);
}
