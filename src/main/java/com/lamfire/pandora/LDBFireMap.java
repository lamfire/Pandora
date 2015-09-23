package com.lamfire.pandora;

import com.lamfire.utils.Lists;
import org.iq80.leveldb.DBIterator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-23
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
class LDBFireMap implements FireMap {
    private final Lock lock = new ReentrantLock();
    private final LDBMeta meta;
    private final LDBDatabase _db;
    private final byte[] sizeKey;
    private final String name;

    public LDBFireMap(LDBMeta meta,LDBDatabase db,String name){
        this.meta = meta;
        this.name = name;
        this._db = db;
        this.sizeKey = meta.getSizeKey(name);
    }

    private synchronized LDBDatabase getDB(){
        return _db;
    }

    private void incrSize(long incr){
        meta.increment(this.sizeKey,incr);
    }

    byte[] asBytes(String message) {
        return LDBManager.asBytes(message);
    }

    String asString(byte[] message) {
        return LDBManager.asString(message);
    }

    @Override
    public void put(byte[] key, byte[] value) {
        try{
            lock.lock();
            byte[] oldValue = getDB().get(key);

            if(!Arrays.equals(value,oldValue)){
                getDB().put(key,value);
            }

            if(oldValue == null && value != null){
                incrSize(1);
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public List<byte[]> keys() {
        DBIterator it =  getDB().iterator();
        try{
            lock.lock();
            List<byte[]> keys = Lists.newArrayList();

            it.seekToFirst();
            while(it.hasNext()){
                byte[] keyBytes = it.next().getKey();
                keys.add((keyBytes));
            }
            return keys;
        }finally {
            lock.unlock();
            LDBManager.closeIterator(it);
        }
    }

    @Override
    public byte[] get(byte[] key) {
        try{
            lock.lock();
            return getDB().get((key));
        }finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized void remove(byte[] key) {
        try{
            lock.lock();
            byte[] oldValue = getDB().get(key);
            getDB().delete(key);
            if(oldValue != null){
                incrSize(-1);
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean exists(byte[] key) {
        try{
            lock.lock();
            return get(key)!=null;
        }finally {
            lock.unlock();
        }
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
