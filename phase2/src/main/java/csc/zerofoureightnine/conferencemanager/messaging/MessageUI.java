package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.ArrayList;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.GeneralMenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class MessageUI implements UISection {
    List<MenuNode> entryPoints;
    MessageController messageController;

    public MessageUI(MessageController messageController) {
        this.messageController = messageController;
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();

        String messageSeqTitle = "Message Single User";
        LinkedMenuNodeBuilder sendMessageSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        sendMessageSeq.addStep("to", messageController.getPresenter()::getPromptForSendTo, messageController::isValidMessageRecipient, messageController.getPresenter()::invalidRecipient);
        sendMessageSeq.addStep("content", messageController.getPresenter()::getPromptForMessageBody, null, null);
        MenuNodeBuilder sendMessageNode = new MenuNodeBuilder(messageSeqTitle, messageController::messageSingleUser, messageController.getPresenter()::getMessageSentCompletion);
        entryPoints.add(sendMessageSeq.build(sendMessageNode.build(), Permission.MESSAGE_SINGLE_USER));
        return entryPoints;
    }

    @Override
    public String getSectionListing() {
        return "Messaging";
    }

}
