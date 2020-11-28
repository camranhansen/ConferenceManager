package csc.zerofoureightnine.conferencemanager.gateway;

import csc.zerofoureightnine.conferencemanager.messaging.MessageManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageGateway {
    private static final String directory = "data/user/inboxes/";
    private class UserMessageCSVGateway extends CSVGateway {
        public UserMessageCSVGateway(String user) {
            super(4,  directory + user + ".csv");
        }
    }
    MessageManager manager;

    public MessageGateway(MessageManager manager) {
        this.manager = manager;
    }


    public void saveForUser(String user) throws IOException {
        UserMessageCSVGateway userGateway = new UserMessageCSVGateway(user);
        List<String[]> data = manager.getInboxAsArray(user);
        for (int row = 0; row < data.size(); row++) {
            userGateway.updateRow(row, data.get(row));
        }
        userGateway.flush();
    }

    public void readForUser(String user) throws IOException {
        UserMessageCSVGateway userGateway = new UserMessageCSVGateway(user);
        userGateway.readFromFile();
        ArrayList<String[]> data = new ArrayList<>();
        for (int i = 0; i < userGateway.getRowCount(); i++) {
            manager.putMessageFromArray(user, userGateway.getRow(i));
        }
    }

    public void saveAllUsers() throws IOException {
        Iterator<String> iterator = manager.getAllUsersWithInboxes();
        while (iterator.hasNext()) {
            saveForUser(iterator.next());
        }
    }

    public void readAllUsers() throws IOException {
        if (!Files.isDirectory(Paths.get(directory))) return;
        File dataFolder = new File(directory);
        File[] files = dataFolder.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                readForUser(name.substring(0, name.lastIndexOf(".csv")));
            }
        }
    }
}
