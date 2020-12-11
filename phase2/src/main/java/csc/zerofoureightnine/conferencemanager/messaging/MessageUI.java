package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.ArrayList;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

public class MessageUI implements UISection {
    List<MenuNode> entryPoints;
    MessageController messageController;
    private MessagePresenter messagePresenter;

    public MessageUI(MessageController messageController, MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
        this.messageController = messageController;
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();

        String messageSeqTitle = "Message Single User";
        LinkedMenuNodeBuilder sendMessageSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        sendMessageSeq.addStep("to", messagePresenter::getPromptForSendTo, messageController::isValidMessageRecipient, messagePresenter::invalidRecipient);
        sendMessageSeq.addStep("content", messagePresenter::getPromptForMessageBody, null, null);
        MenuNodeBuilder sendMessageNode = new MenuNodeBuilder(messageSeqTitle, messageController::messageSingleUser, messagePresenter::getMessageSentCompletion);
        entryPoints.add(sendMessageSeq.build(sendMessageNode.build(), Permission.MESSAGE_SINGLE_USER));
        return entryPoints;
    }

    @Override
    public String getSectionListing() {
        return "Messaging";
    }

}
