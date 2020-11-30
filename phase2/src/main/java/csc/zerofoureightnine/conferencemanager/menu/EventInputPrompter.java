package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.users.PermissionManager;
import csc.zerofoureightnine.conferencemanager.users.User;
import csc.zerofoureightnine.conferencemanager.users.UserManager;

import java.time.Instant;

public class EventInputPrompter extends InputPrompter{

    private EventManager eventManager;
    private UserManager userManager;
    private PermissionManager permissionManager;
    private EventInputPresenter eventInputPresenter;
    private String username;

    public EventInputPrompter(EventManager eventManager, UserManager userManager, PermissionManager permissionManager,
                              String username){
        super();
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.permissionManager = permissionManager;
        this.eventInputPresenter = new EventInputPresenter();
        this.username = username;
    }
    public void createEventPrompt(){}

    public String canEnrollIn(String username){
        String eventId = enterEventIdPrompt();
        while (!canEnroll(eventId, username)){
            eventId = enterEventIdPrompt();
        }
        return eventId;
    }

    private boolean canEnroll(String eventId, String username){
        Instant timeSlot = eventManager.getEventTime(eventId);
        if(eventManager.getParticipants(eventId).contains(username)){
            eventInputPresenter.alreadyEnrolled();
            return false;
        }
        if(eventManager.getParticipants(eventId).size() == eventManager.getCapacity(eventId)){
            eventInputPresenter.isFull();
            return false;
        }
        if(eventManager.checkUserInEvent(timeSlot, username)){
            eventInputPresenter.enrolledAtSameTime();
            return false;
        }
        return true;
    }

    //Idk if this is necessary: can just do username input check and then canEnrollIn in the controller class
    public String enrollOtherPrompt(UserManager userManager){
        String other = super.enterValidUsername(userManager);
        return canEnrollIn(other);
    }

    public void eventEditPrompt(){}

    private String enterEventIdPrompt(){
        String id = super.getResponse("Enter event id");
        while (!eventManager.eventExists(id)){
            doesNotExist("This id ");
            id = super.getResponse("Enter event id");
        }
        return id;
    }

    private boolean checkUserPermission(){
        return false;
    }

    public Instant getEventTime() {
        String date = dayOfMonth();
        String time = timeOfDay();
        return Instant.parse("2020-12-" + date.trim() + "T" + time.trim() + ":00:00.00Z");
    }

    private String dayOfMonth(){
        String date = super.getResponse("Enter the day of the month");
        while (!date.trim().matches("[0-2][0-9]|[3][0-1]|[1-9]")) {
            date = super.getResponse("The date you entered is invalid.\nPlease enter a new day of the month");
        }
        if (date.length() == 1) {
            date = "0" + date;
        }
        return date;
    }

    private String timeOfDay(){
        String time = super.getResponse("Chose the time slot from 9 to 16 on a 24-hour clock");
        while (!time.trim().matches("[9]|[1][0-6]")) {
            time = super.getResponse(
                    "The time slot you chose is invalid." + System.lineSeparator() +
                            "Please enter a new time slot");
        }
        if (time.length() == 1) {
            time = "0" + time;
        }
        return time;
    }
}
