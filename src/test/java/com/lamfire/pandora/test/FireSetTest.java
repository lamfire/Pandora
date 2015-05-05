package com.lamfire.pandora.test;

import com.lamfire.pandora.test.benchmark.FireSetBenchmark;
import com.lamfire.pandora.test.tester.FireSetTester;
import com.lamfire.pandora.FireSet;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class FireSetTest extends Abstract{

    public static void benchmark(){
        String name = "set_benchmark";
        FireSet rank =getPandora().getFireSet(name);
        FireSetBenchmark benchmark = new FireSetBenchmark(rank);
        benchmark.startupBenchmarkWrite(1);
    }

    public static void test() {
        String name = "set_tester";
        FireSet rank =getPandora().getFireSet(name);
        FireSetTester tester = new FireSetTester(rank);
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
