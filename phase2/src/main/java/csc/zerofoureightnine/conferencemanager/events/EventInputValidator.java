package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Responsible for input validation for event-related {@link csc.zerofoureightnine.conferencemanager.interaction.control.Action} and {@link csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable}
 * Methods here must implement the {@link csc.zerofoureightnine.conferencemanager.interaction.control.Validatable} interface.
 * Part of the presenter layer in Model-View-Presenter, and is a controller in clean architecture
 */
public class EventInputValidator {
    private EventManager eventManager;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private Map<String, String> inputMap;

    /**
     * Construct an EventInputValidator
     *
     * @param eventManager      {@link EventManager}
     * @param userManager       {@link UserManager}
     * @param permissionManager {@link PermissionManager}
     * @param inputMap          The input map for the user in the event menu. Used for verifying multi-step processes, such as event creation.
     */
    public EventInputValidator(EventManager eventManager, UserManager userManager, PermissionManager permissionManager, Map<String, String> inputMap) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.inputMap = inputMap;
    }

    /**
     * Returns whether {@code hour} is a valid input
     *
     * @param hour    event hour
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code hour} is valid, false otherwise.
     */
    public boolean isValidHour(String hour, List<TopicPresentable> options) {
        return hour.matches("[0-9]|[0-1][0-9]|[2][0-4]");
    }

    /**
     * Returns whether {@code day} is a valid input
     *
     * @param day     event day
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code day} is valid, false otherwise.
     */
    public boolean isValidDay(String day, List<TopicPresentable> options) {
        return day.matches("[0-2][0-9]|[3][0-1]|[1-9]");
    }

    /**
     * Returns whether {@code capacity} is a valid input
     *
     * @param capacity room capacity
     * @param options  the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code capacity} is valid, false otherwise.
     */
    public boolean isValidCapacity(String capacity, List<TopicPresentable> options) {

        return capacity.matches("[0-9]+");
    }

    /**
     * Returns whether {@code capacity} is a valid input
     *
     * @param capacity room capacity
     * @param options  the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code capacity} is valid, false otherwise.
     */
    public boolean isValidEditCapacity(String capacity, List<TopicPresentable> options) {
        return capacity.matches("[0-9]+") && eventManager.getParticipants(inputMap.get("event_id")).size() < Integer.parseInt(capacity);
    }

    /**
     * Returns whether {@code id} is a valid input
     *
     * @param id      eventId
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code id} exists, false otherwise.
     */
    public boolean isEnrollableEventID(String id, List<TopicPresentable> options) {
        return eventManager.eventExists(id) && !eventManager.isEventFull(id);
    }

    /**
     * Returns whether {@code room} is a valid input
     *
     * @param room    event room
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code room} is valid at the time, false otherwise.
     */
    public boolean isValidRoom(String room, List<TopicPresentable> options) {
        Instant timeslot = eventManager.parseTime(inputMap.get("day"), inputMap.get("hour"));
        return eventManager.checkRoom(timeslot, room);
    }

    /**
     * Returns whether {@code unparsedSpeakers} is a valid input
     *
     * @param unparsedSpeakers event speakers
     * @param options          the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code unparsedSpeakers} are valid speakers, false otherwise.
     */
    public boolean isValidSpeakers(String unparsedSpeakers, List<TopicPresentable> options) {
        if (unparsedSpeakers.isEmpty()) {
            return true;
        }
        List<String> speakers = Arrays.asList(unparsedSpeakers.split(", "));
        for (String speaker : speakers) {
            List<Permission> speakerPermissions = permissionManager.getPermissions(speaker);
            boolean isValid = speakerPermissions.containsAll(Template.SPEAKER.getPermissions());
            if (!isValid) {
                return false;
            }
        }
        Instant timeslot = eventManager.parseTime(inputMap.get("day"), inputMap.get("hour"));
        return !eventManager.checkConflictSpeaker(timeslot, speakers);

    }

    /**
     * Returns whether {@code id} is a valid input
     *
     * @param id      event id
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code id} exists, false otherwise.
     */
    public boolean isValidID(String id, List<TopicPresentable> options) {
        return eventManager.eventExists(id);
    }

    /**
     * Returns whether {@code input} is a valid input
     *
     * @param input   username
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code input} exists, false otherwise.
     */
    public boolean isValidUsername(String input, List<TopicPresentable> options) {
        return userManager.userExists(input);
    }

}
