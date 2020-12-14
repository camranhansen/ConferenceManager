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
    private List<MenuNode> entryPoints;
    private MessageActions messageActions;
    private MessagePresenter messagePresenter;
    private MessageInputValidator messageInputValidator;

    /**
     * Creates the {@link MessageUI} to allow user to interact with messaging.
     *
     * @param messageActions   An {@link MessageActions}
     * @param messagePresenter An {@link MessagePresenter}
     */
    public MessageUI(MessageActions messageActions, MessagePresenter messagePresenter, MessageInputValidator messageInputValidator) {
        this.messageInputValidator = messageInputValidator;
        this.messageActions = messageActions;
        this.messagePresenter = messagePresenter;
        this.messagePresenter.setSelectedMessageIDs(messageActions.getSelectedMessageIDs());
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

    /**
     * Generates the steps necessary to message a single user.
     * Returns a {@link MenuNode} to add to entry points and to be executed.
     *
     * @return a {@link MenuNode} of sending a message
     */
    private MenuNode generateMessageSingleUserNode() {
        String messageSeqTitle = "Message Single User";
        LinkedMenuNodeBuilder sendMessageSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageActions.getInputMap());
        sendMessageSeq.addStep("to", messagePresenter::getPromptForSendTo, messageInputValidator::isValidMessageRecipient, messagePresenter::invalidRecipient);
        sendMessageSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageInputValidator::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder sendMessageNode = new MenuNodeBuilder(messageSeqTitle, messageActions::messageSingleUser);
        sendMessageNode.setCompletable(messagePresenter::getMessageSentCompletion);
        sendMessageNode.backStepCount(3);
        return sendMessageSeq.build(sendMessageNode.build(), Permission.MESSAGE_SINGLE_USER);
    }

    /**
     * Generates the steps necessary to message a group users by template.
     * Returns a {@link MenuNode} to add to entry points and to be executed.
     *
     * @return a {@link MenuNode} of messaging a group
     */
    private MenuNode generateMessageTemplateNodes() {
        String messageTemplateTitle = "Message Group of Users";
        LinkedMenuNodeBuilder sendTemplateSeq = new LinkedMenuNodeBuilder(messageTemplateTitle, messageActions.getInputMap());
        List<String> options = new ArrayList<>();
        Arrays.asList(Template.values()).forEach(t -> options.add(t.toString()));
        sendTemplateSeq.addMultipleOptions("selected_group", options, null);
        sendTemplateSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageInputValidator::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder end = new MenuNodeBuilder(messageTemplateTitle, messageActions::messageGroup);
        end.setCompletable(messagePresenter::getMessageSentCompletion);
        end.backStepCount(3);
        return (sendTemplateSeq.build(end.build(), Permission.MESSAGE_ALL_USERS));
    }

    /**
     * Generates the steps necessary to message the users in events. Gives the option
     * between one event or all events the user is hosting. Returns a {@link MenuNode} to
     * add to entry points and to be executed.
     *
     * @return a {@link MenuNode} of messaging events
     */
    private MenuNode generateMessageEventNodes(){
        String messageEventTitle = "Message Event Lists";
        MenuNodeBuilder eventMessage = new MenuNodeBuilder(messageEventTitle);
        eventMessage.addChildren(getMessageSingleEventNode(), getMessageAllEventNode());
        eventMessage.setPermission(Permission.MESSAGE_EVENTS);
        return eventMessage.build();
    }

    /**
     * Generates the steps necessary to message a users from a single event.
     * Returns a {@link MenuNode} to add in the message event generation.
     *
     * @return a {@link MenuNode} of messaging single event
     */
    private MenuNode getMessageSingleEventNode() {
        String messageSeqTitle = "Message Users From Single Event";
        LinkedMenuNodeBuilder sendEventSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageActions.getInputMap());
        sendEventSeq.addStep("event_id", messagePresenter::getPromptForEventId, messageInputValidator::isValidEventIdForSending, messagePresenter::invalidEventId);
        sendEventSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageInputValidator::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::messageSingleEvent);
        end.setCompletable(messagePresenter::getMessageSentCompletion);
        end.backStepCount(3);
        return (sendEventSeq.build(end.build()));
    }

    /**
     * Generates the steps necessary to message the users in all events
     * a user is hosting. Returns a {@link MenuNode} to add in the
     * message event generation.
     * @return a {@link MenuNode} of messaging all events
     */
    private MenuNode getMessageAllEventNode() {
        String messageSeqTitle = "Message Users For All Your Events";
        LinkedMenuNodeBuilder sendEventSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageActions.getInputMap());
        sendEventSeq.addStep("content", messagePresenter::getPromptForMessageBody, messageInputValidator::isValidContent, messagePresenter::wrongInput);
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::messageAllEvents);
        end.setCompletable(messagePresenter::getMessageSentCompletion);
        end.backStepCount(2);
        return (sendEventSeq.build(end.build()));
    }

    /**
     * Generates the steps necessary to move messages, which gives users the option
     * to move to archived, move to unread or remove from archive. Returns a {@link MenuNode}
     * to add to entry points and to be executed.
     *
     * @return a {@link MenuNode} of moving messages
     */
    private MenuNode generateMoveMessageNodes(){
        String messageMoveTitle = "Move Messages";
        MenuNodeBuilder moveMessage = new MenuNodeBuilder(messageMoveTitle);
        moveMessage.addChildren(getMoveMessageArchiveNode(), getMoveMessageUnreadNode(), getRemoveMessageArchiveNode());
        moveMessage.setPermission(Permission.MESSAGE_MOVE);
        return moveMessage.build();
    }

    /**
     * Generates the steps necessary to move a message to unread inbox. Returns a
     * {@link MenuNode} to add to the generation of move message node.
     *
     * @return a {@link MenuNode} of moving to unread
     */
    private MenuNode getMoveMessageUnreadNode(){
        String messageSeqTitle = "Move a Message To Unread";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::moveMessageToUnread);
        end.setListable(messagePresenter::getUserInboxRead);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageInputValidator::isValidMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        end.setCompletable(messagePresenter::messageMoveConfirmation);
        return end.build();
    }

    /**
     * Generates the steps necessary to move a message to archive inbox. Returns a
     * {@link MenuNode} to add to the generation of move message node.
     *
     * @return a {@link MenuNode} of moving to archive
     */
    private MenuNode getMoveMessageArchiveNode(){
        String messageSeqTitle = "Move a Message To Archive";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::moveMessageToArchive);
        end.setListable(messagePresenter::getUserInbox);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageInputValidator::isValidMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        end.setCompletable(messagePresenter::messageMoveConfirmation);
        return end.build();
    }

    /**
     * Generates the steps necessary to remove a message from archive inbox. Returns a
     * {@link MenuNode} to add to the generation of move message node.
     *
     * @return a {@link MenuNode} of removing from archive
     */
    private MenuNode getRemoveMessageArchiveNode(){
        String messageSeqTitle = "Remove a Message From Archive";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::removeMessageFromArchive);
        end.setListable(messagePresenter::getUserArchived);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageInputValidator::isValidMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        end.setCompletable(messagePresenter::messageMoveConfirmation);
        return end.build();
    }

    /**
     * Generates the steps necessary to delete a single message. Returns a
     * {@link MenuNode} to add to the generation of delete message node.
     *
     * @return a {@link MenuNode} of deleting a message
     */
    private MenuNode deleteSingleSeq(){
        String messageSeqTitle = "Delete Single Message";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::deleteSingleMessage);
        end.setListable(messagePresenter::getUserInbox);
        end.setPromptable(messagePresenter::promptForMessageIndex);
        end.setValidatable(messageInputValidator::isValidMessageSelectionFromGroup);
        end.setReattemptable(messagePresenter::wrongInput);
        end.setCompletable(messagePresenter::messageDeletedConfirmation);
        return end.build();
    }

    /**
     * Generates the steps necessary to delete a single conversation from
     * another username. Returns a {@link MenuNode} to add to the generation of delete message node.
     *
     * @return a {@link MenuNode} of deleting a conversation
     */
    private MenuNode deleteConvoSeq(){
        String messageSeqTitle = "Delete Conversation";
        MenuNodeBuilder deleteConvoSeq = new MenuNodeBuilder(messageSeqTitle, messageActions::deleteSingleConversation);
        deleteConvoSeq.setPromptable(messagePresenter::getPromptForFrom);
        deleteConvoSeq.setValidatable(messageInputValidator::isValidMessageRecipient);
        deleteConvoSeq.setCompletable(messagePresenter::messageDeletedConfirmation);
        return (deleteConvoSeq.build());
    }

    /**
     * Generates the steps necessary to clear the users inbox. Returns a
     * {@link MenuNode} to add to the generation of delete message node.
     *
     * @return a {@link MenuNode} of deleting inbox
     */
    private MenuNode deleteInboxSeq(){
        String messageSeqTitle = "Clear Inboxes";
        MenuNodeBuilder deleteInboxSeq = new MenuNodeBuilder(messageSeqTitle, messageActions::deleteAllInboxes);
        deleteInboxSeq.setCompletable(messagePresenter::messageDeletedConfirmation);
        return deleteInboxSeq.build();
    }

    /**
     * Generates the steps necessary to delete messages, which gives users the option
     * to delete a single, delete a conversation or clear inboxes. Returns a {@link MenuNode}
     * to add to entry points and to be executed.
     *
     * @return a {@link MenuNode} of this deleting messages
     */
    private MenuNode generateDeleteMessageNodes(){
        String  messageSeqTitle = "Delete Messages";
        MenuNodeBuilder deleteMessageSeq = new MenuNodeBuilder(messageSeqTitle);
        deleteMessageSeq.addChildren(deleteSingleSeq(), deleteConvoSeq(), deleteInboxSeq());
        deleteMessageSeq.setPermission(Permission.MESSAGE_DELETE);
        return deleteMessageSeq.build();
    }

    /**
     * Generates the steps necessary to get the unread inbox for viewing.
     * Returns a {@link MenuNode} to add to the generation of viewing message node.
     *
     * @return a {@link MenuNode} of getting unread inbox
     */
    private MenuNode getUnreadInbox(){
        String messageSeqTitle = "View Unread Inbox";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::confirmationReadAction);
        end.setPromptable(messagePresenter::promptForConfirmation);
        end.setListable(messagePresenter::getUserUnread);
        return end.build();
    }

    /**
     * Generates the steps necessary to get the entire inbox for viewing.
     * Returns a {@link MenuNode} to add to the generation of viewing message node.
     *
     * @return a {@link MenuNode} of getting entire inbox
     */
    private MenuNode getEntireInbox(){
        String messageSeqTitle = "View Entire Inbox";
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::confirmationReadAction);
        end.setPromptable(messagePresenter::promptForConfirmation);
        end.setListable(messagePresenter::getUserInbox);
        return end.build();
    }

    /**
     * Generates the steps necessary to get the inbox from another username for viewing.
     * Returns a {@link MenuNode} to add to the generation of viewing message node.
     *
     * @return a {@link MenuNode} of getting inbox from another username
     */
    private MenuNode getInboxFrom() {
        String messageSeqTitle = "View Messages From Username";
        LinkedMenuNodeBuilder viewInboxSeq = new LinkedMenuNodeBuilder(messageSeqTitle, messageActions.getInputMap());
        viewInboxSeq.addStep("from", messagePresenter::getPromptForFrom, messageInputValidator::isValidMessageRecipient, messagePresenter::invalidUsername);
        MenuNodeBuilder end = new MenuNodeBuilder(messageSeqTitle, messageActions::confirmationReadAction);
        end.backStepCount(2);
        end.setListable(messagePresenter::getUserInboxFrom);
        end.setPromptable(messagePresenter::promptForConfirmation);
        return (viewInboxSeq.build(end.build()));
    }


    /**
     * Generates the steps necessary to get the archived inbox for viewing.
     * Returns a {@link MenuNode} to add to the generation of viewing message node.
     *
     * @return a {@link MenuNode} of archived inbox
     */
    private MenuNode getArchivedInbox(){
        String messageSeqTitle = "View Archived Messages";
        MenuNodeBuilder node = new MenuNodeBuilder(messageSeqTitle, messageActions::confirmationAction);
        node.setPromptable(messagePresenter::promptForConfirmation);
        node.setListable(messagePresenter::getUserArchived);
        return (node.build());
    }

    /**
     * Generates the steps necessary to view messages which gives users the option
     * to view archived, view unread, view from one user, or view whole inbox. Returns
     * a {@link MenuNode} to add to entry points and to be executed.
     *
     * @return a {@link MenuNode} of this viewing messages
     */
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
