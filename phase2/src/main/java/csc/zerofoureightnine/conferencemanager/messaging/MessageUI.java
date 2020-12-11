package csc.zerofoureightnine.conferencemanager.messaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

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
        entryPoints.add(generateMessageSingleUserNode());
        entryPoints.add(generateMessageTemplateNodes());
        return entryPoints;
    }

    private MenuNode generateMessageSingleUserNode() {
        String messageSeqTitle = "Message Single User";
        LinkedMenuNodeBuilder sendMessageSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        sendMessageSeq.addStep("to", messagePresenter::getPromptForSendTo, messageController::isValidMessageRecipient, messagePresenter::invalidRecipient);
        sendMessageSeq.addStep("content", messagePresenter::getPromptForMessageBody, null, null);
        MenuNodeBuilder sendMessageNode = new MenuNodeBuilder(messageSeqTitle, messageController::messageSingleUser);
        sendMessageNode.setCompletable(messagePresenter::getMessageSentCompletion);
        sendMessageNode.backStepCount(3);
        return sendMessageSeq.build(sendMessageNode.build(), Permission.MESSAGE_SINGLE_USER);
    }

    private MenuNode generateMessageTemplateNodes() {
        String messageTemplateTitle = "Message Group of Users";
        LinkedMenuNodeBuilder sendTemplateSeq = new LinkedMenuNodeBuilder(messageTemplateTitle, messageController.getInputMap());
        List<String> options = new ArrayList<>();
        Arrays.asList(Template.values()).forEach(t -> options.add(t.toString()));
        sendTemplateSeq.addMultipleOptions("selected_group", options, null);
        sendTemplateSeq.addStep("content", messagePresenter::getPromptForMessageBody, null, null);
        MenuNodeBuilder end = new MenuNodeBuilder(messageTemplateTitle, messageController::messageGroup);
        end.backStepCount(3);
        return (sendTemplateSeq.build(end.build(), Permission.MESSAGE_ALL_USERS));
    }

    @Override
    public String getSectionListing() {
        return "Messaging";
    }

}
