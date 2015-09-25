package com.lamfire.pandora.test.tester;


import com.lamfire.utils.Asserts;
import com.lamfire.utils.Bytes;
import com.lamfire.utils.RandomUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-10-25
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class ListTester {

    List<byte[]> list;

    public ListTester(List<byte[]> list){
        this.list = list;
    }

    public  void test() {
        System.out.println("==>> startup : " + this.getClass().getName());


        list.clear();
        System.out.println("clear()");
        long startAt = System.currentTimeMillis();

        list.clear();
        for(int i=0;i<10;i++){
            list.add(Bytes.toBytes(i));
        }
        Asserts.equalsAssert(10,list.size());
        System.out.println("add elements " + list.size() + " [OK]");

        int i=0;
        for(byte[] bytes : list){
            int v = Bytes.toInt(bytes);
            Asserts.equalsAssert(v,i++);
        }
        System.out.println("iterator elements " + list.size() + " [OK]");

        int index = RandomUtils.nextInt(10);
        byte[] bytes = list.get(index);
        int val = Bytes.toInt(bytes);
        Asserts.equalsAssert(index,val);
        System.out.println("get elements " + index + " [OK]");

        int rnd = RandomUtils.nextInt();
        list.set(index,Bytes.toBytes(rnd));
        byte[] vBytes = list.get(index);
        Asserts.equalsAssert(rnd,Bytes.toInt(vBytes));
        System.out.println("set elements " + index + " = " + rnd + " [OK]");


        System.out.println("<<== finish : " + this.getClass().getName() +" - " +(System.currentTimeMillis() - startAt) +"ms");
    }

}
