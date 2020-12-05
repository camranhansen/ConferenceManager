package csc.zerofoureightnine.conferencemanager.gateway;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.Identifiable;

public class DummyPersistentMap<K extends Serializable, V extends Identifiable<K>> implements PersistentMap<K, V> {
    private int interactions = 0;
    private HashMap<K, V> hashmap = new HashMap<>();

    @Override
    public int size() {
        return hashmap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashmap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return hashmap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return hashmap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return hashmap.get(key);
    }

    @Override
    public V put(K key, V value) {
        value.setId(key);
        return hashmap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return hashmap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        hashmap.putAll(m);
    }

    @Override
    public void clear() {
        hashmap.clear();
    }

    @Override
    public Set<K> keySet() {
        return hashmap.keySet();
    }

    @Override
    public Collection<V> values() {
        return hashmap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return hashmap.entrySet();
    }

    @Override
    public void save(K key, V entity) {
        hashmap.put(key, entity);
    }

    @Override
    public V load(K key) {
        return hashmap.get(key);
    }

    @Override
    public List<V> loadInCollection(String field, Object value) {
        ArrayList<V> res = new ArrayList<>();

        Iterator<V> vals = hashmap.values().iterator();
        while (vals.hasNext()) {
            V v = vals.next();
            try {
                Field selected = v.getClass().getDeclaredField(field);
                Collection fieldVal = (Collection) selected.get(v);
                if (fieldVal.contains(value)) {
                    res.add(v);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<V> loadForSame(String field, Object value) {
        ArrayList<V> res = new ArrayList<>();

        Iterator<V> vals = hashmap.values().iterator();
        while (vals.hasNext()) {
            V v = vals.next();
            try {
                Field selected = v.getClass().getDeclaredField(field);
                Object fieldVal = selected.get(v);
                if (fieldVal.equals(value)) {
                    res.add(v);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<V> search(String field, String search) {
        ArrayList<V> res = new ArrayList<>();

        Iterator<V> vals = hashmap.values().iterator();
        while (vals.hasNext()) {
            V v = vals.next();
            try {
                Field selected = v.getClass().getDeclaredField(field);
                String fieldVal = selected.get(v).toString();
                if (fieldVal.contains(search)) {
                    res.add(v);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public void beginInteraction() {
        interactions++;
    }

    @Override
    public void endInteraction() {
        interactions--;
        if (interactions < 0) throw new IllegalStateException("Improper number of interaction beginnings and ends detected.");;
    }
}
