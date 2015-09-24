package com.lamfire.pandora;


import java.util.Iterator;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-23
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
class LDBValueIterator implements Iterator<byte[]>,LDBIterator<byte[]> {
    private LDBEntryIterator iterator;


    LDBValueIterator(LDBEntryIterator it){
        this.iterator = it;
        this.iterator.seekToFirst();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public byte[] next() {
        Map.Entry<byte[], byte[]> e = iterator.next();
        if(e == null){
            return null;
        }
        return e.getValue();
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not supported operation.");
    }

    @Override
    public String getId() {
        return iterator.getId();
    }

    @Override
    public void close() {
        iterator.close();
    }

    @Override
    public long getLastUseTimeMillis() {
        return iterator.getLastUseTimeMillis();
    }
}
