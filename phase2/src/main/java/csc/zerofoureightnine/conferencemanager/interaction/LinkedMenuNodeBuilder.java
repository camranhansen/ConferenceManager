package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.Validatable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.Promptable;

public class LinkedMenuNodeBuilder {
    private Map<String, String> inputMap = new HashMap<>();
    private ArrayList<String> inputTags = new ArrayList<>();
    private ArrayList<Promptable> prompts = new ArrayList<>();
    private ArrayList<Validatable> validatables = new ArrayList<>();
    private ArrayList<String> retryMessages = new ArrayList<>();

    private final String goalName;

    public LinkedMenuNodeBuilder(String goalName, Map<String, String> inputMap) {
        this.goalName = goalName;
        this.inputMap = inputMap;
    }

    public void addStep(String inputTag, Promptable prompt, Validatable validatable, String retryMessage) {
        inputTags.add(inputTag);
        prompts.add(prompt);
        validatables.add(validatable);
        retryMessages.add(retryMessage);
    }

    public MenuNode build(MenuNode tail) {
        MenuNode previous = tail;
        for (int i = inputTags.size() - 1; i >= 0; i--) {
            final String tag = inputTags.get(i);
            final MenuNode nextStep = previous;
            MenuNodeBuilder builder = new MenuNodeBuilder(() -> goalName, (u, in, o) -> {
                inputMap.put(tag, in);
                return nextStep;
            }, (n) -> "");
            builder.setPromptable(prompts.get(i));
            builder.setValidatable(validatables.get(i));
            final String retryMessage = retryMessages.get(i);
            builder.setReattemptable(() -> retryMessage);
            builder.addChildren(nextStep);
            previous = builder.build();
        }

        return previous;
    }
}
