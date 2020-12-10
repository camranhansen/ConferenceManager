package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;

public interface Validatable { //Generally, controllers are responsible for their own validation of input.
    /**
     * Checks if the input is considered valid. If the input is invalid,
     * {@link Presentable#getRetryMessage()} is presented to the user, and 
     * they are given requested to input something valid.
     * @param input the raw input from the user.
     * @param options the options the user has available to them.
     * @return True if the input is valid for the given options. False otherwise.
     */
    public boolean validateInput(String input, List<MenuNode> options);
}
