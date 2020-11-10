package Messaging;

import Events.EventManager;
import Menus.SubController;
import Users.Permission;
import Users.Template;
import Users.UserManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class MessageController implements SubController {
    //Potentially move eventmanager and usermanager to menu?
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;
    private MessagePresenter messagePresenter;
    private Scanner messageScanner;

    //TODO: handle incorrect input
    public MessageController(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messagePresenter = new MessagePresenter();
        this.messageScanner = new Scanner(System.in);
    }

    public void performSelectedAction(String username, Permission permissionSelected) {
        if (permissionSelected == Permission.MESSAGE_ALL_USERS){
            orgSendToAll(username);
        }
        else if (permissionSelected == Permission.MESSAGE_SINGLE_USER){
            writeMessage(username);
        }
        else if (permissionSelected == Permission.MESSAGE_EVENT_USERS){
            messageEvents(username);
        }
        else if (permissionSelected == Permission.VIEW_SELF_MESSAGES){
            viewMessage(username);
        }
    }

    //TODO: change to string
    public void orgSendToAll(String username){
        messagePresenter.sendToAll();
        int attOrSpk = messageScanner.nextInt();
        messagePresenter.enterContent();
        String content = messageScanner.nextLine();
        if (attOrSpk == 1){
            orgSendToAllAtt(username, content);
        }
        else if (attOrSpk == 2){
            orgSendToAllSpeakers(username,content);
        }
    }

    public void writeMessage(String from) {
        messagePresenter.enterUsername();
        String to = messageScanner.nextLine();
        messagePresenter.enterContent();
        String message = messageScanner.nextLine();
        messageManager.sendMessage(from, message, to);
    }

    //TODO: Change to string instead of int
    public void messageEvents(String from){
        messagePresenter.sendToEvents();
        int allOrOne = messageScanner.nextInt();
        messagePresenter.enterContent();
        String content = messageScanner.nextLine();
        if (allOrOne == 1){
            Integer[] eventIds = eventManager.getSpkEvents(from);
            writeToEvents(from, content, eventIds);
        }
        else if (allOrOne == 2){
            messagePresenter.sendToOneEvent();
            int eventId = messageScanner.nextInt();
            writeToEvents(from, content, eventId);
        }
    }

    public void viewMessage(String username){
        messagePresenter.viewAllOrFromOne();
        int allOrOne = messageScanner.nextInt();
        if (allOrOne == 1){
            System.out.println(viewSentMessage(username));
        }
        else if (allOrOne == 2){
            messagePresenter.enterUsername();
            String otherUser = messageScanner.nextLine();
            System.out.println(viewMessageFrom(username, otherUser));
        }
    }

    public void writeMessage(String from, String to, String message) {
        messageManager.sendMessage(from, message, to);
    }

    public HashMap<String, List<Message>> viewSentMessage(String to){
        return messageManager.retrieveUserInbox(to);
    }

    public List<Message> viewMessageFrom(String username, String from){
        return messageManager.retrieveUserInboxFor(username, from);
    }

    public List<Message> viewMessageSentFrom(String to, String from) {
        return messageManager.retrieveUserInboxFor(to, from);
    }

    public void writeToEvents(String from, String message, Integer... events) {
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
