package gateway;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GatewayTest {
    private static String testFilePath = "testfiles/test.csv";
    private Gateway gateway;

    String[][] table = new String[][] {
            {"Alicia", "Harrison", "Yosi"},
            {"Gateway", "Test", "File"},
            {"I\'m", "Very", "Bored"}
    };

    @Before
    public void createGateway(){
        gateway = new Gateway(3, testFilePath){};
    }

    @AfterClass
    public static void cleanGeneratedFiles() {
        File testFile = new File(testFilePath);
        testFile.delete();
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
        Gateway g = new Gateway(3, "testfiles/testfile.csv") {};
        try {
            g.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                assertEquals(table[row][col], g.getValue(row, col));
            }
        }
    }

    @Test
    public void testUpdateRow() {
        for (int row = 0; row < table.length; row++) {
            gateway.updateRow(row, table[row]);
        }

        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                assertEquals(table[row][col], gateway.getValue(row, col));
            }
        }
    }

    @Test
    public void testUpdateRowExisting() {
        gateway.updateRow(0, table[0]);
        gateway.updateRow(1, table[2]);
        gateway.updateRow(0, table[1]);

        assertArrayEquals(table[1], gateway.getRow(0));
    }

    @Test
    public void testGetRow() {
        for (int row = 0; row < table.length; row++) {
            gateway.updateRow(row, table[row]);
        }

        for (int row = 0; row < table.length; row++) {
            assertArrayEquals(table[row], gateway.getRow(row));
        }
    }
}
