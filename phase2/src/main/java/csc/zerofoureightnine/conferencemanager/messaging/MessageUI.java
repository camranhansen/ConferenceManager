package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageUI implements UISection {
    List<MenuNode> entryPoints;
    MessageController messageController;
    private MessagePresenter messagePresenter;

    /**
     * Creates the {@link MessageUI} to allow user to interact with messaging.
     * @param messageController An {@link MessageController}
     * @param messagePresenter An {@link MessagePresenter}
     */
    public MessageUI(MessageController messageController, MessagePresenter messagePresenter) {
        this.messageController = messageController;
        this.messagePresenter = messagePresenter;
        this.messagePresenter.setSelectedMessageIDs(messageController.getSelectedMessageIDs());
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();
        entryPoints.add(generateMessageSingleUserNode());
        entryPoints.add(generateMessageTemplateNodes());
        entryPoints.add(generateDeleteMessageNodes());
        entryPoints.add(generateMessageEventNodes());
        entryPoints.add(generateMoveMessageNodes());
        entryPoints.add(generateMessageViewingNodes());
        return entryPoints;
    }

    private MenuNode generateMessageSingleUserNode() {
        String messageSeqTitle = "Message Single User";
        LinkedMenuNodeBuilder sendMessageSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        sendMessageSeq.addStep("to", messagePresenter::getPromptForSendTo, messageController::isValidMessageRecipient, messagePresenter::invalidRecipient);
        sendMessageSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageController::isValidContent, messagePresenter::wrongInput);
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
        sendTemplateSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageController::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder end = new MenuNodeBuilder(messageTemplateTitle, messageController::messageGroup);
        end.backStepCount(3);
        return (sendTemplateSeq.build(end.build(), Permission.MESSAGE_ALL_USERS));
    }

    private MenuNode generateMessageEventNodes(){
        String messageEventTitle = "Message Event Lists";
        MenuNodeBuilder eventMessage = new MenuNodeBuilder(messageEventTitle);
        eventMessage.addChildren(getMessageSingleEventNode(), getMessageAllEventNode());
        eventMessage.setPermission(Permission.MESSAGE_EVENTS);
        return eventMessage.build();
    }
    private MenuNode getMessageSingleEventNode(){
        String messageSeqTitle = "Message Users From Single Event";
        LinkedMenuNodeBuilder sendEventSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        sendEventSeq.addStep("event_id", messagePresenter::getPromptForEventId, messageController::isValidEventIdForSending, messagePresenter::invalidEventId);
        sendEventSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageController::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::messageSingleEvent);
        end.backStepCount(3);
        return (sendEventSeq.build(end.build()));
    }

    private MenuNode getMessageAllEventNode(){
        String messageSeqTitle = "Message Users For All Your Events";
        LinkedMenuNodeBuilder sendEventSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        sendEventSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageController::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::messageAllEvents);
        end.backStepCount(2);
        return (sendEventSeq.build(end.build()));
    }

    private MenuNode generateMoveMessageNodes(){
        String messageMoveTitle = "Move Messages";
        MenuNodeBuilder moveMessage = new MenuNodeBuilder(messageMoveTitle);
        moveMessage.addChildren(getMoveMessageArchiveNode(), getMoveMessageUnreadNode(), getRemoveMessageArchiveNode());
        moveMessage.setPermission(Permission.MESSAGE_MOVE);
        return moveMessage.build();
    }

    private MenuNode getMoveMessageUnreadNode(){
        String messageSeqTitle = "Move a Message To Unread";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::moveMessageToUnread);
        end.setListable(messagePresenter::getUserInboxRead);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageController::validateMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        return end.build();
    }

    private MenuNode getMoveMessageArchiveNode(){
        String messageSeqTitle = "Move a Message To Archive";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::moveMessageToArchive);
        end.setListable(messagePresenter::getUserInbox);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageController::validateMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        return end.build();
    }

    private MenuNode getRemoveMessageArchiveNode(){
        String messageSeqTitle = "Remove a Message From Archive";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::removeMessageFromArchive);
        end.setListable(messagePresenter::getUserArchived);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageController::validateMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        return end.build();
    }


    private MenuNode deleteSingleSeq(){
        String messageSeqTitle = "Delete Single Message";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::deleteSingleMessage);
        end.setListable(messagePresenter::getUserInbox);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageController::validateMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        return end.build();
    }

    private MenuNode deleteConvoSeq(){
        String messageSeqTitle = "Delete Conversation";
        MenuNodeBuilder deleteConvoSeq = new MenuNodeBuilder(messageSeqTitle, messageController::deleteSingleConversation);
        deleteConvoSeq.setPromptable(messagePresenter::getPromptForFrom);
        deleteConvoSeq.setValidatable(messageController::isValidMessageRecipient);
        return (deleteConvoSeq.build());
    }

    private MenuNode deleteInboxSeq(){
        String messageSeqTitle = "Clear Inboxes";
        MenuNodeBuilder deleteInboxSeq = new MenuNodeBuilder(messageSeqTitle, messageController::deleteAllInboxes);
        return deleteInboxSeq.build();
    }

    private MenuNode generateDeleteMessageNodes(){
        String  messageSeqTitle = "Delete Messages";
        MenuNodeBuilder deleteMessageSeq = new MenuNodeBuilder(messageSeqTitle);
        deleteMessageSeq.addChildren(deleteSingleSeq(), deleteConvoSeq(), deleteInboxSeq());
        deleteMessageSeq.setPermission(Permission.MESSAGE_DELETE);
        return deleteMessageSeq.build();
    }

    private MenuNode getUnreadInbox(){
        String messageSeqTitle = "View Unread Inbox";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::confirmationReadAction);
        end.setPromptable(messagePresenter::promptForConfirmation);
        end.setListable(messagePresenter::getUserUnread);
        return end.build();
    }

    private MenuNode getEntireInbox(){
        String messageSeqTitle = "View Entire Inbox";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::confirmationReadAction);
        end.setPromptable(messagePresenter::promptForConfirmation);
        end.setListable(messagePresenter::getUserInbox);
        return end.build();
    }

    private MenuNode getInboxFrom(){
        String messageSeqTitle = "View Messages From Username";
        LinkedMenuNodeBuilder viewInboxSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageController.getInputMap());
        viewInboxSeq.addStep("from", messagePresenter::getPromptForFrom, messageController::isValidMessageRecipient, messagePresenter::invalidUsername);
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageController::confirmationReadAction);
        end.backStepCount(2);
        end.setListable(messagePresenter::getUserInboxFrom);
        end.setPromptable(messagePresenter::promptForConfirmation);
        return (viewInboxSeq.build(end.build()));
    }

    private MenuNode getArchivedInbox(){
        String messageSeqTitle = "View Archived Messages";
        MenuNodeBuilder node = new MenuNodeBuilder(messageSeqTitle, messageController::confirmationAction);
        node.setPromptable(messagePresenter::promptForConfirmation);
        node.setListable(messagePresenter::getUserArchived);
        return (node.build());
    }

    private MenuNode generateMessageViewingNodes(){
        String messageSeqTitle = "View Messages";
        MenuNodeBuilder viewMessageSeq = new MenuNodeBuilder(messageSeqTitle);
        viewMessageSeq.addChildren(getEntireInbox(), getInboxFrom(), getUnreadInbox(), getArchivedInbox());
        viewMessageSeq.setPermission(Permission.VIEW_SELF_MESSAGES);
        return viewMessageSeq.build();
    }

    @Override
    public String getSectionListing() {
        return "Messaging";
    }
}
