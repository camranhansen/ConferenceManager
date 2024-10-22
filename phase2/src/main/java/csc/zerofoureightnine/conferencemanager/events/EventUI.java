package csc.zerofoureightnine.conferencemanager.events;


import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.messaging.MessageUI;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;


public class EventUI implements UISection {
    List<MenuNode> entryPoints;
    private EventActionHolder eventActionHolder;
    private EventPresenter eventPresenter;
    private EventInputValidator eventInputValidator;

    /**
     * Creates the {@link MessageUI} to allow user to interact with events.
     * @param eventActionHolder An {@link EventActionHolder}
     * @param eventPresenter An {@link EventPresenter}
     * @param eventInputValidator An {@link EventInputValidator}
     */
    public EventUI(EventActionHolder eventActionHolder, EventPresenter eventPresenter, EventInputValidator eventInputValidator) {
        this.eventActionHolder = eventActionHolder;
        this.eventPresenter = eventPresenter;
        this.eventInputValidator = eventInputValidator;
    }

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
     * Generate and add to the set of nodes that represents user editing event.
     */
    private void generateEditEventNodes() {
        String seqTitle = "Edit Event capacity";
        LinkedMenuNodeBuilder eventEditSeq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        eventEditSeq.addStep("event_id", eventPresenter::enterId, eventInputValidator::isValidID, eventPresenter::wrongInput);
        eventEditSeq.addStep("capacity", eventPresenter::enterCapacity, eventInputValidator::isValidEditCapacity, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder editEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::editCapacity);
        editEventNode.setCompletable(eventPresenter::eventEditConfirmation);
        entryPoints.add(eventEditSeq.build(editEventNode.build(), Permission.EVENT_EDIT));
    }

    /**
     * Generate the necessary steps to view all events that is hosting by the user who is interacting with the program.
     */
    private void generateViewHostingEventsNodes() {
        String seqTitle = "View All Hosting Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep(null, eventPresenter::renderHostingEventsToUser, null, null);
        MenuNode.MenuNodeBuilder showAllEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::viewMethod);
        entryPoints.add(seq.build(showAllEventNode.build(), Permission.VIEW_HOSTING_EVENTS));
    }

    /**
     * Generate all events that the user is attending.
     */
    private void generateViewAttendingEventNodes(){
        String seqTitle = "View Attending Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep(null, eventPresenter::renderAttendingEventsToUser, null, null);
        MenuNode.MenuNodeBuilder showAllEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::viewMethod);
        entryPoints.add(seq.build(showAllEventNode.build(), Permission.VIEW_ATTENDING_EVENTS));
    }

    /**
     * Generate all necessary steps that allow user to drop another user from an event.
     */
    private void generateEventOtherDropNodes() {
        String seqTitle = "Un-enroll other user from an event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventInputValidator::isValidID, eventPresenter::wrongInput);
        seq.addStep("target", eventPresenter::enterUsername, eventInputValidator::isValidUsername, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder dropEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::dropOther);
        dropEventNode.setCompletable(eventPresenter::eventUnEnrollConfirmation);
        entryPoints.add(seq.build(dropEventNode.build(), Permission.EVENT_OTHER_DROP));
    }

    /**
     * Generate all necessary steps that allow users to drop themselves from an event.
     */
    private void generateEventSelfDropNodes() {
        String seqTitle = "Un-enroll yourself from an event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventInputValidator::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder dropEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::dropSelf);
        dropEventNode.setCompletable(eventPresenter::eventUnEnrollConfirmation);
        entryPoints.add(seq.build(dropEventNode.build(), Permission.EVENT_SELF_DROP));
    }

    /**
     * Generate all events information to users.
     */
    private void generateViewAllEventNodes() {
        String seqTitle = "View All Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep(null, eventPresenter::renderAllEvents, null, null);
        MenuNode.MenuNodeBuilder showAllEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::viewMethod);
        entryPoints.add(seq.build(showAllEventNode.build(), Permission.VIEW_ALL_EVENTS));

    }

    /**
     * Generate necessary steps to show all available events to the user.
     */
    private void generateViewAvailableEventNodes(){
        String seqTitle = "View Available Events";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep(null, eventPresenter::renderAvailableEventsToUser, null, null);
        MenuNode.MenuNodeBuilder showAvailableEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::viewMethod);
        entryPoints.add(seq.build(showAvailableEventNode.build(), Permission.VIEW_AVAILABLE_EVENTS));

    }

    /**
     * Generate necessary steps to create a not existed event into the system.
     */
    private void generateEventCreateNodes() {
        String seqTitle = "Create An Event";
        LinkedMenuNodeBuilder eventCreateSeq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        eventCreateSeq.addStep("name", eventPresenter::enterEventName, null, null);
        eventCreateSeq.addStep("day", eventPresenter::enterDay, eventInputValidator::isValidDay, eventPresenter::wrongInput);
        eventCreateSeq.addStep("hour", eventPresenter::enterHour, eventInputValidator::isValidHour, eventPresenter::wrongInput);
        eventCreateSeq.addStep("room", eventPresenter::enterRoom, eventInputValidator::isValidRoom, eventPresenter::invalidRoom);
        eventCreateSeq.addStep("speakers", eventPresenter::enterSpeakerName, eventInputValidator::isValidSpeakers, eventPresenter::wrongInput);
        eventCreateSeq.addStep("capacity", eventPresenter::enterCapacity, eventInputValidator::isValidCapacity, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder createEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::createEvent);
        createEventNode.setCompletable(eventPresenter::eventCreateConfirmation);
        entryPoints.add(eventCreateSeq.build(createEventNode.build(), Permission.EVENT_CREATE));
    }

    /**
     * Generate necessary steps to delete a existed event.
     */
    private void generateEventDeleteNodes() {
        String seqTitle = "Delete an Event";
        LinkedMenuNodeBuilder eventDeleteSeq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        eventDeleteSeq.addStep("event_id", eventPresenter::enterId, eventInputValidator::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder deleteEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::deleteEvent);
        deleteEventNode.setCompletable(eventPresenter::eventDeleteConfirmation);
        entryPoints.add(eventDeleteSeq.build(deleteEventNode.build(), Permission.EVENT_DELETE));
    }

    /**
     * Generate necessary steps for users to enroll themselves.
     */
    public void generateEventSelfEnrollNodes() {
        String seqTitle = "Enroll in an Event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventInputValidator::isEnrollableEventID, eventPresenter::enrollConflict);
        MenuNode.MenuNodeBuilder enrollNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::enrollSelf);
        enrollNode.setCompletable(eventPresenter::eventEnrollConfirmation);
        entryPoints.add(seq.build(enrollNode.build(), Permission.EVENT_SELF_ENROLL));
    }

    /**
     * Generate necessary steps for users to enroll other user into a event.
     */
    public void generateEventOtherEnrollNodes() {
        String seqTitle = "Enroll other user in an event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventActionHolder.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventInputValidator::isEnrollableEventID, eventPresenter::wrongInput);
        seq.addStep("target", eventPresenter::enterUsername, eventInputValidator::isValidUsername, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder enrollOtherEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventActionHolder::enrollOther);
        enrollOtherEventNode.setCompletable(eventPresenter::eventEnrollConfirmation);
        entryPoints.add(seq.build(enrollOtherEventNode.build(), Permission.EVENT_OTHER_ENROLL));
    }

    @Override
    public String getSectionListing() {
        return "Events";
    }

}
