package com.lamfire.pandora.test;

import com.lamfire.pandora.test.tester.*;
import com.lamfire.pandora.*;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-29
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class PandoraTest extends Abstract{

    public static void testIncrement(Pandora store) {
        FireIncrement increment = store.getFireIncrement("increment_tester");
        FireIncrementTester tester = new FireIncrementTester(increment);
        tester.test();
    }

    public static void testList(Pandora store) {
        FireList list = store.getFireList("list_tester");
        FireListTester tester = new FireListTester(list);
        tester.test();
    }

    public static void testMap(Pandora store) {
        FireMap map = store.getFireMap("map_tester");
        FireMapTester tester = new FireMapTester(map);
        tester.test();
    }

    public static void testQueue(Pandora store) {
        FireQueue queue =store.getFireQueue("queue_tester");
        FireQueueTester tester = new FireQueueTester(queue);
        tester.test();
    }

    public static void testRank(Pandora store) {
        FireRank set = store.getFireRank("rank_tester");
        FireRankTester tester = new FireRankTester(set);
        tester.test();
    }

    public static void testSet(Pandora store) {
        FireSet set =store.getFireSet("set_tester");
        FireSetTester tester = new FireSetTester(set);
        tester.test();
    }

    public static void testStore(Pandora store){
        PandoraTester tester  = new PandoraTester(store);
        tester.test();
    }

    public static void testAll(Pandora store){
        testIncrement(store);
        testList(store);
        testMap(store);
        testQueue(store);
        testRank(store);
        testSet(store);
        testStore(store);
    }

    public static void main(String[] args){
        PandoraMaker maker = new PandoraMaker("/data/pandora" ,"TEST1");
        maker.createIfMissing(true);
        Pandora pandora = maker.make();
        testAll(pandora);
    }
}
