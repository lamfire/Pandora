package com.lamfire.pandora;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LDBMap implements Map<byte[] ,byte[]> ,FireCollection{
    private final Lock lock = new ReentrantLock();
    private final LDBMeta meta;
    private final LDBDatabase _db;
    private final byte[] sizeKey;
    private final String name;

    public LDBMap(LDBMeta meta, LDBDatabase db, String name){
        this.meta = meta;
        this.name = name;
        this._db = db;
        this.sizeKey = meta.getSizeKey(name);
    }

    void sizeCounter(long incr){
        meta.increment(this.sizeKey,incr);
    }

    LDBDatabase db(){
        return _db;
    }

    @Override
    public int size() {
        try{
            lock.lock();
            return (int)meta.getValueAsLong(this.sizeKey);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        try{
            lock.lock();
            return get(key)!=null;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public byte[] get(Object key) {
        if(!(key instanceof byte[])){
            throw new IllegalArgumentException("key not instanceof byte[].");
        }
        try{
            lock.lock();
            return _db.get(((byte[])key));
        }finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] put(byte[] key, byte[] value) {
        try{
            lock.lock();
            byte[] oldValue = _db.get(key);
            byte[] resultValue = oldValue;

            if(!Arrays.equals(value, oldValue)){
                _db.put(key,value);
            }

            if(oldValue == null && value != null){
                sizeCounter(1);
                resultValue = value;
            }
            return resultValue;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] remove(Object key) {
        try{
            lock.lock();
            byte[] oldValue = get(key);
            _db.delete((byte[]) key);
            if(oldValue != null){
                sizeCounter(-1);
            }
            return oldValue;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends byte[], ? extends byte[]> map) {
        for(Entry<? extends byte[],? extends byte[]> e : map.entrySet()){
            put(e.getKey(),e.getValue());
        }
    }

    @Override
    public void clear() {
        try{
            lock.lock();
            meta.remove(sizeKey);
            _db.clear();
        }finally {
            lock.unlock();
        }
    }

    public Set<byte[]> keySet() {

        return new Set<byte[]>() {
            @Override
            public int size() {
                return LDBMap.this.size();
            }

            @Override
            public boolean isEmpty() {
                return LDBMap.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return LDBMap.this.containsKey(o);
            }

            @Override
            public Iterator<byte[]> iterator() {
                return new LDBKeyIterator(LDBIteratorMgr.getInstance().openIterator(_db));
            }

            @Deprecated
            @Override
            public Object[] toArray() {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public <T> T[] toArray(T[] a) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean add(byte[] bytes) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean remove(Object o) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean containsAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean addAll(Collection<? extends byte[]> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean retainAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean removeAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public void clear() {
                throw new RuntimeException("Not supported operation.");
            }
        };
    }


    @Override
    public Collection<byte[]> values() {
        return new Set<byte[]>() {
            @Override
            public int size() {
                return LDBMap.this.size();
            }

            @Override
            public boolean isEmpty() {
                return LDBMap.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return LDBMap.this.containsKey(o);
            }

            @Override
            public Iterator<byte[]> iterator() {
                return new LDBValueIterator(LDBIteratorMgr.getInstance().openIterator(_db));
            }

            @Deprecated
            @Override
            public Object[] toArray() {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public <T> T[] toArray(T[] a) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean add(byte[] bytes) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean remove(Object o) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean containsAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean addAll(Collection<? extends byte[]> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean retainAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean removeAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public void clear() {
                throw new RuntimeException("Not supported operation.");
            }
        };
    }

    @Override
    public Set<Entry<byte[], byte[]>> entrySet() {
        return new Set<Entry<byte[], byte[]>>() {
            @Override
            public int size() {
                return LDBMap.this.size();
            }

            @Override
            public boolean isEmpty() {
                return LDBMap.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return LDBMap.this.containsKey(o);
            }

            @Override
            public Iterator<Entry<byte[], byte[]>> iterator() {
                return LDBIteratorMgr.getInstance().openIterator(_db);
            }

            @Deprecated
            @Override
            public Object[] toArray() {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public <T> T[] toArray(T[] a) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean add(Entry<byte[], byte[]> e) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean remove(Object o) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean containsAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean addAll(Collection<? extends Entry<byte[], byte[]>> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean retainAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public boolean removeAll(Collection<?> c) {
                throw new RuntimeException("Not supported operation.");
            }

            @Deprecated
            @Override
            public void clear() {
                throw new RuntimeException("Not supported operation.");
            }
        };
    }

}
