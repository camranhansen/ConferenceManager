package csc.zerofoureightnine.conferencemanager.menu;

import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import csc.zerofoureightnine.conferencemanager.users.UserManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EventInputPrompter extends InputPrompter{

    private EventManager eventManager;
    private EventInputPresenter eventInputPresenter;

    public EventInputPrompter(EventManager eventManager){
        super();
        this.eventManager = eventManager;
        this.eventInputPresenter = new EventInputPresenter();
    }

    public String canEnrollIn(String username){
        String eventId = enterEventIdPrompt();
        while (!canEnroll(eventId, username)){
            eventId = enterEventIdPrompt();
        }
        return eventId;
    }

    public Instant pickEventTime() {
        String date = dayOfMonth();
        String time = timeOfDay();
        return Instant.parse("2020-12-" + date.trim() + "T" + time.trim() + ":00:00.00Z");
    }

    //TODO: depending on how options work, this will need to be modified:
    public String pickSingleSpeaker(UserManager userManager, Instant time){
        eventInputPresenter.forSpeaker();
        String speaker = super.enterValidUsername(userManager);
        if (!isSpeaker(userManager, speaker)){
            eventInputPresenter.notSpeaker(speaker);
            speaker = super.enterValidUsername(userManager);
        }
        if (checkSpeakerConflict(speaker, time)){
            eventInputPresenter.isBooked("Speaker");
            speaker = super.enterValidUsername(userManager);
        }
        return speaker;
    }

    public String pickEventName(){
        return super.getResponse("Enter the name of the new event");
    }

    public String pickEventRoom(Instant time){
        String room = super.getResponse("Enter event room");
        while(eventManager.checkRoom(time, room)){
            eventInputPresenter.isBooked(room);
            room = super.getResponse("Enter event room");
        }
        return room;
    }

    public int pickEventCapacity(){
        String capacity = super.getResponse("Enter the capacity of this event");
        eventInputPresenter.capacityWarning();
        while(!capacity.trim().matches("[0-9]|[0-9][0-9]|[0-9][0-9][0-9]")){
            eventInputPresenter.invalidCap();
            capacity = super.getResponse("Enter the capacity of this event");
        }
        return Integer.parseInt(capacity.trim());
    }

//    //Idk if this is necessary: can just do username input check and then canEnrollIn in the controller class
//    public String enrollOtherPrompt(UserManager userManager){
//        String other = super.enterValidUsername(userManager);
//        return canEnrollIn(other);
//    }

    private String enterEventIdPrompt(){
        String id = super.getResponse("Enter event id");
        while (!eventManager.eventExists(id)){
            doesNotExist("This id ");
            id = super.getResponse("Enter event id");
        }
        return id;
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

    private boolean checkSpeakerConflict(String speaker, Instant time){
        List<String> speakers = new ArrayList<>();
        speakers.add(speaker);
        return eventManager.checkConflictSpeaker(time, speakers);
    }

    private boolean isSpeaker(UserManager userManager, String speaker){
        List<String> speakers = userManager.getUserByPermissionTemplate(Template.SPEAKER);
        return speakers.contains(speaker);
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
