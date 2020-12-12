package csc.zerofoureightnine.conferencemanager.interaction.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.MenuNode.MenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.interaction.control.Action;
import csc.zerofoureightnine.conferencemanager.interaction.control.Validatable;
import csc.zerofoureightnine.conferencemanager.interaction.general.OptionPresenter;
import csc.zerofoureightnine.conferencemanager.interaction.general.OptionSelector;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.PromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.RetryPromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

/**
 * Helps to build sequential {@link MenuNode} steps to collect a sequence of
 * data for one terminal {@link Action}. For example, logging in, is a two piece
 * of information step process, starting with getting the username, and ending
 * with the password.
 * 
 * As part of collecting a sequence of information, sometimes it's easier to
 * choose from a list of presented options to then execute an {@link Action}
 * upon selection. This can be done as well.
 */
public class LinkedMenuNodeBuilder {
    private Map<String, String> inputMap = new HashMap<>();
    private ArrayList<String> inputTags = new ArrayList<>();
    private ArrayList<PromptPresentable> prompts = new ArrayList<>();
    private ArrayList<Validatable> validatables = new ArrayList<>();
    private ArrayList<RetryPromptPresentable> retryMessages = new ArrayList<>();
    private ArrayList<List<String>> options = new ArrayList<>();
    private ArrayList<List<Permission>> optionsPermissions = new ArrayList<>();

    private final String goalName;

    /**
     * Instantiates a {@link LinkedMenuNodeBuilder} for use.
     * @param goalName A {@link String} for the {@link TopicPresentable}.
     * @param inputMap A {@link Map} for storing the inputs at the different stages in the sequence.
     */
    public LinkedMenuNodeBuilder(String goalName, Map<String, String> inputMap) {
        this.goalName = goalName;
        this.inputMap = inputMap;
    }

    /**
     * Adds a step to the sequence.
     * @param inputTag A {@link String} to associated with the input at this step stored in the {@link Map} given in the constructor.
     * @param prompt A {@link PromptPresentable} for prompting the user for input at this step.
     * @param validatable A {@link Validatable} to use to validate this steps input.
     * @param retryMessage A {@link RetryPromptPresentable} to be used to indicate invalid input and require a reattempt.
     */
    public void addStep(String inputTag, PromptPresentable prompt, Validatable validatable,
            RetryPromptPresentable retryMessage) {
        inputTags.add(inputTag);
        prompts.add(prompt);
        validatables.add(validatable);
        retryMessages.add(retryMessage);
        options.add(null);
        optionsPermissions.add(null);
    }

    /**
     * Adds a step where there exists multiple choice option.
     * @param inputTag The input tag to be associated with the choice.
     * @param options The variety of options.
     * @param permissions The permissions in the same order as the options to associated with each choice.
     */
    public void addMultipleOptions(String inputTag, List<String> options, List<Permission> permissions) {
        inputTags.add(inputTag);
        prompts.add(null);
        validatables.add(null);
        retryMessages.add(null);
        this.options.add(options);
        this.optionsPermissions.add(permissions);
    }

    /**
     * Builds the sequence of {@link MenuNode}s.
     * @param terminator terminating node.
     * @param permission the permission to associate with the entry node.
     * @return The {@link MenuNode} acting as the entry point to this sequence.
     */
    public MenuNode build(MenuNode terminator, Permission permission) {
        class TaggedAutoAction implements Action {
            private final String tag;
            private final Map<String, String> inputMap;

            public TaggedAutoAction(String tag, Map<String, String> inputMap) {
                this.tag = tag;
                this.inputMap = inputMap;
            }

            @Override
            public int complete(String username, String input, List<TopicPresentable> selectableOptions) {
                inputMap.put(tag, input);
                return 2;
            }

        }

        MenuNode previous = terminator;
        for (int i = inputTags.size() - 1; i >= 0; i--) {
            String tag = inputTags.get(i);
            if (options.get(i) == null) {
                MenuNode nextStep = previous;
                MenuNodeBuilder builder = new MenuNodeBuilder(goalName, new TaggedAutoAction(tag, inputMap));
                builder.setPromptable(prompts.get(i));
                builder.setValidatable(validatables.get(i));
                builder.setReattemptable(retryMessages.get(i));
                builder.addChildren(nextStep);

                if (i == 0)
                    builder.setPermission(permission);
                previous = builder.build();
            } else {
                previous = buildMultiNode(tag, previous, options.get(i), optionsPermissions.get(i), permission);
            }

        }

        return previous;
    }

    private MenuNode buildMultiNode(String tag, MenuNode tail, List<String> options, List<Permission> permissions,
            Permission leadPermission) {

        MenuNode[] optionNodes = new MenuNode[options.size()];

        for (int i = 0; i < optionNodes.length; i++) {
            MenuNodeBuilder builder = new MenuNodeBuilder(options.get(i), (u, in, o) -> 2);
            builder.addChildren(tail);
            if (permissions != null)
                builder.setPermission(permissions.get(i));
            optionNodes[i] = builder.build();
        }

        OptionPresenter presenter = new OptionPresenter(goalName);
        OptionSelector selector = new OptionSelector() {
            public int complete(String username, String input, java.util.List<TopicPresentable> selectableOptions) {
                inputMap.put(tag, input);
                return super.complete(username, input, selectableOptions);
            };
        };

        MenuNodeBuilder entry = new MenuNodeBuilder(presenter, selector);
        entry.setValidatable(selector);
        entry.setListable(presenter);
        entry.setPromptable(presenter);
        entry.setReattemptable(presenter);
        entry.addChildren(optionNodes);
        entry.setPermission(leadPermission);
        return entry.build();
    }

    public MenuNode build(MenuNode tail) {
        return build(tail, null);
    }
}
