package Gateway;

import Messaging.Message;
import Messaging.MessageManager;
import Users.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageGateway {
    private class UserMessageGateway extends Gateway {
        public UserMessageGateway(String user) {
            super(4, "data/user/inboxes/" + user + ".csv");
        }
    }
    MessageManager manager;

    public MessageGateway(MessageManager manager) {
        this.manager = manager;
    }


    public void saveForUser(String user) throws IOException {
        UserMessageGateway userGateway = new UserMessageGateway(user);
        List<String[]> data = manager.getInboxAsArray(user);
        for (int row = 0; row < data.size(); row++) {
            userGateway.updateRow(row, data.get(row));
        }
        userGateway.flush();
    }

    public void readForUser(String user) throws IOException {
        UserMessageGateway userGateway = new UserMessageGateway(user);
        userGateway.readFromFile();
        ArrayList<String[]> data = new ArrayList<>();
        for (int i = 0; i < userGateway.getRowCount(); i++) {
            manager.putMessageFromArray(user, userGateway.getRow(i));
        }
    }
}
