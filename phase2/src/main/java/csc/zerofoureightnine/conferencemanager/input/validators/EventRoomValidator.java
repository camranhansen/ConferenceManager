package csc.zerofoureightnine.conferencemanager.input.validators;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.input.InputStrategy;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventRoomValidator implements Validator{
    private EventManager eventManager;
    private Map<InputStrategy, String> inputHistory;

    public EventRoomValidator(EventManager eventManager, Map<InputStrategy, String> inputHistory){
        this.eventManager = eventManager;
        this.inputHistory = inputHistory;
    }

    public boolean validateInput(String userInput) {
        return eventManager.checkRoom(getTime(), userInput);
    }

    private Instant getTime(){
        return eventManager.parseTime(inputHistory.get(InputStrategy.EVENT_DAY),
                inputHistory.get(InputStrategy.EVENT_HOUR));
    }

}
