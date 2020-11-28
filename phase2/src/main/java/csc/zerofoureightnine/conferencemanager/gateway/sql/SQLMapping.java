package csc.zerofoureightnine.conferencemanager.gateway.sql;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

public class SQLMapping {
    private SessionFactory factory;

    private SQLMapping() {
        factory = new Configuration().
                addAnnotatedClass(MessageData.class).
                buildSessionFactory();
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
