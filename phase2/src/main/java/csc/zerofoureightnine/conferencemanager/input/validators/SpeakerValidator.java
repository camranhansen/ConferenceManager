package csc.zerofoureightnine.conferencemanager.input.validators;

import csc.zerofoureightnine.conferencemanager.users.UserManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import java.util.List;

public class SpeakerValidator implements Validator{
    private UserManager userManager;

    public SpeakerValidator(UserManager um){
        userManager = um;
    }

    public boolean validateInput(String userInput) {
        List<String> speakers = userManager.getUserByPermissionTemplate(Template.SPEAKER);
        return speakers.contains(userInput);
    }
}
