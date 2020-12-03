package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.EventData;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import javax.persistence.Query;
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
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(EventData.class);
        criteria.setProjection(Projections.rowCount());
        Integer rowCount = 0;
        List data = criteria.list();
            if (data!=null) {
                rowCount = ((Number) data.get(0)).intValue();
            }
        transaction.commit();
            session.close();
        return rowCount;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        List<EventData> result = this.retrieveByField("id", (String) key);
        return !result.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        EventData ed = (EventData) value;
        return this.containsKey(ed.getDataId());
    }

    @Override
    public EventData get(Object key) {
        return this.load((String) key);
    }

    @Override
    public EventData put(String key, EventData value) {
        this.save(key, value);
        return this.load(key);
    }

    @Override
    public EventData remove(Object key) {
        EventData ed = this.load((String) key);
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(ed);
        transaction.commit();
        session.close();
        return ed;
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
        //entity.setDataId();
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String id = (String) session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public EventData load(String key) {
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        EventData eventdata = session.get(EventData.class, key);
        transaction.commit();
        session.close();
        return eventdata;
    }

    @Override
    public List<EventData> retrieveByField(String field, String filter) {
        Session session = mapping.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("select md from EventData md where md.");
        sb.append(field);
        sb.append(" like \'");
        sb.append(filter);
        sb.append("%\'");
        Query q = session.createQuery(sb.toString());
        List<EventData> res = q.getResultList();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }
}
