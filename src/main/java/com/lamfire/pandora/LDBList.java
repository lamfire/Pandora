package com.lamfire.pandora;

import com.lamfire.utils.Bytes;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-24
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class LDBList implements List<byte[]>,Container {
    private final Lock lock = new ReentrantLock();
    private final LDBMeta meta;
    private final LDBDatabase _db;
    private final byte[] sizeKey;
    private final String name;

    public LDBList(LDBMeta meta,LDBDatabase db,String name) {
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
        throw new RuntimeException("Not supported operation.");
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
        if(bytes == null){
            return false;
        }
        int index = size();
        try{
            lock.lock();
            _db.put(Bytes.toBytes(index),bytes);
            sizeCounter(1);
            return true;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean addAll(Collection<? extends byte[]> c) {
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends byte[]> c) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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

    @Override
    public byte[] get(int index) {
        try{
            lock.lock();
            return _db.get(Bytes.toBytes(index));
        }finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] set(int index, byte[] element) {
        try{
            lock.lock();
            _db.put(Bytes.toBytes(index),element);
            return element;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void add(int index, byte[] element) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public byte[] remove(int index) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public int indexOf(Object o) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public ListIterator<byte[]> listIterator() {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public ListIterator<byte[]> listIterator(int index) {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public List<byte[]> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Not supported operation.");
    }
}
