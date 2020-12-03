package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.input.validators.*;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

public class InputStrategyManager {
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;
    private EventDayValidator eventDayValidator;
    private EventHourValidator eventHourValidator;
    private CapacityValidator capacityValidator;
    private MessageContentValidator messageContentValidator;
    private ExistingUserValidator existingUserValidator;
    private SpeakerValidator speakerValidator;
    private EventIdValidator eventIdValidator;

    public InputStrategyManager(MessageManager mm, UserManager um, EventManager em){
        messageManager = mm;
        userManager = um;
        eventManager = em;
        eventDayValidator = new EventDayValidator();
        eventHourValidator = new EventHourValidator();
        capacityValidator = new CapacityValidator();
        messageContentValidator = new MessageContentValidator();
        existingUserValidator = new ExistingUserValidator(userManager);
        speakerValidator = new SpeakerValidator(userManager);
        eventIdValidator = new EventIdValidator(eventManager);
    }

    public boolean validate(InputStrategy inputStrategy, String userInput){
        switch(inputStrategy){
            case VALID_USERNAME:
                return existingUserValidator.validateInput(userInput);
            case EVENT_DAY:
                return eventDayValidator.validateInput(userInput);
            case EVENT_HOUR:
                return eventHourValidator.validateInput(userInput);
            case EVENT_CAPACITY:
                return capacityValidator.validateInput(userInput);
            case MESSAGE_CONTENT:
                return messageContentValidator.validateInput(userInput);
            case EVENT_SPEAKER_SINGLE:
                return speakerValidator.validateInput(userInput);
            case VALID_EVENT_ID:
                return eventIdValidator.validateInput(userInput);
        }
        return false;
    }
}
