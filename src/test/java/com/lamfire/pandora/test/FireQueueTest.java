package com.lamfire.pandora.test;

import com.lamfire.pandora.test.benchmark.FireQueueBenchmark;
import com.lamfire.pandora.test.tester.FireQueueTester;
import com.lamfire.pandora.FireQueue;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class FireQueueTest extends Abstract{
    public static void benchmark() {
        FireQueue queue = getPandora().getFireQueue("benchmark_queue");
        FireQueueBenchmark benchmark = new FireQueueBenchmark(queue);
        benchmark.startupBenchmarkRead(1);
    }

    public static void test() {
        FireQueue queue = getPandora().getFireQueue("queue_tester");
        FireQueueTester tester = new FireQueueTester(queue);
        tester.test();
    }

    public static void main(String[] args){
        if(ArrayUtils.contains(args, "benchmark")){
            benchmark();
        }else{
            test();
        }
    }
}
