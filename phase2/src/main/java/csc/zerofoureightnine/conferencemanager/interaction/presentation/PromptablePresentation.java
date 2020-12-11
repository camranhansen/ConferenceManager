package csc.zerofoureightnine.conferencemanager.interaction.presentation;

public interface PromptablePresentation { //Presenter
    /**
     * The prompt the user recieves when landing on the {@link MenuNode} associated
     * with this presenter.
     * 
     * @return A string containing the prompt for the user.
     */
    String getPrompt();
}
