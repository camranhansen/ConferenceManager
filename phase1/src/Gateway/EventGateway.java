package Gateway;

public class EventGateway extends Gateway{

    public EventGateway() {
        super(7, "assets/event_data.csv");
    }

    public void readFromGateway(){
        this.readFromFile();

    }

    public void saveEvents(){

    }



}
