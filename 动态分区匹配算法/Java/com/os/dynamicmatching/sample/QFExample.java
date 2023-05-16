package com.os.dynamicmatching.sample;

import com.os.dynamicmatching.algorithms.QF;
import com.os.dynamicmatching.util.RandomTestUtil;

import java.util.List;

/**
 * @author adorabled4
 * @className QFExample
 * @date : 2023-5-16 19:26:06
 **/
public class QFExample {

    public static void main(String[] args) {
        testQF(1024, 10);
    }

    /**
     * 测试 动态分区匹配算法  仅仅测试当前算法的执行方式以及内容
     *
     * @param cap         frame容量
     * @param processSize 进程的个数
     */
    static void testQF(int cap, int processSize) {
        List<Long> timeCounts = RandomTestUtil.testDynamicMatching(cap, processSize, QF.class);
        long timeCount = 0;
        for (Long count : timeCounts) {
            timeCount += count;
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 分配总耗时: " + timeCount + "(ns)");
    }

        /*
    下面是一组测试结果
    2023-05--16 07:34:45 [ADD]添加进程: 1001 驻留时间: 10(s) 需要Frame: 100
    2023-05--16 07:34:45 [ALLOCATE]为进程1001分配, 大小: 100  起始frame: 0 本次分配耗时 : 2088(ns)
    2023-05--16 07:34:47 [ADD]添加进程: 1002 驻留时间: 10(s) 需要Frame: 130
    2023-05--16 07:34:47 [ALLOCATE]为进程1002分配, 大小: 130  起始frame: 0 本次分配耗时 : 80(ns)
    2023-05--16 07:34:49 [ADD]添加进程: 1003 驻留时间: 2(s) 需要Frame: 50
    2023-05--16 07:34:49 [ALLOCATE]为进程1003分配, 大小: 50  起始frame: 0 本次分配耗时 : 81(ns)
    2023-05--16 07:34:51 [ADD]添加进程: 1004 驻留时间: 2(s) 需要Frame: 110
    2023-05--16 07:34:51 [ALLOCATE]为进程1004分配, 大小: 110  起始frame: 0 本次分配耗时 : 201(ns)
    2023-05--16 07:34:51 [GC]回收进程 1003 ,大小: 50
    2023-05--16 07:34:53 [ADD]添加进程: 1005 驻留时间: 7(s) 需要Frame: 120
    2023-05--16 07:34:53 [ALLOCATE]为进程1005分配, 大小: 120  起始frame: 0 本次分配耗时 : 60(ns)
    2023-05--16 07:34:53 [GC]回收进程 1004 ,大小: 110
    2023-05--16 07:34:55 [GC]回收进程 1001 ,大小: 100
    2023-05--16 07:34:55 [ADD]添加进程: 1006 驻留时间: 7(s) 需要Frame: 20
    2023-05--16 07:34:55 [ALLOCATE]为进程1006分配, 大小: 20  起始frame: 0 本次分配耗时 : 89(ns)
    2023-05--16 07:34:57 [GC]回收进程 1002 ,大小: 130
    2023-05--16 07:34:57 [ADD]添加进程: 1007 驻留时间: 4(s) 需要Frame: 150
    2023-05--16 07:34:57 [ALLOCATE]为进程1007分配, 大小: 150  起始frame: 0 本次分配耗时 : 67(ns)
    2023-05--16 07:34:59 [ADD]添加进程: 1008 驻留时间: 2(s) 需要Frame: 30
    2023-05--16 07:34:59 [ALLOCATE]为进程1008分配, 大小: 30  起始frame: 0 本次分配耗时 : 61(ns)
    2023-05--16 07:35:00 [GC]回收进程 1005 ,大小: 120
    2023-05--16 07:35:01 [GC]回收进程 1008 ,大小: 30
    2023-05--16 07:35:01 [GC]回收进程 1007 ,大小: 150
    2023-05--16 07:35:01 [ADD]添加进程: 1009 驻留时间: 9(s) 需要Frame: 130
    2023-05--16 07:35:01 [ALLOCATE]为进程1009分配, 大小: 130  起始frame: 0 本次分配耗时 : 64(ns)
    2023-05--16 07:35:02 [GC]回收进程 1006 ,大小: 20
    2023-05--16 07:35:03 [ADD]添加进程: 1010 驻留时间: 10(s) 需要Frame: 80
    2023-05--16 07:35:03 [ALLOCATE]为进程1010分配, 大小: 80  起始frame: 0 本次分配耗时 : 58(ns)
    [测试结束] 分配总耗时: 2849(ns)
    2023-05--16 07:35:10 [GC]回收进程 1009 ,大小: 130
    2023-05--16 07:35:13 [GC]回收进程 1010 ,大小: 80

    进程已结束,退出代码0

     */
}
