package com.lamfire.pandora;

import com.lamfire.utils.Threads;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-24
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
public class PandoraSchedules {

    private PandoraSchedules(){}

    private static final ScheduledThreadPoolExecutor executor = Threads.newSingleThreadScheduledExecutor(Threads.makeThreadFactory("Pandora/schedule"));


    public static java.util.concurrent.ScheduledFuture<?> scheduleWithFixedDelay(java.lang.Runnable command, long initialDelay, long delay, java.util.concurrent.TimeUnit unit) {
        return executor.scheduleWithFixedDelay(command,initialDelay,delay,unit);
    }

}
