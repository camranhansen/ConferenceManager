package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.presentation.TopicPresentable;
import csc.zerofoureightnine.conferencemanager.users.permission.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;

import java.util.HashMap;
import java.util.List;

public class UserController {

    private UserManager um;
    private PermissionManager pm;
    private HashMap<String, String> inputMap = new HashMap<>();


    public UserController(UserManager um, PermissionManager pm) {
        this.um = um;
        this.pm = pm;
    }

//    public boolean validatorTemplate(String input, List<TopicPresentable> options) {
//      Return true if the validation check is sucessful
//    }

//TODO edit password, other_edit_password, create_account, create_speaker_account, delete_account,

    public int editPassword(String username, String input, List<TopicPresentable> opts) {
        um.setPassword(username, inputMap.get("password"));
        return 0;
    }

//Template.values()[Integer.parseInt(inputMap.get("template"))]
    public HashMap<String, String> getInputMap() {
        return inputMap;
    }
}
