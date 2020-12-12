package csc.zerofoureightnine.conferencemanager.users;

import csc.zerofoureightnine.conferencemanager.interaction.MenuNode;
import csc.zerofoureightnine.conferencemanager.interaction.control.UISection;
import csc.zerofoureightnine.conferencemanager.interaction.utils.LinkedMenuNodeBuilder;
import csc.zerofoureightnine.conferencemanager.users.permission.Permission;
import csc.zerofoureightnine.conferencemanager.users.permission.Template;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestController;
import csc.zerofoureightnine.conferencemanager.users.specialrequest.SpecialRequestPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserUI implements UISection {
    private UserController userController;
    private UserPresenter userPresenter;
    private SpecialRequestController specialRequestController;
    private SpecialRequestPresenter specialRequestPresenter;
    List<MenuNode> entryPoints;

    public UserUI(UserController userController, UserPresenter userPresenter, SpecialRequestController specialRequestController, SpecialRequestPresenter specialRequestPresenter) {
        this.userController = userController;
        this.userPresenter = userPresenter;
        this.specialRequestController = specialRequestController;
        this.specialRequestPresenter = specialRequestPresenter;
    }

    @Override
    public List<MenuNode> getEntryMenuNodes() {
        if (entryPoints != null)
            return entryPoints;
        entryPoints = new ArrayList<>();
        generateCreateAccountNodes();
        generateOtherEditPasswordNodes();
        generateDeleteSelfAccountNodes();
        generateDeleteOtherAccountNodes();
        generateCreateSpecialRequestNodes();
        generateAddressSpecialRequestNodes();
        generateDeleteSpecialRequestNodes();
        generateViewSelfSpecialRequestNodes();
        generateViewPendingSpecialRequestNodes();
        generateViewAddressedSpecialRequestNodes();
        //put generators here.
        return entryPoints;
    }

    @Override
    public String getSectionListing() {
        return "Account Management";
    }

    private void generateCreateAccountNodes(){
        String seqTitle = "Create a new User Account from a Template";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, userController.getInputMap());
        seq.addStep("username", userPresenter::enterUsername, userController::isNotValidUser, userPresenter::userExists);
        seq.addStep("password", userPresenter::enterPassword, null, userPresenter::wrongInput);
        List<String> options = new ArrayList<>();
        Arrays.asList(Template.values()).forEach(t -> options.add(t.toString()));
        seq.addMultipleOptions("template", options, null);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, userController::createAccount);
        entryPoints.add(seq.build(Node.build(), Permission.USER_CREATE_ACCOUNT));
    }


//    private void generateOtherEditPermissionNodes(){
//        String seqTitle = "Edit another User's Permission";
//        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, userController.getInputMap());
//        seq.addStep("username", userPresenter::enterUsername, userController::isValidUser, userPresenter::wrongInput);
//        List<String> options = new ArrayList<>();
//        Arrays.asList(Permission.values()).forEach(p -> options.add(p.toString()));
//        seq.addMultipleOptions("permission", options, null);
//        entryPoints.add(seq.build(Node.build(), Permission.USER_ALL_EDIT_PERMISSION));
//    }

    private void generateOtherEditPasswordNodes(){
        String seqTitle = "Edit another User's password";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, userController.getInputMap());
        seq.addStep("username", userPresenter::enterUsername, userController::isValidUser, userPresenter::wrongInput);
        seq.addStep("password", userPresenter::enterPassword, null, null);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, userController::editOtherPassword);
        entryPoints.add(seq.build(Node.build(), Permission.USER_OTHER_EDIT_PASSWORD));
    }

    private void generateDeleteSelfAccountNodes(){
        String seqTitle = "Delete your User Account :(";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, userController.getInputMap());
        seq.addStep("password", userPresenter::enterPassword, userController::isCorrectPassword, userPresenter::wrongPassword);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, userController::deleteAccount);
        entryPoints.add(seq.build(Node.build(), Permission.USER_SELF_DELETE_ACCOUNT));
    }
    private void generateDeleteOtherAccountNodes(){
        String seqTitle = "Delete someone else's User Account :(";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, userController.getInputMap());
        seq.addStep("username", userPresenter::enterUsername, userController::isValidUser, userPresenter::wrongInput);
        seq.addStep("password", userPresenter::enterPassword, userController::isCorrectPassword, userPresenter::wrongPassword);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, userController::deleteAccount);
        entryPoints.add(seq.build(Node.build(), Permission.USER_OTHER_DELETE_ACCOUNT));
    }

    private void generateCreateSpecialRequestNodes(){
        String seqTitle = "Create a new Special Request";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, specialRequestController.getInputMap());
        seq.addStep("header", specialRequestPresenter::enterHeader, null, null);
        seq.addStep("description", specialRequestPresenter::enterDescription, null, null);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, specialRequestController::addRequest);
        Node.setCompletable(specialRequestPresenter::requestCreateConfirmation);
        entryPoints.add(seq.build(Node.build(), Permission.USER_CREATE_REQUEST));
    }

    private void generateAddressSpecialRequestNodes(){
        String seqTitle = "Edit an existing Special Requests";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, specialRequestController.getInputMap());
        seq.addStep("request_id", specialRequestPresenter::enterRequestID, specialRequestController::isValidID, specialRequestPresenter::invalidRequestID);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, specialRequestController::addressRequest);
        entryPoints.add(seq.build(Node.build(), Permission.USER_OTHER_EDIT_REQUEST));
    }

    private void generateDeleteSpecialRequestNodes(){
        String seqTitle = "Remove an existing Special Requests";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, specialRequestController.getInputMap());
        seq.addStep("request_id", specialRequestPresenter::enterRequestID, specialRequestController::isValidID, specialRequestPresenter::invalidRequestID);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, specialRequestController::removeRequest);
        entryPoints.add(seq.build(Node.build(), Permission.USER_SELF_EDIT_REQUEST));
    }

    private void generateViewSelfSpecialRequestNodes(){
        String seqTitle = "View your existing Special Requests";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, specialRequestController.getInputMap());
        seq.addStep(null, specialRequestPresenter::getRequests, null, null);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, specialRequestController::viewMethod);
        entryPoints.add(seq.build(Node.build(), Permission.VIEW_SELF_REQUESTS));
    }

    private void generateViewPendingSpecialRequestNodes(){
        String seqTitle = "View all Special Requests flagged as Pending";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, specialRequestController.getInputMap());
        seq.addStep(null, specialRequestPresenter::getPendingRequests, null, null);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, specialRequestController::viewMethod);
        entryPoints.add(seq.build(Node.build(), Permission.VIEW_OTHER_PENDING_REQUESTS));
    }

    private void generateViewAddressedSpecialRequestNodes(){
        String seqTitle = "View all Special Requests flagged as Addressed";
        LinkedMenuNodeBuilder seq = new LinkedMenuNodeBuilder(seqTitle, specialRequestController.getInputMap());
        seq.addStep(null, specialRequestPresenter::getAddressedRequests, null, null);
        MenuNode.MenuNodeBuilder Node = new MenuNode.MenuNodeBuilder(seqTitle, specialRequestController::viewMethod);
        entryPoints.add(seq.build(Node.build(), Permission.VIEW_OTHER_ADDRESSED_REQUESTS));
    }

}
