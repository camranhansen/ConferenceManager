package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLConfiguration;
import csc.zerofoureightnine.conferencemanager.gateway.sql.SQLMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;


public class EntryPoint {
    public static void main(String[] args) {
        SQLConfiguration configuration = new SQLConfiguration("db/data");
        PersistentMap<String, UserData> userMap = new SQLMap<>(configuration, UserData.class);
        PersistentMap<String, EventData> eventMap = new SQLMap<>(configuration, EventData.class);
        PersistentMap<String, MessageData> messageMap = new SQLMap<>(configuration, MessageData.class);
        MasterController masterController = new MasterController(userMap, eventMap, messageMap);
        MainUI mainUI = new MainUI(masterController);
        mainUI.run();
    }
}
