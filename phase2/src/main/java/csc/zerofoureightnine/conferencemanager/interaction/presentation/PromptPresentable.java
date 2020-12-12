package csc.zerofoureightnine.conferencemanager.interaction.presentation;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;

public interface PromptPresentable { //Presenter
    /**
     * The prompt the user recieves when landing on the {@link MenuNode} associated
     * with this presenter.
     *
     * @return A string containing the prompt for the user.
     */
    String getPrompt(String username);
}
