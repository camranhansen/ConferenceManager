package csc.zerofoureightnine.conferencemanager.messaging.controllers;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class TemplateMessageController {
    private List<String> users;
    private String content;
    private Template selected;
    private MessageManager messageManager;
    private PermissionManager permissionManager;

    public TemplateMessageController(MessageManager messageManager, PermissionManager permissionManager) {
        this.messageManager = messageManager;
        this.permissionManager = permissionManager;
    }

    public boolean validateGroupSelection(String input, List<TopicPresentable> names) {
        return input.matches("^[0-9]+$") && 
        Integer.parseInt(input) < Template.values().length;
    }

    public int getGroupSelection(String username, String input, List<TopicPresentable> names) {
        selected = Template.values()[Integer.parseInt(input)];
        users = permissionManager.getUserByPermissionTemplate(selected); // Input has already been validated, therefore, will not have issues.
        return 2;
    }

    public int composeMessageToGroup(String username, String input, List<TopicPresentable> names) {
        this.content = input;
        return 2;
    }

    public int sendMessageToGroup(String username, String input, List<TopicPresentable> names) {
        messageManager.sendMessage(username, content, users);
        return 0;
    }

    public Template getSelected() {
        return selected;
    }
}
