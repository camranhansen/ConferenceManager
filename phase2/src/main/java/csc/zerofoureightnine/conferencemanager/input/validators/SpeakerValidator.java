package csc.zerofoureightnine.conferencemanager.input.validators;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.input.InputStrategy;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpeakerValidator implements Validator{
    private PermissionManager pm;
    private EventManager eventManager;
    private Map<InputStrategy, String> inputHistory;

    public SpeakerValidator(PermissionManager pm, EventManager em,  Map<InputStrategy, String> inputHistory){
        this.pm = pm;
        this.eventManager = em;
        this.inputHistory = inputHistory;
    }

    public boolean validateInput(String userInput) {
        if (userInput.trim().equalsIgnoreCase("party")){
            return true;
        }
        String[] inputSpeakers = userInput.split("[,]*");
        if (!allSpeakersExist(inputSpeakers)){
            return false;
        }
        List<String> validSpeakers = new ArrayList<>(Arrays.asList(inputSpeakers));
        return eventManager.checkConflictSpeaker(getTime(), validSpeakers);
    }

    private boolean allSpeakersExist(String[] inputSpeakers){
        List<String> speakers = pm.getUserByPermissionTemplate(Template.SPEAKER);
        for (String speaker: inputSpeakers){
            if(!speakers.contains(speaker)){
                return false;
            }
        }
        return true;
    }

    private Instant getTime(){
       return eventManager.parseTime(inputHistory.get(InputStrategy.EVENT_DAY),
               inputHistory.get(InputStrategy.EVENT_HOUR));
    }
}
