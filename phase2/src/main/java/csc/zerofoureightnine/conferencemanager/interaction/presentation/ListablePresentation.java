package csc.zerofoureightnine.conferencemanager.interaction.presentation;

import java.util.List;

public interface ListablePresentation {
    /**
     * An informative {@link String} formatted that displays potential options for
     * the user currently on this {@see MenuNode}. The options list will be
     * identical to the list passed to
     * {@link Action#complete(String, String, List, java.util.Map)} except instead
     * of {@link MenuNode}, it will be their respective {@link NameablePresentation}s.
     * 
     * @param options presentables associated and in the same order as the possible
     *                selections of {@see MenuNode}'s to traverse to.
     * @return The formatted {@link String} with all options displayed to the user.
     */
    String getOptionsPresentation(List<NameablePresentation> options);
}
