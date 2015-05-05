package com.lamfire.pandora.test;

import com.lamfire.pandora.test.benchmark.FireRankBenchmark;
import com.lamfire.pandora.test.tester.FireRankTester;
import com.lamfire.pandora.FireRank;
import com.lamfire.pandora.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-9-24
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class FireRankTest extends Abstract{

    public static void benchmark(){
        String name = "rank_benchmark";
        FireRank rank = getPandora().getFireRank(name);
        FireRankBenchmark benchmark = new FireRankBenchmark(rank);
        benchmark.startupBenchmarkWrite(1);
    }

    public static void test() {
        String name = "rank_tester";
        FireRank rank = getPandora().getFireRank(name);
        FireRankTester tester = new FireRankTester(rank);
        tester.test();
    }

    public static void main(String[] args){
        String name = "rank_tester";
        FireRank rank = getPandora().getFireRank(name);

        for(int i=0;i<10;i++){
            rank.set(String.valueOf(i),i);
        }

        rank.set("8",-100);

        List<Item> max = rank.max((int)rank.size());
        for(Item item : max){
            System.out.println(item );
        }

    }
}
