package Gateway;

import Users.User;
import Users.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserGateway extends Gateway{


    public UserGateway() {
        super(3,"assets/user_data.csv");
    }

    public void saveAllUsers(UserManager um) throws IOException {
        ArrayList<String[]> userData = um.getAllUserData();
        for (int row = 0; row < userData.size(); row++) {
            this.updateRow(row, userData.get(row));
        }
        this.flush();
    }

    public void readFromGateway(){

    }
}
