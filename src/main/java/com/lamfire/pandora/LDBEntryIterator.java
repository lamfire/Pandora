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

    LDBEntryIterator(DBIterator it){
        this.iterator = it;
        this.iterator.seekToFirst();
    }

    @Override
    public void close(){
        try {
            lock.lock();
            closed = true;
            iterator.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean hasNext() {
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
        try {
            lock.lock();
            Map.Entry<byte[], byte[]> result =  iterator.next();
            return result;
        } finally {
            lock.unlock();
        }
    }

    @Deprecated
    @Override
    public void remove() {

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
