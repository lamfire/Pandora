package com.lamfire.pandora.test.tester;


import com.lamfire.utils.Asserts;
import com.lamfire.utils.Bytes;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-10-25
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class MapTester {

    Map<byte[],byte[]> map;

    public MapTester(Map<byte[],byte[]> map){
        this.map = map;
    }

    void testKeySet(){
        map.clear();
        for(int i=0;i<10;i++){
            byte[] bytes = Bytes.toBytes(i);
            map.put(bytes,bytes) ;
        }

        Asserts.equalsAssert(map.size(),10);

        Set<byte[]> keys = map.keySet();
        int i=0;
        for(byte[] bytes : keys){
            int val = Bytes.toInt(bytes);
            Asserts.equalsAssert(val,i++);
        }

        System.out.println("testKeySet - [OK]");
    }

    void testValues(){
        map.clear();
        for(int i=0;i<10;i++){
            byte[] bytes = Bytes.toBytes(i);
            map.put(bytes,bytes) ;
        }

        Asserts.equalsAssert(map.size(),10);

        Collection<byte[]> values = map.values();
        int i=0;
        for(byte[] bytes : values){
            int val = Bytes.toInt(bytes);
            Asserts.equalsAssert(val,i++);
        }

        System.out.println("testValues - [OK]");
    }

    void testEntrySet(){
        map.clear();
        for(int i=0;i<10;i++){
            byte[] bytes = Bytes.toBytes(i);
            map.put(bytes,bytes) ;
        }

        Asserts.equalsAssert(map.size(),10);

        int i=0;
        for( Map.Entry<byte[], byte[]> e : map.entrySet()){
            int key = Bytes.toInt(e.getKey());
            int val = Bytes.toInt(e.getValue());
            Asserts.equalsAssert(key,i);
            Asserts.equalsAssert(val,i);
            Asserts.equalsAssert(val,key);
            i++;
        }

        System.out.println("testEntrySet - [OK]");
    }

    void testEmpty(){
        map.clear();

        Asserts.equalsAssert(map.size(),0);

        int i=0;
        for( Map.Entry<byte[], byte[]> e : map.entrySet()){
            int key = Bytes.toInt(e.getKey());
            int val = Bytes.toInt(e.getValue());
            Asserts.equalsAssert(key,i);
            Asserts.equalsAssert(val,i);
            Asserts.equalsAssert(val,key);
            i++;
        }
        System.out.println("testEmpty - [OK]");
    }

    public void test() {
        System.out.println("==>> startup : " + this.getClass().getName());

        testKeySet();

        testValues();

        testEntrySet();

        testEmpty();

        System.out.println("<<== finish : " + this.getClass().getName());
    }

}
