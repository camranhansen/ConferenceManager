package csc.zerofoureightnine.conferencemanager.gateway;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.Identifiable;

public interface PersistentMap<K extends Serializable, V extends Identifiable<K>> extends Map<K, V> {
    /**
     * Saves the entity to the database with the associated key as the identifier.
     * @param key The identifier for the record.
     * @param entity The field data for the record.
     */
    void save(K key, V entity);

    /**
     * Loads a record given its associated key.
     * @param key The key the record to be retrieved is associated with.
     * @return The record object.
     */
    V load(K key);
    
    /**
     * Loads the data that have the exact value.
     * for the given {@code field}.
     * @param field The field to check the {@code value} for.
     * @param value The value to check for in the {@code field}.
     * @return The {@see List} of all the data objects that have the {@code value} for the {@code field}.
     */
    List<V> loadForSame(String field, Object value);

    /**
     * Loads the data that contains the exact {@code value} in {@code field}'s collection.
     * @param field The field to check the collection for {@code value}.
     * @param value The value to be looked for in {@code field}'s collection.
     * @return The {@see List} of all the data objects that have the {@code value} contained in the collection of {@code field}.
     */
    List<V> loadInCollection(String field, Object value);

    /**
     * Loads the data that has {@code search} within the {@code field} value.
     * Use '%' to denote a wildcard.
     * For example, if field "sender" has value "Jayden", to search for it:
     * {@code persistentMap.search("sender", "Jay%")} which will match for anything that has a 
     * value string value starting with "Jay".
     * @param field The field to perform the search on.
     * @param search The {@see String} to search for.
     * @return A {@see List} that satisfies the search {@See String} in the given {@code field}.
     */
    List<V> search(String field, String search);
}
