package csc.zerofoureightnine.conferencemanager.input;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.input.validators.*;
import csc.zerofoureightnine.conferencemanager.options.*;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputStrategyManager {
    private EventDayValidator eventDayValidator;
    private EventHourValidator eventHourValidator;
    private CapacityValidator capacityValidator;
    private LongTextValidator longTextValidator;
    private ExistingUserValidator existingUserValidator;
    private SpeakerValidator speakerValidator;
    private EventIdValidator eventIdValidator;
    private EventRoomValidator eventRoomValidator;
    private EventEnrollOption eventEnrollOption;
    private EventViewOptions eventViewOptions;
    private MenuCategoryOption menuCategoryOption;
    private MessageEventOption messageEventOption;
    private MessageViewOption messageViewOptions;
    private MessageAllOption messageAllOption;
    private PermissionMenuOption permissionMenuOption;
    private UserTemplateOption userTemplateOption;
    private ShortTextValidator shortTextValidator;
    private MessageMoveOption messageMoveOption;

    public InputStrategyManager(UserManager um, EventManager em, PermissionManager pm,
                                Map<InputStrategy, String> inputHistory, String username){
        eventDayValidator = new EventDayValidator();
        eventHourValidator = new EventHourValidator();
        capacityValidator = new CapacityValidator();
        longTextValidator = new LongTextValidator();
        shortTextValidator = new ShortTextValidator();
        existingUserValidator = new ExistingUserValidator(um);
        speakerValidator = new SpeakerValidator(pm, em, inputHistory);
        eventIdValidator = new EventIdValidator(em);
        eventRoomValidator = new EventRoomValidator(em, inputHistory);
        eventEnrollOption = new EventEnrollOption();
        eventViewOptions = new EventViewOptions();
        menuCategoryOption = new MenuCategoryOption();
        messageEventOption = new MessageEventOption();
        messageViewOptions = new MessageViewOption();
        permissionMenuOption = new PermissionMenuOption(inputHistory, um, username);
        userTemplateOption = new UserTemplateOption();
        messageAllOption = new MessageAllOption();
        messageMoveOption = new MessageMoveOption();

    }

    public boolean validate(InputStrategy inputStrategy, String userInput){
        if(inputStrategy.isMenu()){
            return validateMenu(inputStrategy, userInput);
        }
        return validateSingleInput(inputStrategy, userInput);
    }

    private boolean validateSingleInput(InputStrategy inputStrategy, String userInput){
        switch(inputStrategy){
            case VALID_USERNAME:
                return existingUserValidator.validateInput(userInput);
            case EVENT_DAY:
                return eventDayValidator.validateInput(userInput);
            case EVENT_HOUR:
                return eventHourValidator.validateInput(userInput);
            case EVENT_CAPACITY:
                return capacityValidator.validateInput(userInput);
            case LONG_TEXT:
                return longTextValidator.validateInput(userInput);
            case SHORT_TEXT:
                return shortTextValidator.validateInput(userInput);
            case EVENT_SPEAKER_SINGLE:
                return speakerValidator.validateInput(userInput);
            case VALID_EVENT_ID:
                return eventIdValidator.validateInput(userInput);
            case EVENT_ROOM:
                return eventRoomValidator.validateInput(userInput);
        }
        return false;
    }

    private boolean validateMenu(InputStrategy inputStrategy, String userInput){
        switch(inputStrategy){
            case USER_TEMPLATE_OPTIONS:
                return validOptionSelection(userTemplateOption.generateOptions(), userInput);
            case ENROLL_OPTIONS:
                return validOptionSelection(eventEnrollOption.generateOptions(), userInput);
            case VIEWING_EVENT_OPTIONS:
                return validOptionSelection(eventViewOptions.generateOptions(), userInput);
            case CATEGORY_MENU:
                return validOptionSelection(menuCategoryOption.generateOptions(), userInput);
            case PERMISSION_MENU:
                return validOptionSelection(permissionMenuOption.generateOptions(), userInput);
            case SEND_ALL_OPTIONS:
                return validOptionSelection(messageAllOption.generateOptions(), userInput);
            case VIEWING_MESSAGE_OPTIONS:
                return validOptionSelection(messageViewOptions.generateOptions(), userInput);
            case MESSAGE_EVENT_OPTIONS:
                return validOptionSelection(messageEventOption.generateOptions(), userInput);
            case MOVE_MESSAGE:
                return validOptionSelection(messageMoveOption.generateOptions(), userInput);
        }
        return false;
    }

    public List<Option> getOptions(InputStrategy inputStrategy){
        switch(inputStrategy){
            case USER_TEMPLATE_OPTIONS:
                return userTemplateOption.generateOptions();
            case ENROLL_OPTIONS:
                return eventEnrollOption.generateOptions();
            case VIEWING_EVENT_OPTIONS:
                return eventViewOptions.generateOptions();
            case CATEGORY_MENU:
                return menuCategoryOption.generateOptions();
            case PERMISSION_MENU:
                return permissionMenuOption.generateOptions();
            case SEND_ALL_OPTIONS:
                return messageAllOption.generateOptions();
            case VIEWING_MESSAGE_OPTIONS:
                return messageViewOptions.generateOptions();
            case MESSAGE_EVENT_OPTIONS:
                return messageEventOption.generateOptions();
            case MOVE_MESSAGE:
                return messageMoveOption.generateOptions();
        }
        List<Option> noOptions = new ArrayList<>();
        noOptions.add(new Option("no options"));
        return noOptions;
    }

    private boolean validOptionSelection(List<Option> options, String userInput){
        return userInput.matches("^[0-9]+$") && Integer.parseInt(userInput) < options.size();
    }
}
