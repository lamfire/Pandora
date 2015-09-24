package com.lamfire.pandora;

import com.lamfire.logger.Logger;
import org.iq80.leveldb.DBIterator;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-23
 * Time: 下午3:34
 * To change this template use File | Settings | File Templates.
 */
class LDBEntryIterator implements LDBIterator<Map.Entry<byte[],byte[]>> {
    private static final Logger LOGGER = Logger.getLogger(LDBEntryIterator.class);
    private final Lock lock = new ReentrantLock();
    private DBIterator iterator;
    private boolean closed = false;
    private final String id;
    private final LDBIteratorMgr mgr;

    private long lastUseTimeMillis = System.currentTimeMillis();

    LDBEntryIterator(String id,LDBIteratorMgr mgr ,DBIterator it){
        this.id = id;
        this.mgr = mgr;
        this.iterator = it;
        this.iterator.seekToFirst();
    }

    public String getId(){
        return id;
    }

    @Override
    public void close(){
        if(closed){
            return;
        }
        try {
            lock.lock();
            this.iterator.close();
            mgr.onIteratorClosed(this);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        } finally {
            closed = true;
            lock.unlock();
        }
    }

    @Override
    public boolean hasNext() {
        lastUseTimeMillis = System.currentTimeMillis();
        try {
            lock.lock();
            if(closed){
                return false;
            }
            boolean hasNext =  iterator.hasNext();
            if(!hasNext){
                close();
            }
            return hasNext;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map.Entry<byte[], byte[]> next() {
        lastUseTimeMillis = System.currentTimeMillis();
        if(closed){
            return null;
        }
        try {
            lock.lock();
            Map.Entry<byte[], byte[]> result =  iterator.next();
            return result;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not supported operation.");
    }

    public long getLastUseTimeMillis(){
        return this.lastUseTimeMillis;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(!closed)close();
    }
}
