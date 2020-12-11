package csc.zerofoureightnine.conferencemanager.interaction.control;

import java.util.List;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.RetryPromptPresentable;

public interface Validatable { //Generally, controllers are responsible for their own validation of input.
    /**
     * Checks if the input is considered valid. If the input is invalid,
     * {@link RetryPromptPresentable#getRetryMessage()} is presented to the user, and 
     * they are given requested to input something valid.
     * @param input the raw input from the user.
     * @param options the options the user has available to them.
     * @return True if the input is valid for the given options. False otherwise.
     */
    public boolean validateInput(String input, List<TopicPresentable> options);
}
