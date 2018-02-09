package com.lamfire.pandora;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Pandora {

    void remove(String key);

    long count();

    Set<String> keys();

    boolean exists(String key);

    Increment getIncrement(String key);

    Queue getQueue(String key);

    BlockingQueue getBlockingQueue(String key);

    Rank getRank(String key);

    Map<byte[] ,byte[]> getMap(String key);

    Set<byte[]> getSet(String key);

    Collection<byte[]> getCollection(String key);

    List<byte[]> getList(String key);

}
