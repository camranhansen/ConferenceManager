package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Presenter-level (in MVP) class for holding methods that follow the {@link Action} interface, for modifying the model
 * responsible for events.
 */
public class EventActionHolder {
    private EventManager eventManager;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private Map<String, String> inputMap;

    /**
     * Initiates EventActions
     *
     * @param eventManager      associated {@link EventManager}
     * @param userManager       associated {@link UserManager}
     * @param permissionManager associated {@link PermissionManager}
     */
    public EventActionHolder(EventManager eventManager,
                             UserManager userManager,
                             PermissionManager permissionManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.inputMap = new HashMap<>();
    }

    /**
     * edit event capacity,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int editCapacity(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.editCapacity(inputMap.get("event_id"), Integer.parseInt(inputMap.get("capacity")));
        return 0;
    }

    /**
     * create an event,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int createEvent(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.createEvent(
                Arrays.asList(inputMap.get("speakers").split(", ")),
                eventManager.parseTime(inputMap.get("day"), inputMap.get("hour")),
                inputMap.get("name"),
                inputMap.get("room"),
                Integer.parseInt(inputMap.get("capacity")));
        return 0;
    }

    /**
     * delete an event
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int deleteEvent(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.deleteEvent(inputMap.get("event_id"));
        return 0;
    }

    /**
     * enroll in an event,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int enrollSelf(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.enrollUser(inputMap.get("event_id"),username);
        return 0;
    }

    /**
     * enroll another user in an event,
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int enrollOther(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.enrollUser(inputMap.get("event_id"),inputMap.get("target"));
        return 0;
    }

    /**
     * cancel self-enrollment to an event
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int dropSelf(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.dropUser(inputMap.get("event_id"), username);
        return 0;
    }

    /**
     * cancel enrollment of other users to an event
     * according to previous user input
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int dropOther(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.dropUser(inputMap.get("event_id"), inputMap.get("target"));
        return 0;
    }

    /**
     * Return the inputMap
     * @return a {@link Map} mapping strings to associating user inputs
     */
    public Map<String, String> getInputMap() {
        return inputMap;
    }

    /**
     * Returns back to main menu
     *
     * @param username          the username of the user who is doing the inputting
     * @param input             the input in this node. Not used in this case.
     * @param selectableOptions the list of {@link TopicPresentable} options available at this node.
     *                          In this case, there are none
     * @return an integer representing what node to go to after this action.
     * In this case, return 0, representing the main menu.
     */
    public int viewMethod(String username, String input, List<TopicPresentable> selectableOptions){
        return 0;
    }

}
