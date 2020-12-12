package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;


public class EntryPoint {
    public static void main(String[] args) {
        SQLConfiguration configuration = new SQLConfiguration("db/data");
        MasterController masterController = new MasterController(
        new SQLMap<>(configuration, UserData.class), 
        new SQLMap<>(configuration, EventData.class), 
        new SQLMap<>(configuration, MessageData.class),
        new SQLMap<>(configuration, SpecialRequestData.class)
        );
        MainUI mainUI = new MainUI(masterController);
        mainUI.run();
    }
}
