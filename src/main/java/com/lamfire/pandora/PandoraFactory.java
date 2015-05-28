package com.lamfire.pandora;

import com.lamfire.utils.Maps;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-28
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */
public class PandoraFactory {
    private static Map<String,Pandora> pandoraMap = Maps.newHashMap();

    public synchronized static Pandora getPandora(String dir){
        Pandora pandora = pandoraMap.get(dir);
        if(pandora != null){
            return pandora;
        }
        PandoraMaker maker = new PandoraMaker(dir);
        maker.createIfMissing(true);
        pandora = maker.make();
        pandoraMap.put(dir,pandora);
        return pandora;
    }

    public synchronized static Pandora getPandora(String dir,PandoraOptions opts){
        Pandora pandora = pandoraMap.get(dir);
        if(pandora != null){
            return pandora;
        }

        pandora = new PandoraImpl(dir,opts);
        pandoraMap.put(dir,pandora);
        return pandora;
    }
}
