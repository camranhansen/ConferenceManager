package csc.zerofoureightnine.conferencemanager.interaction.control;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.RetryPromptPresentable;
import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;

import java.util.List;

/**
 * Interface used to denote input validity for various UIsections.
 */
public interface Validatable {
    /**
     * Checks if the input is considered valid. If the input is invalid,
     * {@link RetryPromptPresentable#getRetryMessage()} is presented to the user, and
     * they are given requested to input something valid.
     *
     * @param input   the raw input from the user.
     * @param options the options the user has available to them.
     * @return True if the input is valid for the given options. False otherwise.
     */
    boolean validateInput(String input, List<TopicPresentable> options);
}
