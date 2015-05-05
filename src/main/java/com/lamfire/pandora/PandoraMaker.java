package com.lamfire.pandora;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-5-5
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class PandoraMaker extends PandoraOptions{
    private String name;
    private String storeDir;

    public PandoraMaker(String storeDir , String name){
        this.name = name;
        this.storeDir = storeDir;
    }

    public String name(){
        return name;
    }

    public String storeDir(){
        return storeDir;
    }

    public Pandora make(){
        return new PandoraImpl(storeDir,name,this);
    }
}
