package csc.zerofoureightnine.conferencemanager.events;


import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class EventUI implements UISection {
    List<MenuNode> entryPoints;
    private EventController eventController;
    private EventPresenter eventPresenter;

    public EventUI(EventController eventController, EventPresenter eventPresenter) {
        this.eventController = eventController;
        this.eventPresenter = eventPresenter;
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {

        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();
        generateEventCreateNodes();
        generateEventDeleteNodes();
        generateEventSelfEnrollNodes();
        return entryPoints;


    }


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
        entryPoints.add(eventCreateSeq.build(createEventNode.build(), Permission.EVENT_CREATE));
    }

    private void generateEventDeleteNodes() {
        String seqTitle = "Delete an Event";
        LinkedMenuNodeBuilder eventDeleteSeq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        eventDeleteSeq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder deleteEventNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::deleteEvent);
        entryPoints.add(eventDeleteSeq.build(deleteEventNode.build(), Permission.EVENT_DELETE));
    }

    public void generateEventSelfEnrollNodes() {
        String seqTitle = "Enroll in an Event";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
        seq.addStep("event_id", eventPresenter::enterId, eventController::isValidID, eventPresenter::wrongInput);
        MenuNode.MenuNodeBuilder enrollNode = new MenuNode.MenuNodeBuilder(seqTitle, eventController::enrollSelf);
        entryPoints.add(seq.build(enrollNode.build(), Permission.EVENT_SELF_ENROLL));
    }

//    public void generateShowUserEventNodes(){
//        String seqTitle = "Check your enrolled events";
//        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, eventController.getInputMap());
//        MenuNode.MenuNodeBuilder showNode = new MenuNode.MenuNodeBuilder(seqTitle);
//        entryPoints.add(seq.build(enrollNode.build(), Permission.EVENT_SELF_ENROLL));
//    }

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