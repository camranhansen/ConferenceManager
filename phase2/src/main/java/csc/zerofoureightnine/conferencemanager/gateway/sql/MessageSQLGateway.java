package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.messaging.Message;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageSQLGateway implements SQLMap<String, Message> {
    SQLMapping mapping;

    public MessageSQLGateway(SQLMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int save(String key, Message entity) {
        return 0;
    }

    @Override
    public Message load(String key) {
        return null;
    }

    @Override
    public List<Message> retrieveByField(String field, String filter) {
        return null;
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
        return false;
    }

    @Override
    public Message get(Object key) {
        return null;
    }

    @Override
    public Message put(String key, Message value) {
        return null;
    }

    @Override
    public Message remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Message> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Message> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Message>> entrySet() {
        return null;
    }
}
