package csc.zerofoureightnine.conferencemanager.gateway.sql;

import java.util.List;
import java.util.Map;

public interface SQLMap<K, V> extends Map<K, V> {
    /**
     * Saves the entity to the database with the associated key as the identifier.
     * @param key The identifier for the record.
     * @param entity The field data for the record.
     * @return The numerical ID.
     */
    int save(K key, V entity);

    /**
     * Loads a record given its associated key.
     * @param key The key the record to be retrieved is associated with.
     * @return The record object.
     */
    V load(K key);

    /**
     * Retrieves a list of records who all have the filter string in the given field.
     * @param field A field to query.
     * @param filter The value in the field to query for.
     * @return A list of all the record objects that satisfy having the {@code filter} in the {@code field}.
     */
    List<V> retrieveByField(String field, String filter);

    /**
     * Empties the table completely and fully.
     */
    void clear();
}
