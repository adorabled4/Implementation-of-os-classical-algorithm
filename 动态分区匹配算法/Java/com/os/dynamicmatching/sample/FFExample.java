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

    public static final Integer BASE_PID=1000;

    public static void main(String[] args){
        testFF(500, 10);
    }

    /**
     * 测试 动态分区匹配算法  仅仅测试当前算法的执行方式以及内容
     * @param cap frame容量
     * @param processSize 进程的个数
     */
    static void testFF(int cap,int processSize){
        List<Long> timeCounts = RandomTestUtil.testDynamicMatching(cap, processSize, FF.class);
        long timeCount =0;
        for (Long count : timeCounts) {
            timeCount+=count;
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 总耗时: "+timeCount+ "(s)");
    }

        /*
    下面是一组测试结果
    2023-05--13 08:20:05 [ADD]添加进程: 1001 预计耗时: 5 需要Frame: 120
    2023-05--13 08:20:05 [ALLOCATE]为进程1001分配, 大小: 120  起始frame: 0 耗时 : 0(s)
    2023-05--13 08:20:07 [ADD]添加进程: 1002 预计耗时: 4 需要Frame: 50
    2023-05--13 08:20:07 [ALLOCATE]为进程1002分配, 大小: 50  起始frame: 120 耗时 : 2(s)
    2023-05--13 08:20:09 [ADD]添加进程: 1003 预计耗时: 6 需要Frame: 30
    2023-05--13 08:20:09 [ALLOCATE]为进程1003分配, 大小: 30  起始frame: 170 耗时 : 4(s)
    2023-05--13 08:20:10 [GC]回收进程 1001 ,大小: 120
    2023-05--13 08:20:11 [GC]回收进程 1002 ,大小: 50
    2023-05--13 08:20:11 [ADD]添加进程: 1004 预计耗时: 7 需要Frame: 20
    2023-05--13 08:20:11 [ALLOCATE]为进程1004分配, 大小: 20  起始frame: 0 耗时 : 6(s)
    2023-05--13 08:20:13 [ADD]添加进程: 1005 预计耗时: 7 需要Frame: 10
    2023-05--13 08:20:13 [ALLOCATE]为进程1005分配, 大小: 10  起始frame: 20 耗时 : 8(s)
    2023-05--13 08:20:15 [GC]回收进程 1003 ,大小: 30
    2023-05--13 08:20:15 [ADD]添加进程: 1006 预计耗时: 3 需要Frame: 50
    2023-05--13 08:20:15 [ALLOCATE]为进程1006分配, 大小: 50  起始frame: 30 耗时 : 10(s)
    2023-05--13 08:20:17 [ADD]添加进程: 1007 预计耗时: 5 需要Frame: 150
    2023-05--13 08:20:17 [ALLOCATE]为进程1007分配, 大小: 150  起始frame: 80 耗时 : 12(s)
    2023-05--13 08:20:18 [GC]回收进程 1004 ,大小: 20
    2023-05--13 08:20:18 [GC]回收进程 1006 ,大小: 50
    2023-05--13 08:20:19 [ADD]添加进程: 1008 预计耗时: 2 需要Frame: 180
    2023-05--13 08:20:19 [ALLOCATE]为进程1008分配, 大小: 180  起始frame: 230 耗时 : 14(s)
    2023-05--13 08:20:20 [GC]回收进程 1005 ,大小: 10
    2023-05--13 08:20:21 [GC]回收进程 1008 ,大小: 180
    2023-05--13 08:20:21 [ADD]添加进程: 1009 预计耗时: 3 需要Frame: 100
    2023-05--13 08:20:21 [ALLOCATE]为进程1009分配, 大小: 100  起始frame: 230 耗时 : 16(s)
    2023-05--13 08:20:22 [GC]回收进程 1007 ,大小: 150
    2023-05--13 08:20:23 [ADD]添加进程: 1010 预计耗时: 6 需要Frame: 60
    2023-05--13 08:20:23 [ALLOCATE]为进程1010分配, 大小: 60  起始frame: 0 耗时 : 18(s)
    2023-05--13 08:20:24 [GC]回收进程 1009 ,大小: 100
    [测试结束] 总耗时: 90(s)
    2023-05--13 08:20:29 [GC]回收进程 1010 ,大小: 60
     */
}
