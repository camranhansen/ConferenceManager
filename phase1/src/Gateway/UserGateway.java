package Gateway;

import Users.User;
import Users.UserManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        if (new File(getFilePath()).isFile()) return;
        this.readFromFile();
        for (int i = 0; i < this.getRowCount(); i++) {
            um.setSingleUserData(this.getRow(i));
        }
    }
}
