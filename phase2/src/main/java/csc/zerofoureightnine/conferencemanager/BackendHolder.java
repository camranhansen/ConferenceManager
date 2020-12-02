package csc.zerofoureightnine.conferencemanager;

import csc.zerofoureightnine.conferencemanager.datacollection.RuntimeDataHolder;
import csc.zerofoureightnine.conferencemanager.events.EventManager;
import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;
import csc.zerofoureightnine.conferencemanager.users.*;

import java.util.HashMap;
import java.util.List;

//TODO come up with a better name.
public class BackendHolder {
    private EventManager eventManager;
    private UserManager userManager;
    private MessageManager messageManager;
    private PermissionManager permissionManager;
    private SpecialRequestManager specialRequestManager;

    public BackendHolder(){

    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    public PermissionManager getPermissionManager() {
        return this.permissionManager;
    }

    public SpecialRequestManager getSpecialRequestManager() {
        return this.specialRequestManager;
    }
    //DPE builder pattern. as long as the next TODOS are fulfilled.
    public void loadData(){
        //TODO actually load data. i.e. use gateway interface. Perhaps have two functions i.e. loadDataFromDB, loadDataFromCSV.
        //TODO also have private helper functions loadUserData or createUserManager. this should not all be in the same file.
        //TODO also standardize construction of usecases. i.e. they should either all take in a hashmap,
        // or none at all and they have a function to take them in.
        this.eventManager = new EventManager();
        HashMap<String, User> usermap = new HashMap<>();
        this.userManager = new UserManager(usermap);
        this.messageManager = new MessageManager();
        HashMap<String, List<Permission>> permissionHashMap = new HashMap<>();
        this.permissionManager = new PermissionManager(permissionHashMap);
        HashMap<String, List<SpecialRequest>> specialRequestMap = new HashMap<>();
        this.specialRequestManager = new SpecialRequestManager(specialRequestMap);
    }
}
