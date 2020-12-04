package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MessageSQLGateway implements PersistentMap<String, MessageData> {
    SQLConfiguration mapping;

    public MessageSQLGateway(SQLConfiguration mapping) {
        this.mapping = mapping;
    }

    @Override
    public int size() {
        
        return 0;
    }

    @Override
    public boolean isEmpty() {

        return false;
    }

    @Override
    public boolean containsKey(Object key) {

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public MessageData get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MessageData put(String key, MessageData value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MessageData remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends MessageData> m) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<MessageData> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Entry<String, MessageData>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String save(String key, MessageData entity) {
        entity.setId(key);
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String id = (String) session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public MessageData load(String key) {
        Session session = mapping.getFactory().openSession();
        Transaction transaction = session.beginTransaction();
        MessageData messages = (MessageData) session.get(MessageData.class, key);
        transaction.commit();
        session.close();
        return messages;
    }

    @Override
    public List<MessageData> retrieveByField(String field, String filter) {
        Session session = mapping.getFactory().openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("select md from MessageData md where md.");
        sb.append(field);
        sb.append(" like \'");
        sb.append(filter);
        sb.append("%\'");
        Query q = session.createQuery(sb.toString());
        List<MessageData> res = q.getResultList();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }
}
