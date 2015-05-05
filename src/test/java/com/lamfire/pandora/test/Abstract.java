package com.lamfire.pandora.test;

import com.lamfire.pandora.Pandora;
import com.lamfire.pandora.PandoraMaker;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-5
 * Time: 下午9:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class Abstract {

    private static Pandora pandora;

    public static Pandora getPandora(){
        if(pandora != null){
            return pandora;
        }
        PandoraMaker maker = new PandoraMaker("/data/pandora" ,"TEST");
        maker.createIfMissing(true);
        pandora = maker.make();
        return pandora;
    }
}
