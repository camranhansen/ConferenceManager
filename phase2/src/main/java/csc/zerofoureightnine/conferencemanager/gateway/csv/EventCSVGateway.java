package csc.zerofoureightnine.conferencemanager.gateway.csv;

import csc.zerofoureightnine.conferencemanager.events.EventManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EventCSVGateway extends CSVGateway {

    public EventCSVGateway() {
        super(7, "data/event_data.csv");
    }

    public void readEventsFromGateway(EventManager eventManager) throws IOException {
        if (!Files.exists(Paths.get(getFilePath()))) return;
        this.readFromFile();
        for (int i=0; i<this.getRowCount(); i++){
            String[] eventData = this.getRow(i);
            eventManager.setEventData(eventData);
        }
    }

    public void saveEvents(EventManager eventManager) throws IOException {
        ArrayList<String[]> eventList = eventManager.getAllEventData();
        for (int i=0; i<eventList.size(); i++){
            this.updateRow(i, eventList.get(i));
        }
        this.flush();

    }




}
