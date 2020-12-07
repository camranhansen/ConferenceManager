package csc.zerofoureightnine.conferencemanager.input.validators;

public class ShortTextValidator implements Validator{
    public boolean validateInput(String userInput)  {
        return userInput.length() < 150;
    }
}
