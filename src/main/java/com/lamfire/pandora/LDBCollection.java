package com.lamfire.pandora;

import com.lamfire.code.MurmurHash;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LDBCollection implements Collection<byte[]> ,Container {
    private final Lock lock = new ReentrantLock();
    private final LDBMeta meta;
    private final LDBDatabase _db;
    private final byte[] sizeKey;
    private final String name;

    public LDBCollection(LDBMeta meta,LDBDatabase db,String name) {
        this.meta = meta;
        this.name = name;
        this._db = db;
        this.sizeKey = meta.getSizeKey(name);
    }

    void sizeCounter(long incr){
        meta.increment(this.sizeKey,incr);
    }

    int hash(byte[] bytes){
        return MurmurHash.hash32(bytes,13);
    }

    public int count(byte[] bytes){
        int hash = hash(bytes);
        for(int i=0;i<Integer.MAX_VALUE;i++){
            String key = String.format("%d/%d",hash,i);
            byte[] value = _db.get(key.getBytes());
            if(value == null){
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    String key(byte[] bytes,int offset){
        int hash = hash(bytes);
        String key = String.format("%d/%d",hash,offset);
        return key;
    }

    String availableKey(byte[] bytes){
        int hash = hash(bytes);
        for(int i=0;i<Integer.MAX_VALUE;i++){
            String key = String.format("%d/%d",hash,i);
            byte[] value = _db.get(key.getBytes());
            if(value == null){
                return key;
            }
        }
        throw new RuntimeException("Not available key space,key offset Maximum.");
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
    public boolean contains(Object o) {
        if(!(o instanceof byte[])){
            throw new IllegalArgumentException("key not instanceof byte[].");
        }
        if(o == null){
            return false;
        }

        try{
            lock.lock();
            byte[] bytes = (byte[])o;
            String key = key(bytes,0);
            byte[] value = _db.get(key.getBytes());
            return value != null;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<byte[]> iterator() {
        return new LDBValueIterator(LDBIteratorMgr.getInstance().openIterator(_db));
    }

    @Override
    public Object[] toArray() {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean add(byte[] bytes) {
        String key = availableKey(bytes);
        try{
            lock.lock();
            _db.put(key.getBytes(),bytes);
            sizeCounter(1);
            return true;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        byte[] bytes = (byte[])o;
        int hash = hash(bytes);
        try{
            lock.lock();
            for(int i=0;i<Integer.MAX_VALUE;i++){
                String key = String.format("%d/%d",hash,i);
                byte[] value = _db.get(key.getBytes());
                if(value == null){
                    break;
                }
                _db.delete(key.getBytes());
                sizeCounter(-1);
            }
        }finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean addAll(Collection<? extends byte[]> c) {
        try{
            lock.lock();
            for(byte[] bytes : c){
                add(bytes);
            }
        }finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        try{
            lock.lock();
            for(Object o : c){
                remove(o);
            }
        }finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Not supported operation.");
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
}
