package com.lamfire.pandora.test;

import com.lamfire.pandora.Queue;
import com.lamfire.pandora.test.benchmark.QueueBenchmark;
import com.lamfire.pandora.test.tester.QueueTester;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class QueueTest extends Abstract{
    public static void benchmark() {
        Queue queue = getPandora().getQueue("benchmark_queue");
        QueueBenchmark benchmark = new QueueBenchmark(queue);
        benchmark.startupBenchmarkRead(1);
    }

    public static void test() {
        Queue queue = getPandora().getQueue("queue_tester");
        QueueTester tester = new QueueTester(queue);
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
