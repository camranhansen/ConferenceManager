package csc.zerofoureightnine.conferencemanager.gateway.sql;

import csc.zerofoureightnine.conferencemanager.gateway.sql.entities.MessageData;
import csc.zerofoureightnine.conferencemanager.messaging.Message;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageSQLGateway implements SQLMap<String, MessageData> {
    SQLMapping mapping;

    public MessageSQLGateway(SQLMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
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
    public int save(String key, MessageData entity) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public MessageData load(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MessageData> retrieveByField(String field, String filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }
}
