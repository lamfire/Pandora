package com.lamfire.pandora.test;

import com.lamfire.pandora.Rank;
import com.lamfire.pandora.test.benchmark.RankBenchmark;
import com.lamfire.pandora.test.tester.FireRankTester;
import com.lamfire.pandora.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class RankTest extends Abstract{

    public static void benchmark(){
        String name = "rank_benchmark";
        Rank rank = getPandora().getRank(name);
        RankBenchmark benchmark = new RankBenchmark(rank);
        benchmark.startupBenchmarkWrite(1);
    }

    public static void test() {
        String name = "rank_tester";
        Rank rank = getPandora().getRank(name);
        FireRankTester tester = new FireRankTester(rank);
        tester.test();
    }

    public static void main(String[] args){
        test();

    }
}
