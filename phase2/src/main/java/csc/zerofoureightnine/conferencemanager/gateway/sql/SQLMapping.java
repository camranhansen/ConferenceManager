package csc.zerofoureightnine.conferencemanager.gateway.sql;

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
                buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
