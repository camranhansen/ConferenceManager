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
                setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC").
                setProperty("hibernate.connection.url", "jdbc:sqlite:test.db").
                buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
