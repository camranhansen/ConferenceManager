package events;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MultiSpkEvent extends Events{
    private String type;

    public void setType(){
        this.type = "m";
    }


    public String getType(){
        return this.type;
    }
}
