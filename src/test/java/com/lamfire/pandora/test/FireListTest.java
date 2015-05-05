package com.lamfire.pandora.test;

import com.lamfire.pandora.test.benchmark.FireListBenchmark;
import com.lamfire.pandora.test.tester.FireListTester;
import com.lamfire.pandora.FireList;
import com.lamfire.utils.ArrayUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class FireListTest extends Abstract {
    public static void benchmark() {
        FireList list = getPandora().getFireList("list_benchmark");
        FireListBenchmark benchmark = new FireListBenchmark(list);
        benchmark.startupBenchmarkWrite(1);
    }

    public static void test() {
        FireList list = getPandora().getFireList("list_tester");
        FireListTester tester = new FireListTester(list);
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
