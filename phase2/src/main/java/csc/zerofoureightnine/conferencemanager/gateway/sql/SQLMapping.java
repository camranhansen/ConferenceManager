package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

public class SQLMapping {
    private SessionFactory factory;

    public SQLMapping() {
        factory = new Configuration().
                addAnnotatedClass(MessageData.class).
                addAnnotatedClass(EventData.class).
                buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
