package csc.zerofoureightnine.conferencemanager.gateway.sql;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
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
    Session session;
    Transaction transaction;
    private int beginnings;
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
        beginInteraction();
        int res = ((Long) session.createQuery("select count(*) from "+ valClass.getSimpleName()).getSingleResult()).intValue();
        endInteraction();
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
        beginInteraction();
        V val = session.get(valClass, (Serializable) key);
        session.delete(val);
        endInteraction();
        if (sizeCache != -1) sizeCache --;
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::save);
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Collection<V> vals = values();
        for (V v : vals) {
            set.add(v.getId());
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<>();
        beginInteraction();
        ScrollableResults results = session.createQuery("select t from " + valClass.getSimpleName() + " t").setCacheMode(CacheMode.IGNORE).scroll();
        while (results.next()) {
            set.add((V) results.get(0));
        }
        results.close();
        endInteraction();
        return set;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        Collection<V> vals = values();
        for (V v : vals) {
            Entry<K, V> entry = new Map.Entry<K,V>() {

                @Override
                public K getKey() {

                    return v.getId();
                }

                @Override
                public V getValue() {
                    return v;
                }

                @Override
                public V setValue(V value) {
                    save(v.getId(), value);
                    return v;
                }
                
            };
            set.add(entry);
        }
        return set;
    }

    @Override
    public void save(K key, V entity) {
        beginInteraction();
        entity.setId(key);
        if (!session.contains(entity)) {
            session.save(entity);
            if (sizeCache != -1) sizeCache ++;
        } else {
            session.update(entity);
        }
        endInteraction();
    }

    @Override
    public V load(K key) {
        beginInteraction();
        V messages = session.get(valClass, key);
        endInteraction();
        return messages;
    }

    @Override
    public List<V> loadForSame(String field, Object filter) {
        beginInteraction();
        StringBuilder sb = new StringBuilder();
        sb.append("select d from ");
        sb.append(valClass.getSimpleName());
        sb.append(" d where d.");
        sb.append(field);
        sb.append(" = :filter");
        TypedQuery<V> q = session.createQuery(sb.toString(), valClass).setParameter("filter", filter);
        List<V> res = q.getResultList();
        endInteraction();
        return res;
    }

    @Override
    public List<V> loadInCollection(String field, Object value) {
        beginInteraction();
        StringBuilder sb = new StringBuilder();
        sb.append("select d from ");
        sb.append(valClass.getSimpleName());
        sb.append(" d join d.");
        sb.append(field);
        sb.append(" c where c = :value");
        TypedQuery<V> q = session.createQuery(sb.toString(), valClass).setParameter("value", value);
        List<V> res = q.getResultList();
        endInteraction();
        return res;
    }

    @Override
    public List<V> search(String field, String search) {
        beginInteraction();
        StringBuilder sb = new StringBuilder();
        sb.append("select d from ");
        sb.append(valClass.getSimpleName());
        sb.append(" d where d.");
        sb.append(field);
        sb.append(" like :search");
        TypedQuery<V> q = session.createQuery(sb.toString(), valClass).setParameter("search", search.replace('*', '%'));
        List<V> res = q.getResultList();
        endInteraction();
        return res;
    }

    @Override
    public void clear() {
        beginInteraction();
        ScrollableResults results = session.createQuery("select t from " + valClass.getSimpleName() + " t").setCacheMode(CacheMode.IGNORE).scroll();
        while (results.next()) {
            session.delete(results.get(0));
        }
        results.close();
        endInteraction();
        sizeCache = 0;
    }

    @Override
    public void beginInteraction() {
        if (beginnings == 0) {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
        }
        
        beginnings ++;
    }

    @Override
    public void endInteraction() {
        if (beginnings <= 0) throw new IllegalStateException("Improper number of interaction beginnings and ends detected.");
        transaction.commit();
        if (beginnings > 1) {
            transaction = session.beginTransaction();
        } else {
            session.close();
        }

        beginnings --;
    }

    
}
