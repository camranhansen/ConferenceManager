package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.HashMap;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class MessageController {
    private MessageManager messageManager;
    private HashMap<String, String> inputMap = new HashMap<>();
    private MessagePresenter presenter;
    private UserManager userManager;

    public MessageController(MessagePresenter messagePresenter, MessageManager messageManager, UserManager userManager) {
        this.presenter = messagePresenter;
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public MenuNode messageSingleUser(String username, String input, List<MenuNode> selectableOptions) {
        messageManager.sendMessage(username, inputMap.get("content"), inputMap.get("to"));
        return selectableOptions.get(0);
    }

    public boolean isValidMessageRecipient(String input, List<MenuNode> options) {
        return userManager.userExists(input);
    }

    public MessagePresenter getPresenter() {
        return presenter;
    }

    public HashMap<String, String> getInputMap() {
        return inputMap;
    }
}
