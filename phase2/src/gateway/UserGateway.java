package gateway;

import users.UserManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserGateway extends Gateway{


    public UserGateway() {
        super(3,"data/user_data.csv");
    }

    public void saveAllUsers(UserManager um) throws IOException {
        ArrayList<String[]> userData = um.getAllUserData();
        for (int row = 0; row < userData.size(); row++) {
            this.updateRow(row, userData.get(row));
        }
        this.flush();
    }

    public void readUsersFromGateway(UserManager um) throws IOException {
        if (!Files.exists(Paths.get(getFilePath()))) return;
        this.readFromFile();
        for (int i = 0; i < this.getRowCount(); i++) {
            um.setSingleUserData(this.getRow(i));
        }
    }
}
