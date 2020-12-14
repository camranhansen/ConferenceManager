package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;

import java.util.List;
import java.util.Map;

/**
 * Responsible for input validation for message {@link csc.zerofoureightnine.conferencemanager.interaction.control.Action} and {@link csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable}
 * Methods here must implement the {@link csc.zerofoureightnine.conferencemanager.interaction.control.Validatable} interface.
 * Part of the presenter layer in Model-View-Presenter, and is a controller in clean architecture
 */
public class MessageInputValidator {

    private MessageManager messageManager;
    private Map<String, String> inputMap;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private EventManager eventManager;
    private final List<String> selectedMessageIDs;

    /**
     * Method for creating a MessageInputValidator.
     *
     * @param messageManager     {@link MessageManager}
     * @param inputMap           map representing user input in the message menu. used for multi-step processes that require input from previous steps.
     * @param userManager        {@link UserManager}
     * @param permissionManager  {@link csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager}
     * @param eventManager       {@link EventManager}
     * @param selectedMessageIDs Representing the selected message ID's, i.e. the ones the user is currently seeing. Used to check if valid selection.
     */
    public MessageInputValidator(MessageManager messageManager, Map<String, String> inputMap, UserManager userManager, PermissionManager permissionManager, EventManager eventManager, List<String> selectedMessageIDs) {
        this.messageManager = messageManager;
        this.inputMap = inputMap;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.eventManager = eventManager;
        this.selectedMessageIDs = selectedMessageIDs;
    }

    /**
     * Returns true if the inputted recipient for a message is an existing user.
     *
     * @param input   the current user's input
     * @param options the options available to user, may be null
     * @return A boolean stating whether or not the inputted recipient is valid
     */
    public boolean isValidMessageRecipient(String input, List<TopicPresentable> options) {
        return userManager.userExists(input);
    }


    /**
     * Returns true if the inputted content for a message is valid,
     * in other words, not empty.
     *
     * @param input   the current user's input
     * @param options the options available to user, may be null
     * @return A boolean stating whether or not the inputted content is valid
     */
    public boolean isValidContent(String input, List<TopicPresentable> options) {
        return !input.isEmpty();
    }


    /**
     * Returns true if the inputted event id is valid.
     *
     * @param input   the current user's input
     * @param options the options available to user, may be null
     * @return A boolean stating whether or not the inputted event id is valid
     */
    public boolean isValidEventIdForSending(String input, List<TopicPresentable> options) {
        return eventManager.eventExists(input);
    }


    /**
     * Checks if a numerical selection from the group of messages being presented by {@link MessagePresenter}.
     *
     * @param input the current user's input
     * @param opts  the options available to user, may be null
     * @return A boolean stating whether or not the input number is within bounds of selectable messages.
     */
    public boolean isValidMessageSelectionFromGroup(String input, List<TopicPresentable> opts) {
        return input.matches("^[0-9]+$") &&
                Integer.parseInt(input) < selectedMessageIDs.size();
    }
}
