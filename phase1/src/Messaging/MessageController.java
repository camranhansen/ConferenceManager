package Messaging;

import Events.EventManager;
import Users.LoginPresenter;
import Users.Template;
import Users.UserManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MessageController {
    //Potentially move eventmanager and usermanager to menu?
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;
    private MessagePresenter messagePresenter;

    public MessageController(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messagePresenter = new MessagePresenter();
    }

    public void writeMessage(String from, String to, String message) {
        messageManager.sendMessage(from, message, to);
    }

    public HashMap<String, List<Message>> viewSentMessage(String to){
        return messageManager.retrieveUserInbox(to);
    }

    public List<Message> viewMessageSentFrom(String to, String from) {
        return messageManager.retrieveUserInboxFor(to, from);
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

    public void orgSendToAllSpeakers(String from, String message){
        messageManager.sendMessage(from, message,
                getStringArray(userManager.getUserByPermissionTemplate(Template.SPEAKER)));
    }

    //TODO: Encapsulation of viewing messages.


}
