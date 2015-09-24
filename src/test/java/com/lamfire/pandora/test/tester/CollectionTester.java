package com.lamfire.pandora.test.tester;


import com.lamfire.utils.Asserts;

import java.util.Collection;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-10-25
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class CollectionTester {

    Collection<byte[]> set;

    public CollectionTester(Collection<byte[]> set){
        this.set = set;
    }

    public  void testIterator(){
        set.clear();
        for(int i=0;i<10;i++){
            String val = String.format("%05d",i);
            set.add(val.getBytes());
        }

        for(int i=0;i<10;i++){
            String val = String.format("%05d",i);
            set.add(val.getBytes());
        }

        for(byte[] bytes : set){
            System.out.println(new String(bytes));
        }
    }

    public  void test() {
        int elements = 100000;
        System.out.println("==>> startup : " + this.getClass().getName());

        testIterator();

        set.clear();
        System.out.println("clear()");
        long startAt = System.currentTimeMillis();


        for(int i=0;i<elements;i++){
            String val = String.format("%05d",i);
            set.add(val.getBytes());
        }
        Asserts.equalsAssert(elements,set.size());
        System.out.println("add elements " + set.size( ) + " [OK] " + (System.currentTimeMillis() - startAt) +"ms");

        startAt = System.currentTimeMillis();
        for(int i=0;i<elements;i++){
            String val = String.format("%05d",i);
            set.add(val.getBytes());
        }
        Asserts.equalsAssert(elements * 2,set.size());
        System.out.println("re add elements " + set.size( ) + " [OK] "+ (System.currentTimeMillis() - startAt) +"ms");


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
        Asserts.equalsAssert(elements * 2 -2,size);
        System.out.println("size():"+size +" [OK]");

        System.out.println("<<== finish : " + this.getClass().getName() +" - " +(System.currentTimeMillis() - startAt) +"ms");
    }

}
