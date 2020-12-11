package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.HashMap;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.NameablePresentation;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class MessageController {
    private MessageManager messageManager;
    private HashMap<String, String> inputMap = new HashMap<>();
    private UserManager userManager;

    public MessageController(MessageManager messageManager, UserManager userManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public NameablePresentation messageSingleUser(String username, String input, List<NameablePresentation> selectableOptions) {
        messageManager.sendMessage(username, inputMap.get("content"), inputMap.get("to"));
        return selectableOptions.get(0);
    }

    public boolean isValidMessageRecipient(String input, List<NameablePresentation> options) {
        return userManager.userExists(input);
    }

    public HashMap<String, String> getInputMap() {
        return inputMap;
    }
}
