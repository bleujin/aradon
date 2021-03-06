package net.ion.nradon.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.RuntimeDelegate;

import net.ion.nradon.HttpResponse;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.CaseInsensitiveMap;

public class RadonResponseHeaders implements MultivaluedMap<String, Object> {
    private CaseInsensitiveMap cachedHeaders = new CaseInsensitiveMap<Object>();
    private HttpResponse response;
    private ResteasyProviderFactory factory;

    public RadonResponseHeaders(HttpResponse response, ResteasyProviderFactory factory) {
        this.response = response;
        this.factory = factory;
    }

    @SuppressWarnings({"unchecked"})
    public void putSingle(String key, Object value) {
        cachedHeaders.putSingle(key, value);
        RuntimeDelegate.HeaderDelegate delegate = factory.createHeaderDelegate(value.getClass());
        if (delegate != null) {
            response.header(key, delegate.toString(value));
        } else {
            response.header(key, value.toString());
        }
    }

    @SuppressWarnings({"unchecked"})
    public void add(String key, Object value) {
        cachedHeaders.add(key, value);
        addResponseHeader(key, value);
    }

    @SuppressWarnings({"unchecked"})
    protected void addResponseHeader(String key, Object value) {
        RuntimeDelegate.HeaderDelegate delegate = factory.createHeaderDelegate(value.getClass());
        if (delegate != null) {
            response.header(key, delegate.toString(value));
        } else {
            response.header(key, value.toString());
        }
    }

    public Object getFirst(String key) {
        return cachedHeaders.getFirst(key);
    }

    public int size() {
        return cachedHeaders.size();
    }

    public boolean isEmpty() {
        return cachedHeaders.isEmpty();
    }

    public boolean containsKey(Object o) {
        return cachedHeaders.containsKey(o);
    }

    public boolean containsValue(Object o) {
        return cachedHeaders.containsValue(o);
    }

    @SuppressWarnings({"unchecked"})
    public List<Object> get(Object o) {
        return cachedHeaders.get(o);
    }

    @SuppressWarnings({"unchecked"})
    public List<Object> put(String s, List<Object> objs) {
        for (Object obj : objs) {
            addResponseHeader(s, obj);
        }
        return cachedHeaders.put(s, objs);
    }

    public List<Object> remove(Object o) {
        throw new RuntimeException("Removing a header is illegal");
    }

    public void putAll(Map<? extends String, ? extends List<Object>> map) {
        for (Map.Entry<? extends String, ? extends List<Object>> entry : map.entrySet()) {
            List<Object> objs = entry.getValue();
            for (Object obj : objs) {
                add(entry.getKey(), obj);
            }
        }
    }

    public void clear() {
        throw new RuntimeException("Removing a header is illegal");
    }

    @SuppressWarnings({"unchecked"})
    public Set<String> keySet() {
        return cachedHeaders.keySet();
    }

    @SuppressWarnings({"unchecked"})
    public Collection<List<Object>> values() {
        return cachedHeaders.values();
    }

    @SuppressWarnings({"unchecked"})
    public Set<Entry<String, List<Object>>> entrySet() {
        return cachedHeaders.entrySet();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean equals(Object o) {
        return cachedHeaders.equals(o);
    }

    public int hashCode() {
        return cachedHeaders.hashCode();
    }

}
