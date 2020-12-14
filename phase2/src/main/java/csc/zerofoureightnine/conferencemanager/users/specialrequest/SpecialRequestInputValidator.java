package csc.zerofoureightnine.conferencemanager.users.specialrequest;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.List;
import java.util.UUID;

/**
 * Responsible for input validation for special request {@link csc.zerofoureightnine.conferencemanager.interaction.control.Action} and {@link csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable}
 * Methods here must implement the {@link csc.zerofoureightnine.conferencemanager.interaction.control.Validatable} interface.
 * Part of the presenter layer in Model-View-Presenter, and is a controller in clean architecture
 */
public class SpecialRequestInputValidator {
    private SpecialRequestManager requestManager;


    /**
     * Create a SpecialRequestInputValidator
     *
     * @param requestManager a {@link SpecialRequestManager}
     */
    public SpecialRequestInputValidator(SpecialRequestManager requestManager) {
        this.requestManager = requestManager;
    }

    /**
     * Returns whether the given requestID is valid or not.
     *
     * @param id      requestID
     * @param options the options available to user
     * @return true if {@code id} exists, otherwise return false
     */
    public boolean isValidID(String id, List<TopicPresentable> options) {
        return id.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
                && (this.requestManager.getPendingRequests().contains(UUID.fromString(id)) ||
                this.requestManager.getAddressedRequests().contains(UUID.fromString(id)));
    }
}
