package Gateway;
import Users.User;
import Users.UserManager;
import Users.UserManagerTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GatewayTest {
    private Gateway gateway;

    @Before
    public void createGateway(){
        gateway = new Gateway(3,"assets/test.csv"){};
    }

    public ArrayList<String[]> createData(){
        String[] u1 = {"bob","123", "[MESSAGE_SINGLE_USER]"};
        String[] u2 = {"louis", "asdf","[MESSAGE_SINGLE_USER, USER_ALL_EDIT_PERMISSION]"};
        ArrayList<String[]> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        return userList;
    }

    @Test
    public void testUpdate() {
        ArrayList<String[]> userList = this.createData();

        Gateway g = gateway;
        for (int row = 0; row < userList.size(); row++) {
            String[] u = userList.get(row);
            g.update(0, row, u[0]);
            g.update(1, row, u[1]);
            g.update(2, row, u[2]);
        }

        for (int row = 0; row < userList.size(); row++) {
            for (int col = 0; col < userList.get(0).length; col++) {
                assertEquals(userList.get(row)[col], g.getValue(row, col));
            }
        }
    }

    @Test
    public void testFlush() throws IOException {
        Gateway g = gateway;
        ArrayList<String[]> userList = this.createData();
        for (int row = 0; row < userList.size(); row++) {
            String[] u = userList.get(row);
            g.update(0, row, u[0]);
            g.update(1, row, u[1]);
            g.update(2, row, u[2]);
        }
        g.flush();
    }

    @Test
    public void testReadFromFile() {
        Gateway g = new Gateway(3, "assets/testfile.csv") {};
        try {
            g.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] table = new String[][] {
                {"Alicia", "Harrison", "Yosi"},
                {"Gateway", "Test", "File"},
                {"I\'m", "Very", "Bored"}};

        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                assertEquals(table[row][col], g.getValue(row, col));
            }
        }
    }
}
