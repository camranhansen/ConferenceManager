package Messaging;

import Events.EventManager;
import Menus.InputPrompter;
import Menus.Option;
import Menus.SubController;
import Users.Permission;
import Users.Template;
import Users.UserManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MessageController implements SubController {
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;
    private MessagePresenter messagePresenter;
    private InputPrompter inputPrompter;

    public MessageController(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messagePresenter = new MessagePresenter();
        this.inputPrompter = new InputPrompter();
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

        else if (permissionSelected == Permission.VIEW_OTHER_MESSAGES){
            String otherUsername = inputPrompter.getResponse("Enter username's messages you'd like to see");
            viewMessage(otherUsername);
        }
    }

    public void orgSendToAll(String username){
        String content = getContent();
        Option allAtt = new Option("Send to all attendees"){
            @Override
            public void run(){
                orgSendToAllAtt(username, content);
            }
        };
        Option allSpk = new Option("Send to all speakers") {
            @Override
            public void run(){
                orgSendToAllSpeakers(username, content);
            }
        };
        ArrayList<Option> options = new ArrayList<>();
        options.add(allAtt);
        options.add(allSpk);
        Option choice = inputPrompter.menuOption(options);
        choice.run();
    }

    public void writeMessage(String from) {
        String to = inputPrompter.getResponse("Enter username to send to");
        while(! userManager.userExists(to)){
            messagePresenter.usernameInvalid();
            to = inputPrompter.getResponse("Enter username to send to");
        }
        String content = getContent();
        messageManager.sendMessage(from, content, to);
    }

    public void messageEvents(String from){
        String content = getContent();
        //TODO: Move option list creation to helper?
        Option allEvents = new Option("Send to all your events"){
            @Override
            public void run(){
                writeToEvents(from, content, eventManager.getSpkEvents(from));
            }
        };
        Option oneEvent = new Option("Send to one of your events"){
            @Override
            public void run(){
                String eventId = inputPrompter.getResponse("Enter event id to send to");
                while(!eventManager.getEvents().containsKey(eventId)){
                    messagePresenter.noEvent();
                    eventId = inputPrompter.getResponse("Enter event id to send to");
                }
                if(!eventManager.getSpkEvents(from).contains(eventId)){
                    messagePresenter.notSpeakerEvent();
                }
                else{
                    List<String>aList = new ArrayList<>();
                    aList.add(eventId);
                    writeToEvents(from, content, aList);
                }
            }
        };
        ArrayList<Option> options = new ArrayList<>();
        options.add(allEvents);
        options.add(oneEvent);
        Option choice = inputPrompter.menuOption(options);
        choice.run();
    }

    public void viewMessage(String username){
        Option viewAll = new Option("View all your messages"){
            @Override
            public void run(){
                String messages = viewAllMessages(username);
                messagePresenter.printMessages(messages);
            }
        };
        Option viewOne = new Option("View messages from one user"){
            @Override
            public void run(){
                String otherUser = inputPrompter.getResponse("Enter other username messages you'd like to see");
                String messages = viewMessageFrom(username, otherUser);
                messagePresenter.printMessages(messages);
            }
        };
        ArrayList<Option> options = new ArrayList<>();
        options.add(viewAll);
        options.add(viewOne);
        Option choice = inputPrompter.menuOption(options);
        choice.run();
    }

    public String viewAllMessages(String username){
        return messageManager.wholeInboxToString(username);
    }

    public String viewMessageFrom(String username, String from) {
        return messageManager.singleInboxToString(username, from);
    }

    public void writeToEvents(String from, String message, List<String> events) {
        ArrayList<String> recipientsSum = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            recipientsSum.addAll(eventManager.getParticipants(events.get(i)));
        }
        String[] recipients = new String[recipientsSum.size()];
        if (!(recipients.length == 0)){
            messageManager.sendMessage(from, message, recipientsSum.toArray(recipients));
        }
        else{messagePresenter.noAttInEvent();}
    }

    public void orgSendToAllAtt(String from, String message){
        String[] attendees = getStringArray(userManager.getUserByPermissionTemplate(Template.ATTENDEE));
        List<String> list = new ArrayList<>(Arrays.asList(attendees));
        list.remove(from);
        attendees = list.toArray(new String[0]);
        if (!(attendees.length == 0)){
        messageManager.sendMessage(from, message, attendees);
        }
        else{messagePresenter.noAttendees();}
    }

    public void orgSendToAllSpeakers(String from, String message){
        String[] speakers = getStringArray(userManager.getUserByPermissionTemplate(Template.SPEAKER));
        List<String> list = new ArrayList<>(Arrays.asList(speakers));
        list.remove(from);
        speakers = list.toArray(new String[0]);
        if (!(speakers.length == 0)){
            messageManager.sendMessage(from, message, speakers);
        }
        else{
        messagePresenter.noSpeakers();
        }
    }

    private String getContent(){
        return inputPrompter.getResponse("Enter text");
    }

    private String[] getStringArray(List<String> list) {
        String[] res = new String[list.size()];
        list.toArray(res);
        return res;
    }

//    private int getValidInput(int options) {
//        while (true) {
//            messagePresenter.enterContent();
//            String input = message    Scanner.nextLine();
//            if (input.matches("^[0-" + (options + 1) + "]$")) {
//                return Integer.parseInt(input);
//            } else {
//                messagePresenter.errorMessage();
//            }
//        }
//    }

    //TODO: Encapsulation of viewing messages.


}
