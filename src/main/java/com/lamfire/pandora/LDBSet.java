package com.lamfire.pandora;

import com.lamfire.code.MurmurHash;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-23
 * Time: 下午8:55
 * To change this template use File | Settings | File Templates.
 */
public class LDBSet implements Set<byte[]> ,FireCollection{

    private final Lock lock = new ReentrantLock();
    private static final byte[] EMPTY_VALUE = {0};
    private final LDBMeta meta;
    private final LDBDatabase _db;
    private final byte[] sizeKey;
    private final String name;

    public LDBSet(LDBMeta meta,LDBDatabase db,String name) {
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
    public boolean contains(Object o) {
        if(!(o instanceof byte[])){
            throw new IllegalArgumentException("key not instanceof byte[].");
        }
        try{
            lock.lock();
            return _db.get(((byte[])o)) != null;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Iterator<byte[]> iterator() {
        return new LDBKeyIterator(_db.iterator());
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
        try{
            lock.lock();
            byte[] existsBytes = _db.get(bytes);
            if(existsBytes != null){
                return false;
            }
            _db.put(bytes,EMPTY_VALUE);
            sizeCounter(1);
            return true;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        try{
            lock.lock();
            if(!(o instanceof byte[])){
                throw new IllegalArgumentException("key not instanceof byte[].");
            }
            byte[] value = _db.get((byte[])o);
            if(value == null){
                return false;
            }
            _db.delete((byte[]) o);
            sizeCounter(-1);
            return true;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean addAll(Collection<? extends byte[]> c) {
        for(byte[] bytes : c){
            add(bytes);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for(Object o : c){
            remove((byte[])o);
        }
        return true;
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
