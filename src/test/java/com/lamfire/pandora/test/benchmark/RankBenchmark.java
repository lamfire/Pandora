package com.lamfire.pandora.test.benchmark;

import com.lamfire.logger.Logger;
import com.lamfire.pandora.Rank;
import com.lamfire.pandora.Item;
import com.lamfire.utils.Lists;
import com.lamfire.utils.RandomUtils;
import com.lamfire.utils.Threads;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RankBenchmark {
    static final Logger logger = Logger.getLogger(RankBenchmark.class);
    static AtomicInteger atomic = new AtomicInteger();
    static AtomicInteger errorAtomic =   new AtomicInteger();
    static List<String> errorList = Lists.newArrayList();
    static TreeSet<Long> times = new TreeSet<Long>();
    static long timeMillisCount = 0;
    static long timeMillisAvg = 0;
    private Rank rank ;


    public RankBenchmark(Rank test){
        this.rank  = test;

        Threads.scheduleWithFixedDelay(new Runnable() {
            int pre = 0;
            @Override
            public void run() {
                synchronized (atomic){
                    int val = atomic.get();
                    List<Item> maxItems = rank.max(1);
                    List<Item> minItems = rank.min(1);
                    Item maxItem=null,minItem=null;
                    if(!maxItems.isEmpty()){
                        maxItem = maxItems.get(0);
                    }
                    if(!minItems.isEmpty()){
                        minItem = minItems.get(0);
                    }
                    System.out.println("[COUNTER/S] : " +  (val - pre) +"/s " + rank.size() +"/" +val +",max=" + maxItem +",min=" +minItem);
                    pre = val;
                }
            }
        },1,1, TimeUnit.SECONDS);
    }

    private void put(String v){
        try{
            rank.put(v);
        }   catch(Exception e){
             e.printStackTrace();
            errorAtomic.getAndIncrement();
            errorList.add(v);
        }
    }

    private long get(int val){
        String key = String.valueOf(val);
        long startAt = System.currentTimeMillis();
        try{
            return rank.score(key);
        }   catch (Exception e){
            logger.error("error get (" + val +")",e);
            errorAtomic.getAndIncrement();
            errorList.add(key);
        }finally{
            long usedMillis = System.currentTimeMillis() - startAt;
            timeMillisCount +=  usedMillis;
            timeMillisAvg = timeMillisCount / (1+val);
        }
        return -1;
    }
	
	private static class Writer implements Runnable  {
        RankBenchmark test;
        public Writer(RankBenchmark test){
             this.test = test;
        }
		@Override
		public void run() {
			long startAt = System.currentTimeMillis();
			while(true){
                synchronized (atomic){
                atomic.getAndIncrement();
                int val = RandomUtils.nextInt(100000);
                test.put(String.valueOf(val));
				}
			}
		}
	};

    private static class Reader implements Runnable{
        RankBenchmark test;
        public Reader(RankBenchmark test){
            this.test = test;
        }
        public void run() {
            long startAt = System.currentTimeMillis();
            while(true){
                int i = atomic.getAndIncrement();
                int val = RandomUtils.nextInt(100000);
                long bytes = test.get(val) ;
                if(i % 10000 == 0){
                    long timeUsed = System.currentTimeMillis() - startAt;
                    times.add(timeUsed);
                    startAt = System.currentTimeMillis();
                }
            }
        }
    };

    public void startupBenchmarkWrite(int threads){
        for(int i=0;i<threads;i++){
            Threads.startup(new Writer(this));
        }
    }

    public void startupBenchmarkRead(int threads){
        for(int i=0;i<threads;i++){
            Threads.startup(new Reader(this));
        }
    }

}
