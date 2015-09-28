package com.lamfire.pandora.test;

import com.lamfire.pandora.Increment;
import com.lamfire.pandora.test.benchmark.IncrementBenchmark;
import com.lamfire.pandora.test.tester.IncrementTester;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class IncrementTest extends Abstract{

    public static void benchmark() {
        Increment increment = getPandora().getIncrement("increment_benchmark") ;
        IncrementBenchmark benchmark = new IncrementBenchmark(increment);
        benchmark.startupBenchmarkRead(1);
    }

    public static void test() {
        Increment increment = getPandora().getIncrement("increment_tester");
        IncrementTester tester = new IncrementTester(increment);
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
