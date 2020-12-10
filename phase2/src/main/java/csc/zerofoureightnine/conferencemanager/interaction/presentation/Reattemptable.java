package csc.zerofoureightnine.conferencemanager.interaction.presentation;

public interface Reattemptable {  //Presenter
    /**
     * The retry message.
     * @return A {@link String} querying the user to retry, or empty.
     */
    String getRetryMessage();
}