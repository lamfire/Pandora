package com.lamfire.pandora.test.tester;


import com.lamfire.utils.Asserts;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-10-25
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class SetTester {

    Set<byte[]> set;

    public SetTester(Set<byte[]> set){
        this.set = set;
    }

    public  void test() {
        int elements = 100000;
        System.out.println("==>> startup : " + this.getClass().getName());
        set.clear();
        System.out.println("set.clear()");
        long startAt = System.currentTimeMillis();


        for(int i=0;i<elements;i++){
            String val = String.format("%05d",i);
            set.add(val.getBytes());
        }
        Asserts.equalsAssert(elements,set.size());
        System.out.println("add elements " + set.size( ) + " [OK]");

        for(int i=0;i<elements;i++){
            String val = String.format("%05d",i);
            set.add(val.getBytes());
        }
        Asserts.equalsAssert(elements,set.size());
        System.out.println("re add elements " + set.size( ) + " [OK]");

        int i=0;
        for(byte[] e : set){
            String v = new String(e);
            Asserts.equalsAssert(v,String.format("%05d",i++));
        }
        System.out.println("iterator size =" + set.size() +" [OK]");


        boolean exists = set.contains("00044".getBytes());
        Asserts.trueAssert(exists);
        System.out.println("contains(00044):"+exists + " [OK]" );

        String value = "00044";
        set.remove(value.getBytes()) ;
        System.out.println("remove(" + value + ".getBytes()) [OK]" );

        exists = set.contains(value.getBytes());
        Asserts.falseAssert(exists);
        System.out.println("contains(00044):"+exists + " [OK]");

        int size = set.size();
        Asserts.equalsAssert(elements -1,size);
        System.out.println("size():"+size +" [OK]");

        System.out.println("<<== finish : " + this.getClass().getName() +" - " +(System.currentTimeMillis() - startAt) +"ms");
    }

}
