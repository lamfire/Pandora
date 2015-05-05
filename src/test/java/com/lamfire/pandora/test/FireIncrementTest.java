package com.lamfire.pandora.test;

import com.lamfire.pandora.test.benchmark.FireIncrementBenchmark;
import com.lamfire.pandora.test.tester.FireIncrementTester;
import com.lamfire.pandora.FireIncrement;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class FireIncrementTest extends Abstract{

    public static void benchmark() {
        FireIncrement increment = getPandora().getFireIncrement("increment_benchmark") ;
        FireIncrementBenchmark benchmark = new FireIncrementBenchmark(increment);
        benchmark.startupBenchmarkRead(1);
    }

    public static void test() {
        FireIncrement increment = getPandora().getFireIncrement("increment_tester");
        FireIncrementTester tester = new FireIncrementTester(increment);
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
