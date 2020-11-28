package csc.zerofoureightnine.conferencemanager.messaging;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.menu.InputPrompter;
import csc.zerofoureightnine.conferencemanager.menu.Option;
import csc.zerofoureightnine.conferencemanager.menu.SubController;
import csc.zerofoureightnine.conferencemanager.users.Permission;
import csc.zerofoureightnine.conferencemanager.users.Template;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MessageController implements SubController {
    private MessageManager messageManager;
    private UserManager userManager;
    private EventManager eventManager;
    private MessagePresenter messagePresenter;
    private InputPrompter inputPrompter;
    private boolean exiting;

    /**
     * Creates a new MessageController with message manger messageManger,
     *  user manager userManger and event manager eventManger.
     *
     * @param messageManager Manger of the messages.
     * @param userManager Manger of the csc.zerofoureightnine.conferencemanager.users.
     * @param eventManager Manger of the csc.zerofoureightnine.conferencemanager.events.
     */

    public MessageController(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
        this.eventManager = eventManager;
        this.messagePresenter = new MessagePresenter();
        this.inputPrompter = new InputPrompter();
        this.inputPrompter.attach(this);
        this.exiting = false;
    }

    /**
     * Directs the permission permissionSelected selected by
     * the user username to further function.
     *
     * @param username Username of the current user.
     * @param permissionSelected Permission the user would like to pursue.
     */
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
            if(!this.exiting){
            viewMessage(otherUsername);}
            this.exiting = false;
        }
    }

    public void exitEarly(){
        this.exiting = true;
    }

    /**
     * Takes in the message content from the user username.
     * Gives the user the option to send a message to all attendees or all speakers.
     * Performs the selected option.
     *
     * @param username Username of the current user.
     */
    public void orgSendToAll(String username){
        String content = getContent();
        if(!this.exiting){
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
        this.exiting = false;
    }

    /**
     * Takes in the username to send a message to.
     * Takes in content of the message and sends it from from.
     *
     * @param from Username of the current user.
     */
    public void writeMessage(String from) {
        String to = inputPrompter.getResponse("Enter username to send to");
        if(!this.exiting){
        while(!userManager.userExists(to)&&!this.exiting){
            messagePresenter.usernameInvalid();
            to = inputPrompter.getResponse("Enter username to send to");
        }

        String content = getContent();
        if(!this.exiting){
            messageManager.sendMessage(from, content, to);}}
        this.exiting = false;
    }

    /**
     * Takes in content. Gives the user from the option to send
     * to all their csc.zerofoureightnine.conferencemanager.events or one of them and sends message.
     *
     * @param from Username of the current user.
     */
    public void messageEvents(String from){
        String content = getContent();
        if(!this.exiting){
            Option allEvents = new Option("Send to all your csc.zerofoureightnine.conferencemanager.events"){
            @Override
            public void run(){
                writeToEvents(from, content, getSpeakerEventIds(from));
            }
        };
            Option oneEvent = new Option("Send to one of your csc.zerofoureightnine.conferencemanager.events"){
                @Override
                public void run(){
                    String eventId = inputPrompter.getResponse("Enter event id to send to");
                    while(!eventManager.getEvents().containsKey(eventId)&&!exiting){
                        messagePresenter.noEvent();
                        eventId = inputPrompter.getResponse("Enter event id to send to");
                    }
                    if(!getSpeakerEventIds(from).contains(eventId)&&!exiting){
                        messagePresenter.notSpeakerEvent();
                    }
                    else if(!exiting){
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
        this.exiting = false;
    }

    /**
     * Gives the user username the option to view all their messages or messages
     * from one other username. Shows them the messages based on their selection.
     *
     * @param username Username of the current user.
     */
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
                if(!exiting){
                String messages = viewMessageFrom(username, otherUser);
                messagePresenter.printMessages(messages);
                }
            }
        };
        ArrayList<Option> options = new ArrayList<>();
        options.add(viewAll);
        options.add(viewOne);
        Option choice = inputPrompter.menuOption(options);
        choice.run();
        this.exiting = false;
    }

    /**
     * Returns the entire inbox of messages of the user username.
     *
     * @param username Username of the current user.
     * @return User's entire inbox of messages.
     */
    public String viewAllMessages(String username){
        return messageManager.wholeInboxToString(username);
    }

    /**
     * Returns the messages from from for the username username.
     *
     * @param username Username of the current user.
     * @param from Username of the sender of the messages.
     * @return User's inbox from specific other username.
     */
    public String viewMessageFrom(String username, String from) {
        return messageManager.singleInboxToString(username, from);
    }

    /**
     * Sends a message with content message from from to the csc.zerofoureightnine.conferencemanager.events csc.zerofoureightnine.conferencemanager.events.
     *
     * @param from Username of the current user.
     * @param message Content of the message
     * @param events Events to send a message to
     */
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

    /**
     * Sends a message with content message to all attendees from from.
     *
     * @param from Username of the current user.
     * @param message Content of the message.
     */
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

    /**
     * Sends a message with content message to all speakers from from.
     *
     * @param from Username of the current user.
     * @param message Content of the message.
     */
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

    private List<String> getSpeakerEventIds(String username){
        List<String> eventIds = eventManager.getAllEventIds();
        List<String> speakersEvents = new ArrayList<>();
        for (String id: eventIds){
            if(eventManager.getEventSpeakerName(id).equals(username)){
                speakersEvents.add(id);
            }
        }
        return speakersEvents;
    }

    private String getContent(){
        return inputPrompter.getResponse("Enter text");
    }

    private String[] getStringArray(List<String> list) {
        String[] res = new String[list.size()];
        list.toArray(res);
        return res;
    }
}
