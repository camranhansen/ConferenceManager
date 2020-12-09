package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;

public class EntryPoint {
    public static void main(String[] args) {
        ProgramInitiator initiator = new ProgramInitiator();
        ConsoleUserInterface userInterface = new ConsoleUserInterface(initiator.getMainMenu());
        userInterface.interact();
    }

}
