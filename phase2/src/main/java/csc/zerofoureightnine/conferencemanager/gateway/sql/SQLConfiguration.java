package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.SpecialRequestData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.UserData;

public class SQLConfiguration {
    private SessionFactory factory;

    public SQLConfiguration(String path) {
        factory = new Configuration().
                addAnnotatedClass(MessageData.class).
                addAnnotatedClass(UserData.class).
                addAnnotatedClass(EventData.class).
                addAnnotatedClass(SpecialRequestData.class).
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbc.JDBCDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:file:" + path).
                setProperty("hibernate.connection.username", "SA").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.hbm2ddl.auto", "update").
                buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
