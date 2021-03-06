package com.lamfire.pandora.test.tester;


import com.lamfire.pandora.Queue;
import com.lamfire.utils.Asserts;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-10-25
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
public class QueueTester {
    Queue queue ;

    public QueueTester(Queue queue){
        this.queue = queue;
    }

    public void test() {
        System.out.println("==>> startup : " + this.getClass().getName());
        queue.clear();
        System.out.println("queue.clear()");

        String s = "hayash";
        queue.push(s.getBytes());
        String peek = new String(queue.peek());
        Asserts.equalsAssert(s, peek);
        String pop = new String(queue.pull());
        Asserts.equalsAssert(s, pop);

        for(int i=0;i<100;i++){
            String val = String.valueOf(i);
            queue.push(val.getBytes());
            System.out.println("queue.push("+val+")");
        }

        long size = queue.size();
        System.out.println("queue.size():"+size);
        Asserts.equalsAssert(100,size);

        for(int i=0;i<100;i++){
            String val = new String(queue.pull());
            System.out.println("queue.pop():"+val);
            Asserts.equalsAssert(val,String.valueOf(i));
        }

        size = queue.size();
        System.out.println("queue.size():"+size);
        Asserts.equalsAssert(0, size);

        System.out.println("<<== finish : " + this.getClass().getName());
    }

}
