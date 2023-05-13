package com.os.dynamicmatching.util;


import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author adorabled4
 * @className ThreadUtil
 * @date : 2023/05/13/ 16:03
 **/
public class ThreadUtil {

    /**
     * 设置线程池 进行内存回收
     */
    public static ThreadPoolExecutor threadPool;

    static {
        threadPool=new ThreadPoolExecutor(10,20,20, TimeUnit.SECONDS,
                new LinkedBlockingDeque());
    }

}
