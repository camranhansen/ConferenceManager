package csc.zerofoureightnine.conferencemanager.gateway.sql.entities;

public interface Identifiable<K> {

    public void setId(K id);

    public K getId();
}
