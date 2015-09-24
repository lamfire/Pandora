package com.lamfire.pandora;

import java.util.Iterator;
import java.util.Map;


class LDBKeyIterator implements Iterator<byte[]> ,LDBIterator<byte[]>{
    private LDBEntryIterator iterator;

    LDBKeyIterator(LDBEntryIterator it){
        this.iterator = it;
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
        return e.getKey();
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
