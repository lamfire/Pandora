package com.lamfire.pandora.test;

import com.lamfire.pandora.test.benchmark.FireMapBenchmark;
import com.lamfire.pandora.test.tester.FireMapTester;
import com.lamfire.pandora.FireMap;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-23
 * Time: 下午6:19
 * To change this template use File | Settings | File Templates.
 */
public class FireMapTest extends Abstract{

    public static void benchmark() throws Exception {
        FireMap map = getPandora().getFireMap("map_benchmark");
        FireMapBenchmark benchmark = new FireMapBenchmark(map);
        benchmark.startupBenchmarkWrite(1);
    }

    public static void benchmarkRead() throws Exception {
        FireMap map = getPandora().getFireMap("map_benchmark");
        FireMapBenchmark benchmark = new FireMapBenchmark(map);
        benchmark.startupBenchmarkRead(1);
    }

    public static void test() throws Exception {
        FireMap map = getPandora().getFireMap("map_tester");
        FireMapTester tester = new FireMapTester(map);
        tester.test();
    }

    public static void main(String[] args) throws Exception {
        if(ArrayUtils.contains(args, "benchmark")){
            benchmark();
        }else{
            test();
        }
    }
}
