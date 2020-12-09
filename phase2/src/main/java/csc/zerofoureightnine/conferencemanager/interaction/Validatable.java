package csc.zerofoureightnine.conferencemanager.interaction;

import java.util.List;

public interface Validatable { //Generally, each controller should have it's own validator.
    /**
     * 
     * @param input the raw input from the user.
     * @param options the options the user has available to them.
     * @return true if the input is valid for the given options.
     */
    public boolean validateInput(String input, List<MenuNode> options);
}
