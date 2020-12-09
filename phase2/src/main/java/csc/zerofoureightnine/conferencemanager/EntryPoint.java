package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.interaction.ConsoleUserInterface;

public class EntryPoint {
    public static void main(String[] args) {
        SQLConfiguration sqlConfiguration = new SQLConfiguration("db/data");
        ProgramInitiator initiator = new ProgramInitiator();
        ConsoleUserInterface userInterface = new ConsoleUserInterface(initiator.getMainMenu());
        userInterface.interact();
    }

}
