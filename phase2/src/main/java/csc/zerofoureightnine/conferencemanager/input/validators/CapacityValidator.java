package csc.zerofoureightnine.conferencemanager.input.validators;

public class CapacityValidator implements Validator{
    public boolean validateInput(String userInput) {
        return userInput.trim().matches("[0-9]+");
    }
}
