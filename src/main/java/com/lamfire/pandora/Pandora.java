package com.lamfire.pandora;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Pandora {
    public void remove(String key);

    public long count();

    public Set<String> keys();

    public boolean exists(String key);

    public FireIncrement getFireIncrement(String key);

    public FireList getFireList(String key);

    public FireMap getFireMap(String key);

    public FireQueue getFireQueue(String key);

    public FireSet getFireSet(String key);

    public FireRank getFireRank(String key);

    public Map<byte[] ,byte[]> getMap(String key);

    public Set<byte[]> getSet(String key);

    public Collection<byte[]> getCollection(String key);

    public List<byte[]> getList(String key);

}
