package Gateway;

import java.io.IOException;

public class EventGateway extends Gateway{

    public EventGateway() {
        super(7, "assets/event_data.csv");
    }

    public void readFromGateway() throws IOException {
        this.readFromFile();

    }

    public void saveEvents(){

    }



}
