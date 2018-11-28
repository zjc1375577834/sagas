package com.zjc.sagas.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {
    private static ThreadPoolExecutor poolExecuute = new ThreadPoolExecutor(1,10,10000L, TimeUnit.SECONDS,new ArrayBlockingQueue(200,true));
    public static ThreadPoolExecutor getInstance(){
        return poolExecuute;
    }
}
