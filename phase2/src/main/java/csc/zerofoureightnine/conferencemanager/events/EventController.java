package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventController {
    private EventManager eventManager;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private Map<String, String> inputMap;

    public EventController(EventManager eventManager,
                           UserManager userManager,
                           PermissionManager permissionManager,
                           Map<String, String> inputMap) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.inputMap = inputMap;
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
        eventManager.editCapacity(inputMap.get("event_id)"), Integer.parseInt(inputMap.get("capacity")));
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

    /**
     * Returns whether {@code hour} is a valid input
     * @param hour event hour
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code hour} is valid, false otherwise.
     */
    public boolean isValidHour(String hour, List<TopicPresentable> options) {
        return hour.matches("[0-9]|[0-1][0-9]|[2][0-4]");
    }

    /**
     * Returns whether {@code day} is a valid input
     * @param day event day
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code day} is valid, false otherwise.
     */
    public boolean isValidDay(String day, List<TopicPresentable> options) {
        return day.matches("[0-2][0-9]|[3][0-1]|[1-9]");
    }

    /**
     * Returns whether {@code capacity} is a valid input
     * @param capacity room capacity
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code capacity} is valid, false otherwise.
     */
    public boolean isValidCapacity(String capacity, List<TopicPresentable> options) {

        return capacity.matches("[0-9]+");
    }

    /**
     * Returns whether {@code capacity} is a valid input
     * @param capacity room capacity
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code capacity} is valid, false otherwise.
     */
    public boolean isValidEditCapacity(String capacity, List<TopicPresentable> options){
        return capacity.matches("[0-9]+")&&eventManager.getParticipants(inputMap.get("event_id")).size() > Integer.parseInt(capacity);
    }

    /**
     * Returns whether {@code id} is a valid input
     * @param id eventId
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code id} exists, false otherwise.
     */
    public boolean isEnrollableEventID(String id, List<TopicPresentable> options){
        return eventManager.eventExists(id)&&eventManager.isEventFull(inputMap.get(id));
    }

    /**
     * Returns whether {@code room} is a valid input
     * @param room event room
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code room} is valid at the time, false otherwise.
     */
    public boolean isValidRoom(String room, List<TopicPresentable> options) {
        Instant timeslot = eventManager.parseTime(inputMap.get("day"), inputMap.get("hour"));
        return eventManager.checkRoom(timeslot, room);
    }

    /**
     * Returns whether {@code unparsedSpeakers} is a valid input
     * @param unparsedSpeakers event speakers
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code unparsedSpeakers} are valid speakers, false otherwise.
     */
    public boolean isValidSpeakers(String unparsedSpeakers, List<TopicPresentable> options) {
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
     * @param id event id
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code id} exists, false otherwise.
     */
    public boolean isValidID(String id, List<TopicPresentable> options) {
        return eventManager.eventExists(id);
    }

    /**
     * Returns whether {@code input} is a valid input
     * @param input username
     * @param options the list of {@link TopicPresentable} options available at this node. In this case, there are none
     * @return true if {@code input} exists, false otherwise.
     */
    public boolean isValidUsername(String input, List<TopicPresentable> options){
        return userManager.userExists(input);
    }
}
