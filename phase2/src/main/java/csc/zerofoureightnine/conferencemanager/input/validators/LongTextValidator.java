package csc.zerofoureightnine.conferencemanager.input.validators;

public class LongTextValidator implements Validator{
    //Note: message content now has a cap of 5000 chars TODO: add max character length into javadoc for message
    public boolean validateInput(String userInput) {
        return userInput.length() < 5000;
    }
}
