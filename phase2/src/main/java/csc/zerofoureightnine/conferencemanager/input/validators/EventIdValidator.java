package csc.zerofoureightnine.conferencemanager.input.validators;

import csc.zerofoureightnine.conferencemanager.events.EventManager;

public class EventIdValidator implements Validator{
    private EventManager eventManager;

    public EventIdValidator(EventManager em){
        eventManager = em;
    }
    public boolean validateInput(String userInput) {
        return eventManager.eventExists(userInput);
    }
}