package csc.zerofoureightnine.conferencemanager.input.validators;

public class EventDayValidator implements Validator{
    public boolean validateInput(String userInput) {
        return userInput.trim().matches("[0-2][0-9]|[3][0-1]|[1-9]");
    }
}
