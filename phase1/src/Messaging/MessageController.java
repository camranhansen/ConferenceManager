package Messaging;

import Events.EventManager;
import Users.Template;
import Users.UserManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MessageController {
    //Potentially move eventmanager and usermanager to menu?
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;

    public MessageController(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    public void writeMessage(String from, String to, String message) {
        messageManager.sendMessage(from, message, to);
    }

    public HashMap<String, List<Message>> viewSentMessage(String username){
        return messageManager.retrieveUserInbox(username);
    }

    public void writeToEvents(String from, String message, int... events) {
        ArrayList<String> recipientsSum = new ArrayList<>();
        for (int i = 0; i < events.length; i++) {
            recipientsSum.addAll(eventManager.getParticipants(events[i]));
        }
        String[] recipients = new String[recipientsSum.size()];
        messageManager.sendMessage(from, message, recipientsSum.toArray(recipients));
    }

    public void orgSendToAllAtt(String from, String message){
        messageManager.sendMessage(from,
                message,
                getStringArray(userManager.getUserByPermissionTemplate(Template.ATTENDEE)));
    }

    private String[] getStringArray(List<String> list) {
        String[] res = new String[list.size()];
        list.toArray(res);
        return res;
    }

    //public void orgSendToAllSpeakers(String from, String message){
       // String[] speakers = userManager.getAllSpeakers(); //TODO: Give us a list of "speakers".
       // messageManager.sendMessage(from, message, speakers);
    //}

    //TODO: Encapsulation of viewing messages.
}
