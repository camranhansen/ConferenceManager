package csc.zerofoureightnine.conferencemanager.input.validators;

public class EventHourValidator implements Validator{
    public boolean validateInput(String userInput) {
        return userInput.trim().matches("[9]|[1][0-6]");
    }
}
