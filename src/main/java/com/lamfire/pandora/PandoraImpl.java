package com.lamfire.pandora;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-26
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
class PandoraImpl implements Pandora {
    private static final Logger LOGGER = Logger.getLogger(PandoraImpl.class);

    private final Map<String, Container> dbs = Maps.newHashMap();
    private final Map<String,String> databaseNames = Maps.newHashMap();
    private final String storageDir;
    private final LDBDatabaseMgr databaseMgr;
    private final LDBMeta meta;

    public PandoraImpl(String storageDir){
        LOGGER.info("Make 'PandoraImpl' : " + storageDir);
        this.storageDir = storageDir;
        this.databaseMgr = new LDBDatabaseMgr(storageDir);
        this.meta = new LDBMeta(this.databaseMgr);
        loadExistsDatabaseInfo();
    }

    public PandoraImpl(String storageDir, PandoraOptions options){
        LOGGER.info("Make 'PandoraImpl' : " + storageDir);
        this.storageDir = storageDir;
        this.databaseMgr = new LDBDatabaseMgr(storageDir,options);
        this.meta = new LDBMeta(this.databaseMgr);
        loadExistsDatabaseInfo();
    }

    private void loadExistsDatabaseInfo(){
        //读取已经存在的DB
        byte[] prefix = databaseMgr.asBytes(LDBDatabaseMgr.META_KEY_PREFIX_DATABASE);
        Map<byte[],byte[]> map = meta.findByPrefix(prefix);
        for(Map.Entry<byte[] ,byte[]> e : map.entrySet()){
            String dbName = meta.getDatabaseNameByKey(e.getKey());
            String clsName = databaseMgr.asString(e.getValue());
            databaseNames.put(dbName,clsName);
        }
    }

    private void register(String name,Container col){
        String clsName = col.getClass().getSimpleName();
        byte[] dbKey = meta.getDatabaseKeyByName(name);
        meta.setValue(dbKey, databaseMgr.asBytes(clsName));
        databaseNames.put(name,clsName);
        dbs.put(name,col);
    }

    private void unregister(String name){
        byte[] dbKey = meta.getDatabaseKeyByName(name);

        Container col = dbs.remove(name);
        if(col != null){
            col.clear();
        }
        this.meta.delete(dbKey);
        this.databaseNames.remove(name);

    }

    @Override
    public void remove(String key) {
        unregister(key);
    }

    @Override
    public long count() {
        return databaseNames.size();
    }

    @Override
    public Set<String> keys() {
        return databaseNames.keySet();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean exists(String key) {
        return dbs.containsKey(key);
    }

    @Override
    public synchronized Increment getIncrement(String key) {
        Increment result = (Increment)dbs.get(key);
        if (result == null) {
            result = new LDBIncrement(this.meta,new LDBDatabase(this.databaseMgr,key),key);
            register(key, result);
        }
        return result;
    }


    @Override
    public synchronized Queue getQueue(String key) {
        Queue result = (Queue)dbs.get(key);
        if (result == null) {
            result = new LDBQueue(this.meta,new LDBDatabase(this.databaseMgr,key),key);
            register(key, result);
        }
        return result;
    }

    public BlockingQueue getBlockingQueue(String key){
        Queue queue = getQueue(key);
        return new BlockingQueue(queue);
    }

    @Override
    public synchronized Rank getRank(String key) {
        Rank result = (Rank)dbs.get(key);
        if (result == null) {
            result = new LDBRank(this.meta,new LDBDatabase(this.databaseMgr,key),new LDBDatabase(this.databaseMgr,key+"_idx"),key);
            register(key, result);
        }
        return result;
    }

    public synchronized Map<byte[] ,byte[]> getMap(String key){
        LDBMap result = (LDBMap)dbs.get(key);
        if (result == null) {
            result = new LDBMap(this.meta,new LDBDatabase(this.databaseMgr,key),key);
            register(key, result);
        }
        return result;
    }

    public synchronized Set<byte[]> getSet(String key){
        LDBSet result = (LDBSet)dbs.get(key);
        if (result == null) {
            result = new LDBSet(this.meta,new LDBDatabase(this.databaseMgr,key),key);
            register(key, result);
        }
        return result;
    }

    @Override
    public synchronized Collection<byte[]> getCollection(String key) {
        LDBCollection result = (LDBCollection)dbs.get(key);
        if (result == null) {
            result = new LDBCollection(this.meta,new LDBDatabase(this.databaseMgr,key),key);
            register(key, result);
        }
        return result;
    }

    public synchronized List<byte[]> getList(String key){
        LDBList result = (LDBList)dbs.get(key);
        if (result == null) {
            result = new LDBList(this.meta,new LDBDatabase(this.databaseMgr,key),key);
            register(key, result);
        }
        return result;
    }

}
