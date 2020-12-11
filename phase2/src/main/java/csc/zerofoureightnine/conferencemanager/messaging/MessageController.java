package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.HashMap;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class MessageController {
    private MessageManager messageManager;
    private HashMap<String, String> inputMap = new HashMap<>();
    private UserManager userManager;
    private PermissionManager permissionManager;

    public MessageController(MessageManager messageManager, UserManager userManager, PermissionManager permissionManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
    }

    public int messageSingleUser(String username, String input, List<TopicPresentable> selectableOptions) {
        messageManager.sendMessage(username, inputMap.get("content"), inputMap.get("to"));
        return 0;
    }

    public boolean isValidMessageRecipient(String input, List<TopicPresentable> options) {
        return userManager.userExists(input);
    }

    public int messageGroup(String username, String input, List<TopicPresentable> opts) {
        List<String> users = permissionManager.getUserByPermissionTemplate(Template.values()[Integer.parseInt(inputMap.get("selected_group"))]);
        messageManager.sendMessage(username, inputMap.get("content"), users);
        return 0;
    }

    public HashMap<String, String> getInputMap() {
        return inputMap;
    }
}
