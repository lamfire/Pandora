package com.lamfire.pandora;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Lists;
import com.lamfire.utils.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-24
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
class LDBIteratorMgr implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(LDBIteratorMgr.class);
    private static final LDBIteratorMgr instance = new LDBIteratorMgr();

    private long expireTimeOutMillis = 180000;

    public static LDBIteratorMgr getInstance(){
        return instance;
    }

    private LDBIteratorMgr(){
        PandoraSchedules.scheduleWithFixedDelay(this,expireTimeOutMillis,expireTimeOutMillis, TimeUnit.MILLISECONDS);
    }

    private final Map<String,LDBIterator<?>> iteratorMap = Maps.newHashMap();
    private final AtomicInteger counter = new AtomicInteger(10000);

    public void onIteratorClosed(LDBIterator<?> iterator){
        if(iterator == null){
            return;
        }
        iteratorMap.remove(iterator.getId());
        LOGGER.debug("[CLOSED] closed iterator - " + iterator.getId());
    }

    public synchronized LDBEntryIterator openIterator(LDBDatabase db){
        String key = counter.incrementAndGet() +"@"+db.getName();
        LDBEntryIterator iterator = new LDBEntryIterator(key,this,db.iterator());
        iteratorMap.put(iterator.getId(),iterator);
        LOGGER.debug("[OPENED] opened iterator - " + iterator.getId());
        return iterator;
    }

    private boolean expired(LDBIterator<?> iterator){
        if(System.currentTimeMillis() - iterator.getLastUseTimeMillis() > expireTimeOutMillis){
            return true;
        }

        return false;
    }

    private void clearExpiredIterators(){
        try{
            List<String> expiredIds = Lists.newArrayList();
            for(Map.Entry<String,LDBIterator<?>> e : iteratorMap.entrySet()){
                LDBIterator<?> it = e.getValue();
                if(expired(it)){
                    expiredIds.add(e.getKey());
                }
            }

            for(String id : expiredIds){
                LDBIterator<?> it = iteratorMap.remove(id);
                LOGGER.debug("[EXPIRED] : auto closing expired iterator - " + id);
                it.close();
            }
        }catch (Throwable t){
            LOGGER.error(t.getMessage(),t);
        }
    }

    @Override
    public void run() {
        clearExpiredIterators();
    }
}
