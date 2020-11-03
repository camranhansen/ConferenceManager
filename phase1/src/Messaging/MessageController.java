package Messaging;

import java.util.ArrayList;
import java.util.Arrays;

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

    public void writeToAttendees(String from, String message, int... events) {
        ArrayList<String> recipientsSum = new ArrayList<>();
        for (int i = 0; i < events.length; i++) {
            recipientsSum.addAll(Arrays.asList(eventManager.viewParticipantsByEvent(events[i])));
        }
        String[] recipients = new String[recipientsSum.size()];
        messageManager.sendMessage(from, message, recipientsSum.toArray(recipients));
    }

}
