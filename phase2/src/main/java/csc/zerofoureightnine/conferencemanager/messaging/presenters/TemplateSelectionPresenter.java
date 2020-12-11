package csc.zerofoureightnine.conferencemanager.messaging.presenters;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.completePresentable;
import csc.zerofoureightnine.conferencemanager.messaging.controllers.TemplateMessageController;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.InfoPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.ReattemptPromptPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

public class TemplateSelectionPresenter implements InfoPresentable, ReattemptPromptPresentable, PromptPresentable, completePresentable {
    private TemplateMessageController controller;

    public TemplateSelectionPresenter(TemplateMessageController groupSendingController) {
        this.controller = groupSendingController;
    }

    @Override
    public String getInfo(List<TopicPresentable> options) {
        StringBuilder sb = new StringBuilder();
        Template[] templates = Template.values();
        for (int i = 0; i < templates.length; i++) {
            sb.append(i + ") " + templates[i].toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getPrompt() {
        return "Please enter number corresponding to the group to message";
    }

    @Override
    public String getRetryMessage() {
        return "Invalid input. Please make sure your value ranges from 0 to " + Template.values().length;
    }

    @Override
    public String getCompleteMessage(TopicPresentable nextNode) {
        return "Selected Group: " + controller.getSelected();
    }

}
