package csc.zerofoureightnine.conferencemanager.gateway.sql;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.hibernate.CacheMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import csc.zerofoureightnine.conferencemanager.gateway.PersistentMap;
import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.Identifiable;

public class SQLMap<K extends Serializable, V extends Identifiable<K>> implements PersistentMap<K, V> {
    private int sizeCache = -1;
    private SQLConfiguration configuration;
    private SessionFactory sessionFactory;
    private Class<V> valClass;

    public SQLMap(SQLConfiguration sqlConfiguration, Class<V> valueClass) {
        this.configuration = sqlConfiguration;
        this.sessionFactory = configuration.getFactory();
        this.valClass = valueClass;
    }

    @Override
    public int size() {
        if (sizeCache == -1) sizeCache = countRecords();
        return sizeCache;
    }
    
    private int countRecords() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int res = ((Long) session.createQuery("select count(*) from "+ valClass.getSimpleName()).getSingleResult()).intValue();
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
        return load(((V) value).getId()) != null;
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
        Transaction transaction = session.beginTransaction();
        V val = (V) session.get(valClass, (Serializable) key);
        session.delete(val);
        transaction.commit();
        session.close();
        if (sizeCache != -1) sizeCache --;
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::save);
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
    public void save(K key, V entity) {
        entity.setId(key);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if (!session.contains(entity)) {
            session.save(entity);
            if (sizeCache != -1) sizeCache ++;
        } else {
            session.update(entity);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public V load(K key) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        V messages = session.get(valClass, key);
        transaction.commit();
        session.close();
        return messages;
    }

    @Override
    public List<V> loadForSame(String field, Object filter) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("select d from ");
        sb.append(valClass.getSimpleName());
        sb.append(" d where d.");
        sb.append(field);
        sb.append(" = :filter");
        TypedQuery<V> q = session.createQuery(sb.toString(), valClass).setParameter("filter", filter);
        List<V> res = q.getResultList();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public List<V> loadInCollection(String field, Object value) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("select d from ");
        sb.append(valClass.getSimpleName());
        sb.append(" d join d.");
        sb.append(field);
        sb.append(" c where c = :value");
        TypedQuery<V> q = session.createQuery(sb.toString(), valClass).setParameter("value", value);
        List<V> res = q.getResultList();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public List<V> search(String field, String search) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("select d from ");
        sb.append(valClass.getSimpleName());
        sb.append(" d where d.");
        sb.append(field);
        sb.append(" like :search");
        TypedQuery<V> q = session.createQuery(sb.toString(), valClass).setParameter("search", search);
        List<V> res = q.getResultList();
        tx.commit();
        session.close();
        return res;
    }

    @Override
    public void clear() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        ScrollableResults results = session.createQuery("select t from " + valClass.getSimpleName() + " t").setCacheMode(CacheMode.IGNORE).scroll();
        while (results.next()) {
            session.delete(results.get(0));
        }
        results.close();
        tx.commit();
        session.close();
        sizeCache = 0;
    }

    
}
