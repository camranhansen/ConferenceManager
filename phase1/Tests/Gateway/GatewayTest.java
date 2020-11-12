package Gateway;
import Users.User;
import Users.UserManager;
import Users.UserManagerTest;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GatewayTest {

    public Gateway createGateway(){
        return new Gateway(3,"C:\\Users\\yosi5\\OneDrive\\Desktop\\GatewayTest\\UserTest.csv"){};
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
        String users = Arrays.deepToString(userList.toArray());
        System.out.println(users);

        Gateway g = this.createGateway();
        for (int row = 0; row < userList.size(); row++) {
            String[] u = userList.get(row);
            g.update(0, row, u[0]);
            g.update(1, row, u[1]);
            g.update(2, row, u[2]);
        }
        String buffer = Arrays.deepToString(g.getBuffer().toArray());
        System.out.println(buffer);

        assertEquals(buffer, users);
    }

    @Test
    public void testFlush() throws IOException {
        Gateway g = this.createGateway();
        ArrayList<String[]> userList = this.createData();
        for (int row = 0; row < userList.size(); row++) {
            String[] u = userList.get(row);
            g.update(0, row, u[0]);
            g.update(1, row, u[1]);
            g.update(2, row, u[2]);
        }
        g.flush();
    }
}
