package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventSQLGateway implements SQLMap<String, EventData> {
    SQLMapping mapping;

    public EventSQLGateway(SQLMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public EventData get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EventData put(String key, EventData value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EventData remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends EventData> m) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<EventData> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Entry<String, EventData>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String save(String key, EventData entity) {
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String id = String.valueOf(session.save(entity));
        transaction.commit();
        return id;
    }

    @Override
    public EventData load(String key) {
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        EventData eventdata = session.get(EventData.class, key);
        transaction.commit();
        return eventdata;
    }

    @Override
    public List<EventData> retrieveByField(String field, String filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }
}
