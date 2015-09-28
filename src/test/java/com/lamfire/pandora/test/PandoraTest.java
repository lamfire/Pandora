package com.lamfire.pandora.test;

import com.lamfire.pandora.test.tester.*;
import com.lamfire.pandora.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-29
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class PandoraTest extends Abstract{

    public static void testIncrement(Pandora store) {
        Increment increment = store.getIncrement("increment_tester");
        IncrementTester tester = new IncrementTester(increment);
        tester.test();
    }

    public static void testList(Pandora store) {
        List<byte[]> list = store.getList("list_tester");
        ListTester tester = new ListTester(list);
        tester.test();
    }

    public static void testMap(Pandora store) {
        Map<byte[],byte[]> map = store.getMap("map_tester");
        MapTester tester = new MapTester(map);
        tester.test();
    }

    public static void testQueue(Pandora store) {
        Queue queue =store.getQueue("queue_tester");
        QueueTester tester = new QueueTester(queue);
        tester.test();
    }

    public static void testRank(Pandora store) {
        Rank set = store.getRank("rank_tester");
        FireRankTester tester = new FireRankTester(set);
        tester.test();
    }

    public static void testSet(Pandora store) {
        Set<byte[]> set =store.getSet("set_tester");
        SetTester tester = new SetTester(set);
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
        PandoraMaker maker = new PandoraMaker("/data/pandora");
        maker.createIfMissing(true);
        Pandora pandora = maker.make();
        testAll(pandora);
    }
}
