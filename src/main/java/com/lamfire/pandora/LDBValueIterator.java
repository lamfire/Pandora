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
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
class LDBValueIterator implements LDBIterator<byte[]> {
    private static final Logger LOGGER = Logger.getLogger(LDBValueIterator.class);

    private Lock lock = new ReentrantLock();
    private DBIterator iterator;
    private boolean closed = false;

    LDBValueIterator(DBIterator it){
        this.iterator = it;
        this.iterator.seekToFirst();
    }

    @Override
    public boolean hasNext() {
        try{
            lock.lock();
            if(closed){
                return false;
            }
            boolean hasNext =  iterator.hasNext();
            if(!hasNext){
                close();
            }
            return hasNext;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] next() {
        try{
            lock.lock();
            if(closed){
                return null;
            }
            Map.Entry<byte[],byte[]> e = iterator.next();
            if(e == null){
                return null;
            }
            return e.getValue();
        }finally {
            lock.unlock();
        }
    }

    @Deprecated
    @Override
    public void remove() {

    }


    public void close(){
        try{
            lock.lock();
            this.closed = true;
            iterator.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }finally {
            lock.unlock();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
