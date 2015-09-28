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

    public Increment getIncrement(String key);

    public Queue getQueue(String key);

    public BlockingQueue getBlockingQueue(String key);

    public Rank getRank(String key);

    public Map<byte[] ,byte[]> getMap(String key);

    public Set<byte[]> getSet(String key);

    public Collection<byte[]> getCollection(String key);

    public List<byte[]> getList(String key);

}
