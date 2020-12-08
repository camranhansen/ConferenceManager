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
     * 
     * <b>Note that modifications to the returned list itself (ex. adding or removing) does not necessarily reflect modifications to the actual data stored.</b>
     * Acts similar to performing a shallow clone of an array in the sense that:
     * The modifications to the objects stored are reflected, however modifications to the returned list itself is not reflected.
     * 
     * @param field The field to check the {@code value} for.
     * @param value The value to check for in the {@code field}.
     * @return The {@link List} of all the data objects that have the {@code value} for the {@code field}.
     */
    List<V> loadForSame(String field, Object value);

    /**
     * Loads the data that contains the exact {@code value} in {@code field}'s collection.
     * 
     * <b>Note that modifications to the returned list itself (ex. adding or removing) does not necessarily reflect modifications to the actual data stored.</b>
     * Acts similar to performing a shallow clone of an array in the sense that:
     * The modifications to the objects stored are reflected, however modifications to the returned list itself is not reflected.
     * 
     * @param field The field to check the collection for {@code value}.
     * @param value The value to be looked for in {@code field}'s collection.
     * @return The {@link PersistentMap#loadInCollection(String, Object)} List of all the data objects that have the {@code value} contained in the collection of {@code field}.
     */
    List<V> loadInCollection(String field, Object value);

    /**
     * Loads the data that has {@code search} within the {@code field} value.
     * Use '%' to denote a wildcard.
     * For example, if field "sender" has value "Jayden", to search for it:
     * {@code persistentMap.search("sender", "Jay*")} which will match for anything that has a 
     * value string value starting with "Jay".
     * 
     * <b>Note that modifications to the returned list itself (ex. adding or removing) does not necessarily reflect modifications to the actual data stored.</b>
     * Acts similar to performing a shallow clone of an array in the sense that:
     * The modifications to the objects stored are reflected, however modifications to the returned list itself is not reflected.
     * 
     * @param field The field to perform the search on.
     * @param search The {@link String} to search for.
     * @return A {@link List} that satisfies the search {@link String} in the given {@code field}.
     */
    List<V> search(String field, String search);

    /**
     * Initiates everything needed to read and write data. Should be called if a group of read or 
     * write operations are to be batched together. This includes opening any potential streams 
     * required for IO. For every interaction started, the interaction should also be ended by calling
     * {@link PersistentMap#endInteraction()}.
     * 
     * Interactions can be nested in that one interaction can be started within another.
     */
    void beginInteraction();

    /**
     * Performs any operations required to end an interaction.
     * This includes closing any potential streams required for IO.
     * Needs to be called explicitly if {@link PersistentMap#beginInteraction()} was
     * explicitly called.
     * 
     * Interactions can be nested in that one interaction can be ended within another.
     */
    void endInteraction();
}
