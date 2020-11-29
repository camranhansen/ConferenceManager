package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;

public class SQLMapping {
    private SessionFactory factory;

    public SQLMapping() {
        factory = new Configuration().
                addAnnotatedClass(MessageData.class).
                addAnnotatedClass(UserData.class).
                addAnnotatedClass(EventData.class).
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbc.JDBCDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:file:db/data").
                setProperty("hibernate.connection.username", "SA").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.hbm2ddl.auto", "create").
                buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
