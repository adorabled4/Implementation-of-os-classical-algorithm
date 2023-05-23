package com.os.dynamicmatching.sample;

import com.os.dynamicmatching.algorithms.BF;
import com.os.dynamicmatching.algorithms.WF;
import com.os.dynamicmatching.util.RandomTestUtil;

import java.util.List;

/**
 * @author adorabled4
 * @className WFExample
 * @date : 2023-5-16 13:15:16
 **/
public class WFExample {

    public static void main(String[] args) {
        testWF(500, 10);
    }

    /**
     * 测试 动态分区匹配算法  仅仅测试当前算法的执行方式以及内容
     *
     * @param cap         frame容量
     * @param processSize 进程的个数
     */
    static void testWF(int cap, int processSize) {
        List<Long> timeCounts = RandomTestUtil.testDynamicMatching(cap, processSize, WF.class);
        long timeCount = 0;
        for (Long count : timeCounts) {
            timeCount += count;
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 分配总耗时: " + timeCount + "(ns)");
    }

        /*
        下面是一组测试数据
        2023-05--16 01:16:21 [ADD]添加进程: 1001 驻留时间: 8(s) 需要Frame: 60
        2023-05--16 01:16:21 当前空闲分区情况:
            [Partition] #: 1:Size: 500, Range: [0, 499]
        2023-05--16 01:16:21 [ALLOCATE]为进程1001分配, 大小: 60  起始frame: 0 本次分配耗时 : 29621(ns)
        2023-05--16 01:16:23 [ADD]添加进程: 1002 驻留时间: 1(s) 需要Frame: 150
        2023-05--16 01:16:23 当前空闲分区情况:
            [Partition] #: 1:Size: 440, Range: [60, 499]
        2023-05--16 01:16:23 [ALLOCATE]为进程1002分配, 大小: 150  起始frame: 60 本次分配耗时 : 350(ns)
        2023-05--16 01:16:24 [GC]回收进程 1002 ,大小: 150
        2023-05--16 01:16:25 [ADD]添加进程: 1003 驻留时间: 5(s) 需要Frame: 40
        2023-05--16 01:16:25 当前空闲分区情况:
            [Partition] #: 1:Size: 440, Range: [60, 499]
        2023-05--16 01:16:25 [ALLOCATE]为进程1003分配, 大小: 40  起始frame: 60 本次分配耗时 : 255(ns)
        2023-05--16 01:16:27 [ADD]添加进程: 1004 驻留时间: 2(s) 需要Frame: 80
        2023-05--16 01:16:27 当前空闲分区情况:
            [Partition] #: 1:Size: 400, Range: [100, 499]
        2023-05--16 01:16:27 [ALLOCATE]为进程1004分配, 大小: 80  起始frame: 100 本次分配耗时 : 334(ns)
        2023-05--16 01:16:29 [GC]回收进程 1001 ,大小: 60
        2023-05--16 01:16:29 [GC]回收进程 1004 ,大小: 80
        2023-05--16 01:16:29 [ADD]添加进程: 1005 驻留时间: 3(s) 需要Frame: 100
        2023-05--16 01:16:29 当前空闲分区情况:
            [Partition] #: 1:Size: 60, Range: [0, 59]
            [Partition] #: 2:Size: 400, Range: [100, 499]
        2023-05--16 01:16:29 [FAILED]添加进程到阻塞队列: 1005
        2023-05--16 01:16:30 [GC]回收进程 1003 ,大小: 40
        2023-05--16 01:16:30 当前空闲分区情况:
            [Partition] #: 1:Size: 500, Range: [0, 499]
        2023-05--16 01:16:30 [ALLOCATE]为进程1005分配, 大小: 100  起始frame: 0 本次分配耗时 : 381(ns)
        2023-05--16 01:16:31 [ADD]添加进程: 1006 驻留时间: 2(s) 需要Frame: 70
        2023-05--16 01:16:31 当前空闲分区情况:
            [Partition] #: 1:Size: 400, Range: [100, 499]
        2023-05--16 01:16:31 [ALLOCATE]为进程1006分配, 大小: 70  起始frame: 100 本次分配耗时 : 248(ns)
        2023-05--16 01:16:33 [GC]回收进程 1005 ,大小: 100
        2023-05--16 01:16:33 [ADD]添加进程: 1007 驻留时间: 1(s) 需要Frame: 170
        2023-05--16 01:16:33 [GC]回收进程 1006 ,大小: 70
        2023-05--16 01:16:33 当前空闲分区情况:
            [Partition] #: 1:Size: 500, Range: [0, 499]
        2023-05--16 01:16:33 [ALLOCATE]为进程1007分配, 大小: 170  起始frame: 0 本次分配耗时 : 340(ns)
        2023-05--16 01:16:34 [GC]回收进程 1007 ,大小: 170
        2023-05--16 01:16:35 [ADD]添加进程: 1008 驻留时间: 4(s) 需要Frame: 70
        2023-05--16 01:16:35 当前空闲分区情况:
            [Partition] #: 1:Size: 500, Range: [0, 499]
        2023-05--16 01:16:35 [ALLOCATE]为进程1008分配, 大小: 70  起始frame: 0 本次分配耗时 : 305(ns)
        2023-05--16 01:16:37 [ADD]添加进程: 1009 驻留时间: 6(s) 需要Frame: 60
        2023-05--16 01:16:37 当前空闲分区情况:
            [Partition] #: 1:Size: 430, Range: [70, 499]
        2023-05--16 01:16:37 [ALLOCATE]为进程1009分配, 大小: 60  起始frame: 70 本次分配耗时 : 365(ns)
        2023-05--16 01:16:39 [GC]回收进程 1008 ,大小: 70
        2023-05--16 01:16:39 [ADD]添加进程: 1010 驻留时间: 6(s) 需要Frame: 120
        2023-05--16 01:16:39 当前空闲分区情况:
            [Partition] #: 1:Size: 70, Range: [0, 69]
            [Partition] #: 2:Size: 370, Range: [130, 499]
        2023-05--16 01:16:39 [FAILED]添加进程到阻塞队列: 1010
        2023-05--16 01:16:40 当前空闲分区情况:
            [Partition] #: 1:Size: 70, Range: [0, 69]
            [Partition] #: 2:Size: 370, Range: [130, 499]
        2023-05--16 01:16:40 [FAILED]添加进程到阻塞队列: 1010
        2023-05--16 01:16:41 当前空闲分区情况:
            [Partition] #: 1:Size: 70, Range: [0, 69]
            [Partition] #: 2:Size: 370, Range: [130, 499]
        2023-05--16 01:16:41 [FAILED]添加进程到阻塞队列: 1010
        2023-05--16 01:16:42 当前空闲分区情况:
            [Partition] #: 1:Size: 70, Range: [0, 69]
            [Partition] #: 2:Size: 370, Range: [130, 499]
        2023-05--16 01:16:42 [FAILED]添加进程到阻塞队列: 1010
        2023-05--16 01:16:43 [GC]回收进程 1009 ,大小: 60
        2023-05--16 01:16:43 当前空闲分区情况:
            [Partition] #: 1:Size: 500, Range: [0, 499]
        2023-05--16 01:16:43 [ALLOCATE]为进程1010分配, 大小: 120  起始frame: 0 本次分配耗时 : 385(ns)
        [测试结束] 分配总耗时: 31818(ns)
        2023-05--16 01:16:49 [GC]回收进程 1010 ,大小: 120
     */
}
