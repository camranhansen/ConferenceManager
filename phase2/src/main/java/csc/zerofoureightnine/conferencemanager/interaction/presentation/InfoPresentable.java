package csc.zerofoureightnine.conferencemanager.interaction.presentation;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.Action;

import java.util.List;

public interface InfoPresentable {
    /**
     * An informative {@link String} formatted that displays potential options for
     * the user currently on this {@see MenuNode}. The options list will be
     * identical to the list passed to
     * {@link Action#complete(String, String, List, java.util.Map)} except instead
     * of {@link MenuNode}, it will be their respective {@link TopicPresentable}s.
     * 
     * @param options presentables associated and in the same order as the possible
     *                selections of {@see MenuNode}'s to traverse to.
     * @return The formatted {@link String} with all options displayed to the user.
     */
    String getInfo(String username, List<TopicPresentable> options);
}
