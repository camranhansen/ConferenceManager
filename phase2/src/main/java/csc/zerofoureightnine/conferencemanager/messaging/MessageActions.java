package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.*;

/**
 * Presenter-level (in MVP) class for holding methods that follow the {@link Action} interface, for modifying the model
 * responsible for sending messages
 */
public class MessageActions {
    private MessageManager messageManager;
    private Map<String, String> inputMap;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private MessageMover messageMover;
    private EventManager eventManager;
    private final List<String> selectedMessageIDs = new ArrayList<>();

    /**
     * Creates a new MessageController
     *
     * @param messageManager    associated message manager
     * @param userManager       associated user manager
     * @param eventManager      associated event manager
     * @param permissionManager associated permission manager
     */
    public MessageActions(MessageManager messageManager, UserManager userManager, EventManager eventManager,
                          PermissionManager permissionManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.messageMover = new MessageMover(messageManager);
        this.eventManager = eventManager;
        this.inputMap = new HashMap<>();
    }

    /**
     * Sends a message to a single user.
     *
     * @param username current user
     * @param input user input, may be null
     * @param selectableOptions options available to user, may be null
     * @return zero for continuation purposes
     */
    public int messageSingleUser(String username, String input, List<TopicPresentable> selectableOptions) {
        messageManager.sendMessage(username, inputMap.get("content"), inputMap.get("to"));
        return 1;
    }

    /**
     * Sends a message to a group of users by template.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int messageGroup(String username, String input, List<TopicPresentable> options) {
        List<String> users = permissionManager.
                getUserByPermissionTemplate(Template.valueOf(inputMap.get("selected_group_value")));
        messageManager.sendMessage(username, inputMap.get("content"), users);
        return 1;
    }

    /**
     * Sends a message to all the events a user is hosting.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int messageAllEvents(String username, String input, List<TopicPresentable> options){
        List<String> eventIds = eventManager.getHostingEvents(username);
        List<String> users = getParticipants(eventIds);
        if (users.isEmpty()){
            return 0;
        }
        messageManager.sendMessage(username, inputMap.get("content"), users);
        return 1;
    }

    /**
     * Sends a message to a single event a user is hosting.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int messageSingleEvent(String username, String input, List<TopicPresentable> options){
        Collection<String> users = eventManager.getParticipants(inputMap.get("event_id"));
        if (users.isEmpty()){
            return 0;
        }
        messageManager.sendMessage(username, inputMap.get("content"), users);
        return 1;
    }

    /**
     * Moves a message from the current user's read inbox to their unread inbox.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int moveMessageToUnread(String username, String input, List<TopicPresentable> options){
        messageMover.moveReadToUnread(UUID.fromString(selectedMessageIDs.get(Integer.parseInt(input))), username);
        return 1;
    }

    /**
     * Moves a message from current user's unread inbox to their read inbox
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int moveMessageToRead(String username, String input, List<TopicPresentable> options){
        messageMover.moveUnreadToRead(UUID.fromString(selectedMessageIDs.get(Integer.parseInt(input))), username);
        return 1;
    }

    /**
     * Moves a message to the current user's archived inbox.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int moveMessageToArchive(String username, String input, List<TopicPresentable> options){
        messageMover.moveToArchived(UUID.fromString(selectedMessageIDs.get(Integer.parseInt(input))), username);
        return 1;
    }

    /**
     * Removes a message from the current user's archived inbox.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int removeMessageFromArchive(String username, String input, List<TopicPresentable> options){
        messageMover.removeFromArchived(UUID.fromString(selectedMessageIDs.get(Integer.parseInt(input))), username);
        return 1;
    }

    /**
     * Deletes a single message the current user has from another user.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int deleteSingleMessage(String username, String input, List<TopicPresentable> options){
        messageMover.deleteOneMessage(UUID.fromString(selectedMessageIDs.get(Integer.parseInt(input))), username);
        return 1;
    }

    /**
     * Deletes a conversation the current user has with another user.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int deleteSingleConversation(String username, String input, List<TopicPresentable> options){
        messageMover.deleteConversation(username, input);
        return 1;
    }

    /**
     * Clears all inboxes the current user has.
     *
     * @param username current user
     * @param input user input, may be null
     * @param options the options available to user, may be null
     * @return zero for continuation purposes
     */
    public int deleteAllInboxes(String username, String input, List<TopicPresentable> options){
        messageMover.clearAllInboxes(username);
        return 1;
    }



    /**
     * Returns the input map of this MessageController.
     *
     * @return a Map of String to String of the input
     */
    public Map<String, String> getInputMap() {
        return inputMap;
    }

    /**
     * Simply asks for input to confirm with the user.
     * @param username the current users username.
     * @param input the users input for this {@link MenuNode}.
     * @param topics the variety of children this {@link MenuNode} has.
     * @return a numerical value representing the option from topics selected.
     */
    public int confirmationAction(String username, String input, List<TopicPresentable> topics) {
        return 1;
    }

    /**
     * Similar to {@link MessageActions#confirmationAction(String, String, List)}, with the only difference of setting the selected messages to read.
     *
     * @param username the current users username.
     * @param input    the users input for this {@link MenuNode}.
     * @param topics   the variety of children this {@link MenuNode} has.
     * @return a numerical value representing the option from topics selected.
     */
    public int confirmationReadAction(String username, String input, List<TopicPresentable> topics) {
        for (String uuid : selectedMessageIDs) {
            messageMover.moveUnreadToRead(UUID.fromString(uuid), username);
        }
        return 1;
    }


    /**
     * Returns the usernames that are attending events,
     * so a message can be sent to them.
     * @param eventIds a list of {@link String} of event ids
     * @return a list {@link String} of users.
     */
    private List<String> getParticipants(List<String> eventIds){
        List<String> users = new ArrayList<>();
        for (String eventId : eventIds){
            users.addAll(eventManager.getParticipants(eventId));
        }
        return users;
    }

    /**
     * 
     * @return A {@link List} of {@link String}s that represent the IDs of the messages currently being looked at by the user through the {@link MessagePresenter}.
     */
    public List<String> getSelectedMessageIDs() {
        return selectedMessageIDs;
    }
}
