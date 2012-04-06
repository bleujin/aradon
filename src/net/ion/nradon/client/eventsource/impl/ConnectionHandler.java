package net.ion.nradon.client.eventsource.impl;

public interface ConnectionHandler {
    void setReconnectionTimeMillis(long reconnectionTimeMillis);
    void setLastEventId(String lastEventId);
}
