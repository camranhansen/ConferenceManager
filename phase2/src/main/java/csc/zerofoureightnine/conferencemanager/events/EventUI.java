package csc.zerofoureightnine.conferencemanager.events;


import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.messaging.MessageController;
import csc.zerofoureightnine.conferencemanager.messaging.MessagePresenter;
import csc.zerofoureightnine.conferencemanager.messaging.MessageUI;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class EventUI implements UISection {
    List<MenuNode> entryPoints;
    private EventController eventController;
    private EventPresenter eventPresenter;

    /**
     * Creates the {@link EventUI} to allow user to interact with messaging.
     * @param eventController An {@link EventController}
     * @param eventPresenter An {@link EventPresenter}
     */
    public EventUI(EventController eventController, EventPresenter eventPresenter) {
        this.eventController = eventController;
        this.eventPresenter = eventPresenter;
    }

    /**
     * Create a new arraylist that display all the options to user
     * @return list of options that allow users to access
     */
    @Override
    public List<MenuNode> getEntryMenuNodes() {

        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();
        generateEventCreateNodes();
        generateEventDeleteNodes();
        generateEventSelfEnrollNodes();
        generateViewAllEventNodes();
        generateViewAvailableEventNodes();
        generateEventSelfDropNodes();
        generateEventOtherDropNodes();
        generateViewAttendingEventNodes();
        generateViewHostingEventsNodes();
        generateEventOtherEnrollNodes();
        generateEditEventNodes();
        return entryPoints;


    }

    /**
     * Creates the {@link EventController} to allow user to edit the time for a specific event.
     */
    private void generateEditEventNodes(){
        String seqTitle = "Edit Event capacity";
        LinkedMenuNodeBuilder eventEditSeq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        eventEditSeq.addStep("capacity", eventPresenter::enterCapacity, eventController::isValidCapacity, eventPresenter::wrongInput);
        eventEditSeq.addStep("capacity", eventPresenter::enterCapacity, eventController::isValidEditCapacity, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder createEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::editCapacity);
        entryPoints.add(eventEditSeq.build(createEventNode.build(), Permission.EVENT_EDIT));
    }

    /**
     * Creates the {@link EventController} to allow user to view all events they are currently hosting.
     */
    private void generateViewHostingEventsNodes(){
        String seqTitle = "View All Hosting Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("username", eventPresenter::enterUsername, eventController::isValidUsername, eventPresenter::wrongInput);
        seq.addStep(null, eventPresenter::renderHostingEventsToUser, null, null);
        MenuNode.MenuNodeBuilder showAllEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::viewMethod);
        entryPoints.add(seq.build(showAllEventNode.build(), Permission.VIEW_HOSTING_EVENTS));
    }

    /**
     * Creates the {@link EventController} to allow user to view all events they are currently attending.
     */
    private void generateViewAttendingEventNodes(){
        String seqTitle = "View All Your Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("username", eventPresenter::enterUsername, eventController::isValidUsername, eventPresenter::wrongInput);
        seq.addStep(null, eventPresenter::renderAttendingEventsToUser, null, null);
        MenuNode.MenuNodeBuilder showAllEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::viewMethod);
        entryPoints.add(seq.build(showAllEventNode.build(), Permission.VIEW_ATTENDING_EVENTS));

    }

    /**
     * Creates the {@link EventController} to allow user to have the option to drop a event for another user.
     */
    private void generateEventOtherDropNodes(){
        String seqTitle = "Un-enroll other user from an event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        seq.addStep("target", eventPresenter::enterUsername, eventController::isValidUsername, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder dropEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::dropOther);
        entryPoints.add(seq.build(dropEventNode.build(), Permission.EVENT_OTHER_DROP));
    }

    /**
     * Creates the {@link EventController} to allow users to drop themselves from the event.
     */
    private void generateEventSelfDropNodes(){
        String seqTitle = "Un-enroll yourself from an event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder dropEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::dropSelf);
        entryPoints.add(seq.build(dropEventNode.build(), Permission.EVENT_SELF_DROP));
    }

    /**
     * Creates the {@link EventController} to allow users to view all events.
     */
    private void generateViewAllEventNodes() {
        String seqTitle = "View All Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep(null, eventPresenter::renderAllEvents, null, null);
        MenuNode.MenuNodeBuilder showAllEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::viewMethod);
        entryPoints.add(seq.build(showAllEventNode.build(), Permission.VIEW_ALL_EVENTS));

    }

    /**
     * Creates the {@link EventController} to allow users to view all available events.
     */
    private void generateViewAvailableEventNodes(){
        String seqTitle = "View Available Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("username", eventPresenter::enterUsername, eventController::isValidUsername, eventPresenter::wrongInput);
        seq.addStep(null, eventPresenter::renderAvailableEventsToUser, null, null);
        MenuNode.MenuNodeBuilder showAvailableEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::viewMethod);
        entryPoints.add(seq.build(showAvailableEventNode.build(), Permission.VIEW_AVAILABLE_EVENTS));

    }

    /**
     * Creates the {@link EventController} to allow users to create events.
     */
    private void generateEventCreateNodes() {
        String seqTitle = "Create An Event";
        LinkedMenuNodeBuilder eventCreateSeq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        eventCreateSeq.addStep("name", eventPresenter::enterEventName, null, null);
        eventCreateSeq.addStep("day", eventPresenter::enterDay, eventController::isValidDay, eventPresenter::wrongInput);
        eventCreateSeq.addStep("hour", eventPresenter::enterHour, eventController::isValidHour, eventPresenter::wrongInput);
        eventCreateSeq.addStep("room", eventPresenter::enterRoom, eventController::isValidRoom, eventPresenter::wrongInput);
        eventCreateSeq.addStep("speakers", eventPresenter::enterSpeakerName, eventController::isValidSpeakers, eventPresenter::wrongInput);
        eventCreateSeq.addStep("capacity", eventPresenter::enterCapacity, eventController::isValidCapacity, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder createEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::createEvent);
        createEventNode.setCompletable(eventPresenter::eventCreateConfirmation);
        entryPoints.add(eventCreateSeq.build(createEventNode.build(), Permission.EVENT_CREATE));
    }

    /**
     * Creates the {@link EventController} to allow users to delete events.
     */
    private void generateEventDeleteNodes() {
        String seqTitle = "Delete an Event";
        LinkedMenuNodeBuilder eventDeleteSeq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        eventDeleteSeq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder deleteEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::deleteEvent);
        entryPoints.add(eventDeleteSeq.build(deleteEventNode.build(), Permission.EVENT_DELETE));
    }

    /**
     * Creates the {@link EventController} to allow users to enroll themselves into events.
     */
    public void generateEventSelfEnrollNodes() {
        String seqTitle = "Enroll in an Event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder enrollNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::enrollSelf);
        entryPoints.add(seq.build(enrollNode.build(), Permission.EVENT_SELF_ENROLL));
    }

    /**
     * Creates the {@link EventController} to allow users to enroll other users into events.
     */
    public void generateEventOtherEnrollNodes(){
        String seqTitle = "enroll other user to an event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        seq.addStep("target", eventPresenter::enterUsername, eventController::isValidUsername, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder dropEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::enrollOther);
        entryPoints.add(seq.build(dropEventNode.build(), Permission.EVENT_OTHER_ENROLL));
    }


    public void generateShowAvailableUserEventNodes(){
        String seqTitle = "Check available events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder enrollNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::enrollSelf);
        entryPoints.add(seq.build(enrollNode.build(), Permission.EVENT_SELF_ENROLL));

    }



    @Override
    public String getSectionListing() {
        return "Events";
    }

}
