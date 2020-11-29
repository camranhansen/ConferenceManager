package csc.zerofoureightnine.conferencemanager.gateway.sql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SQLTest {
    @Test
    public void testSQLDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection c = DriverManager.getConnection("jdbc:hsqldb:file:db/data", "SA", "");
        assertTrue(c.isValid(100));
    }
}
