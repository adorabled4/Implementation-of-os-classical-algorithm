package com.os.dynamicmatching.sample;

import com.os.dynamicmatching.algorithms.FF;
import com.os.dynamicmatching.model.Process;
import com.os.dynamicmatching.util.RandomTestUtil;
import com.os.dynamicmatching.util.TimeUtil;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

/**
 * @author adorabled4
 * @className FFExample
 * @date : 2023/05/13/ 15:45
 **/
public class FFExample {

    public static void main(String[] args) {
        testFF(500, 10);
    }

    /**
     * 测试 动态分区匹配算法  仅仅测试当前算法的执行方式以及内容
     *
     * @param cap         frame容量
     * @param processSize 进程的个数
     */
    static void testFF(int cap, int processSize) {
        List<Long> timeCounts = RandomTestUtil.testDynamicMatching(cap, processSize, FF.class);
        long timeCount = 0;
        for (Long count : timeCounts) {
            timeCount += count;
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 分配总耗时: " + timeCount + "(ms)");
    }

        /*
    下面是一组测试结果
    2023-05--14 10:01:07 [ADD]添加进程: 1001 驻留时间: 1 需要Frame: 50
    2023-05--14 10:01:07 [ALLOCATE]为进程1001分配, 大小: 50  起始frame: 0 本次分配耗时 : 42(ms)
    2023-05--14 10:01:08 [GC]回收进程 1001 ,大小: 50
    2023-05--14 10:01:09 [ADD]添加进程: 1002 驻留时间: 3 需要Frame: 170
    2023-05--14 10:01:09 [ALLOCATE]为进程1002分配, 大小: 170  起始frame: 0 本次分配耗时 : 82(ms)
    2023-05--14 10:01:11 [ADD]添加进程: 1003 驻留时间: 9 需要Frame: 70
    2023-05--14 10:01:11 [ALLOCATE]为进程1003分配, 大小: 70  起始frame: 170 本次分配耗时 : 37(ms)
    2023-05--14 10:01:12 [GC]回收进程 1002 ,大小: 170
    2023-05--14 10:01:13 [ADD]添加进程: 1004 驻留时间: 1 需要Frame: 80
    2023-05--14 10:01:13 [ALLOCATE]为进程1004分配, 大小: 80  起始frame: 0 本次分配耗时 : 20(ms)
    2023-05--14 10:01:14 [GC]回收进程 1004 ,大小: 80
    2023-05--14 10:01:15 [ADD]添加进程: 1005 驻留时间: 9 需要Frame: 120
    2023-05--14 10:01:15 [ALLOCATE]为进程1005分配, 大小: 120  起始frame: 0 本次分配耗时 : 30(ms)
    2023-05--14 10:01:17 [ADD]添加进程: 1006 驻留时间: 10 需要Frame: 190
    2023-05--14 10:01:17 [ALLOCATE]为进程1006分配, 大小: 190  起始frame: 240 本次分配耗时 : 212(ms)
    2023-05--14 10:01:19 [ADD]添加进程: 1007 驻留时间: 1 需要Frame: 160
    2023-05--14 10:01:19 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:01:20 [GC]回收进程 1003 ,大小: 70
    2023-05--14 10:01:20 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:01:21 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:01:21 [ADD]添加进程: 1008 驻留时间: 5 需要Frame: 40
    2023-05--14 10:01:21 [ALLOCATE]为进程1008分配, 大小: 40  起始frame: 120 本次分配耗时 : 22(ms)
    2023-05--14 10:01:22 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:01:23 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:01:23 [ADD]添加进程: 1009 驻留时间: 1 需要Frame: 120
    2023-05--14 10:01:23 [FAILED]添加进程到阻塞队列: 1009
    2023-05--14 10:01:24 [GC]回收进程 1005 ,大小: 120
    2023-05--14 10:01:24 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:01:25 [ADD]添加进程: 1010 驻留时间: 3 需要Frame: 10
    2023-05--14 10:01:25 [ALLOCATE]为进程1009分配, 大小: 120  起始frame: 0 本次分配耗时 : 32(ms)
    2023-05--14 10:01:25 [ALLOCATE]为进程1010分配, 大小: 10  起始frame: 160 本次分配耗时 : 19(ms)
    2023-05--14 10:01:26 [GC]回收进程 1008 ,大小: 40
    2023-05--14 10:01:26 [GC]回收进程 1009 ,大小: 120
    2023-05--14 10:01:26 [ALLOCATE]为进程1007分配, 大小: 160  起始frame: 0 本次分配耗时 : 63(ms)
    2023-05--14 10:01:27 [GC]回收进程 1006 ,大小: 190
    2023-05--14 10:01:27 [GC]回收进程 1007 ,大小: 160
    [测试结束] 分配总耗时: 464(ms)
    2023-05--14 10:01:28 [GC]回收进程 1010 ,大小: 10
     */
}
