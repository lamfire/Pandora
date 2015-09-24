package com.lamfire.pandora;

import com.lamfire.code.MurmurHash;
import com.lamfire.utils.Bytes;
import com.lamfire.utils.Lists;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
class LDBFireSet implements FireSet {
    private final Lock lock = new ReentrantLock();
    private int hashSeed = 13;
    private final LDBMeta meta;
    private final LDBDatabase _db;
    private final byte[] sizeKey;
    private final String name;

    public LDBFireSet(LDBMeta meta,LDBDatabase db,String name) {
        this.meta = meta;
        this.name = name;
        this._db = db;
        this.sizeKey = meta.getSizeKey(name);
        this.hashSeed = MurmurHash.hash32(asBytes(name), hashSeed);
    }

    private synchronized DB getDB(){
        return _db;
    }

    byte[] asBytes(String message) {
        return LDBDatabaseMgr.asBytes(message);
    }

    String asString(byte[] message) {
        return LDBDatabaseMgr.asString(message);
    }

    private long hash(byte[] bytes) {
        return MurmurHash.hash64(bytes, hashSeed);
    }

    private void incrSize() {
        meta.increment(this.sizeKey);
    }

    private void decrSize() {
        meta.increment(this.sizeKey, -1);
    }

    private byte[] makeHashKey(byte[] bytes) {
        long hash = hash(bytes);
        return Bytes.toBytes(hash);
    }

    @Override
    public void add(byte[] value) {
        try {
            lock.lock();
            byte[] key = makeHashKey(value);
            byte[] exists = getDB().get(key);
            if (exists == null) {
                getDB().put(key, value);
                incrSize();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] remove(int index) {
        throw new RuntimeException("Not supported method : [public byte[] remove(int index)]");
    }

    @Override
    public byte[] remove(byte[] value) {
        try {
            lock.lock();
            byte[] key = makeHashKey(value);
            byte[] oldBytes = getDB().get(key);
            if (oldBytes != null) {
                getDB().delete(key);
                decrSize();
            }
            return value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] get(int index) {
        if(index < 0){
            throw new IndexOutOfBoundsException("Index " + index +",Size " + size());
        }

        DBIterator it = getDB().iterator();
        try {
            lock.lock();

            it.seekToFirst();
            int i = 0;
            byte[] result = null;
            while(it.hasNext()){
                byte[] bytes = it.next().getValue();
                if(i == index){
                    result = bytes;
                    break;
                }
                i++;
            }
            if(i == index){
                return  result;
            }
            throw new IndexOutOfBoundsException("Index " + index +",Size " + size());
        } finally {
            lock.unlock();
            LDBDatabaseMgr.closeIterator(it);
        }
    }

    @Override
    public List<byte[]> gets(int fromIndex, int size) {
        DBIterator it = getDB().iterator();
        try {
            lock.lock();
            List<byte[]> list = Lists.newArrayList();

            it.seekToFirst();
            int i = 0;
            while(it.hasNext()){
                byte[] bytes = it.next().getValue();
                if(i >= fromIndex){
                    list.add(bytes);
                    if(list.size() == size){
                        break;
                    }
                }
                i++;
            }
            return list;
        } finally {
            lock.unlock();
            LDBDatabaseMgr.closeIterator(it);
        }
    }

    @Override
    public boolean exists(byte[] bytes) {
        try {
            lock.lock();
            byte[] key = makeHashKey(bytes);
            byte[] oldBytes = getDB().get(key);
            if (oldBytes != null) {
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return (int)meta.getValueAsLong(this.sizeKey);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.lock();
            _db.clear();
            meta.remove(sizeKey);
        } finally {
            lock.unlock();
        }
    }
}
