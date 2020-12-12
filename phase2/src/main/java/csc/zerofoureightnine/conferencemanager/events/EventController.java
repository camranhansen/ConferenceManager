package csc.zerofoureightnine.conferencemanager.events;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import org.hibernate.type.IntegerType;
import org.jvnet.staxex.BinaryText;

import javax.print.DocFlavor;
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
                           HashMap<String, String> inputMap) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.inputMap = inputMap;
    }

    public int editCapacity(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.editCapacity(inputMap.get("event_id)"), Integer.parseInt(inputMap.get("capacity")));
        return 0;
    }

    public int createEvent(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.createEvent(
                Arrays.asList(inputMap.get("speakers").split(", ")),
                eventManager.parseTime(inputMap.get("day"), inputMap.get("hour")),
                inputMap.get("name"),
                inputMap.get("room"),
                Integer.parseInt(inputMap.get("capacity")));
        return 0;
    }

    public int deleteEvent(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.deleteEvent(inputMap.get("event_id"));
        return 0;
    }

    public int enrollSelf(String username, String input, List<TopicPresentable> selectableOptions) {
        eventManager.enrollUser(inputMap.get("event_id"),username);
        return 0;
    }

    public int enrollOther(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.enrollUser(inputMap.get("event_id"),inputMap.get("target"));
        return 0;
    }

    public int dropSelf(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.dropUser(inputMap.get("event_id"), username);
        return 0;
    }

    public int dropOther(String username, String input, List<TopicPresentable> selectableOptions){
        eventManager.dropUser(inputMap.get("event_id"), inputMap.get("target"));
        return 0;
    }

    public Map<String, String> getInputMap() {
        return inputMap;
    }

    public int viewMethod(String username, String input, List<TopicPresentable> selectableOptions){
        return 0;
    }

    public boolean isValidHour(String hour, List<TopicPresentable> options) {
        return hour.matches("[0-9]|[0-1][0-9]|[2][0-4]");
    }

    public boolean isValidDay(String day, List<TopicPresentable> options) {
        return day.matches("[0-2][0-9]|[3][0-1]|[1-9]");
    }

    public boolean isValidCapacity(String capacity, List<TopicPresentable> options) {

        return capacity.matches("[0-9]+");
    }

    public boolean isValidEditCapacity(String capacity, List<TopicPresentable> options){
        return capacity.matches("[0-9]+")&&eventManager.getParticipants(inputMap.get("event_id")).size() > Integer.parseInt(capacity);
    }

    public boolean isEnrollableEventID(String id, List<TopicPresentable> options){
        return eventManager.eventExists(id)&&eventManager.isEventFull(inputMap.get(id));
    }

    public boolean isValidRoom(String room, List<TopicPresentable> options) {
        Instant timeslot = eventManager.parseTime(inputMap.get("day"), inputMap.get("hour"));
        return eventManager.checkRoom(timeslot, room);
    }

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

    public boolean isValidID(String id, List<TopicPresentable> options) {
        return eventManager.eventExists(id);
    }
    public boolean isValidUsername(String input, List<TopicPresentable> options){
        return userManager.userExists(input);
    }
}
