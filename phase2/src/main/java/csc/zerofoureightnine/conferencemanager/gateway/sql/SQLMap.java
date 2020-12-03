package csc.zerofoureightnine.conferencemanager.gateway.sql;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;
import javax.persistence.Query;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.Identifiable;

public class SQLMap<K extends Serializable, V extends Identifiable<K>> implements PersistentMap<K, V> {
    private Table tableAnnotation;
    private int sizeCache = -1;
    private SQLConfiguration configuration;
    private SessionFactory sessionFactory;
    private Class<V> vClass;

    public SQLMap(SQLConfiguration sqlConfiguration, Class<V> valueClass) {
        this.configuration = sqlConfiguration;
        this.sessionFactory = configuration.getFactory();
        this.vClass = valueClass;

        tableAnnotation = vClass.getAnnotation(Table.class);
        tableAnnotation.name();
    }

    @Override
    public int size() {
        if (sizeCache == -1) sizeCache = countRecords();
        return sizeCache;
    }

    private int countRecords() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int res = (int) session.createQuery("select count(*) from "+ getTableName()).getSingleResult();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return load((K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Session session = sessionFactory.openSession();
        boolean res = session.contains(value);
        session.close();
        return res;
    }

    @Override
    public V get(Object key) {
        return load((K) key);
    }

    @Override
    public V put(K key, V value) {
        save(key, value);
        return value;
    }

    @Override
    public V remove(Object key) {
        Session session = sessionFactory.openSession();
        V val = get(key);
        session.delete(val);
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        forEach((k, v) -> {
            save(k, v);
        });
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String save(K key, V entity) {
        entity.setId(key);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String id = (String) session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public V load(K key) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        V messages = (V) session.get(vClass, key);
        transaction.commit();
        session.close();
        return messages;
    }

    @Override
    public List<V> retrieveByField(String field, String filter) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("select md from MessageData md where md.");
        sb.append(field);
        sb.append(" like \'");
        sb.append(filter);
        sb.append("%\'");
        Query q = session.createQuery(sb.toString());
        List<V> res = q.getResultList();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public void clear() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("delete from " + getTableName()).executeUpdate();
        tx.commit();
        session.close();
    }

    private String getTableName() {
        return (tableAnnotation.name().isEmpty()) ? vClass.getSimpleName() : tableAnnotation.name();
    }
    
}
