package csc.zerofoureightnine.conferencemanager.input.validators;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EventHourValidator implements Validator{
    private EventManager eventManager;
    private LinkedHashMap<InputStrategy, String> inputHistory;

    public EventHourValidator(EventManager eventManager, LinkedHashMap<InputStrategy, String> inputHistory){
        this.eventManager = eventManager;
        this.inputHistory = inputHistory;
    }

    public boolean validateInput(String userInput) {
        if(!userInput.trim().matches("[9]|[1][0-6]")){return false;
        }
        if(!allEventDetailsPresent()){
            return false;
        }
        return eventManager.validHourForEvent(getRoom(), getSpeakers(), getDay(), userInput);
    }

    private List<String> getSpeakers(){
        List<String> speakers = new ArrayList<>();
        String speaker = inputHistory.get(InputStrategy.EVENT_SPEAKER_SINGLE);
        speakers.add(speaker);
        return speakers;
    }

    private String getRoom(){
        return inputHistory.get(InputStrategy.EVENT_ROOM);
    }

    private String getDay(){
        return inputHistory.get(InputStrategy.EVENT_DAY);
    }

    private boolean allEventDetailsPresent(){
        List<InputStrategy> nodes = new ArrayList<>();
        if (inputHistory.containsKey(InputStrategy.EVENT_ROOM)){
            nodes.add(InputStrategy.EVENT_ROOM);
        }
        if(inputHistory.containsKey(InputStrategy.EVENT_SPEAKER_SINGLE)){
            nodes.add(InputStrategy.EVENT_SPEAKER_SINGLE);
        }
        if(inputHistory.containsKey(InputStrategy.EVENT_DAY)){
            nodes.add(InputStrategy.EVENT_DAY);
        }
        return nodes.size() == 3;
    }
}
