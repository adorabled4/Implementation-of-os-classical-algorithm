package com.os.dynamicmatching.sample;

import com.os.dynamicmatching.algorithms.FF;
import com.os.dynamicmatching.algorithms.NF;
import com.os.dynamicmatching.model.Process;
import com.os.dynamicmatching.util.RandomTestUtil;
import com.os.dynamicmatching.util.TimeUtil;

import java.util.List;

/**
 * @author adorabled4
 * @className NFExample
 * @date : 2023-5-13 19:13:31
 **/
public class NFExample {


    public static void main(String[] args){
        testNF(500, 10);
    }

    /**
     * 测试 动态分区匹配算法  仅仅测试当前算法的执行方式以及内容
     * @param cap frame容量
     * @param processSize 进程的个数
     */
    static void testNF(int cap,int processSize){
        List<Long> timeCounts = RandomTestUtil.testDynamicMatching(cap, processSize, NF.class);
        long timeCount =0;
        for (Long count : timeCounts) {
            timeCount+=count;
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 总耗时: "+timeCount+ "(ms)");
    }
    /*
    下面是一组测试示例
    2023-05--14 10:41:20 [ADD]添加进程: 1001 驻留时间: 7(s) 需要Frame: 160
    2023-05--14 10:41:20 [ALLOCATE]为进程1001分配, 大小: 160  起始frame: 0 本次分配耗时 : 93(ms)
    2023-05--14 10:41:22 [ADD]添加进程: 1002 驻留时间: 9(s) 需要Frame: 140
    2023-05--14 10:41:22 [ALLOCATE]为进程1002分配, 大小: 140  起始frame: 160 本次分配耗时 : 123(ms)
    2023-05--14 10:41:24 [ADD]添加进程: 1003 驻留时间: 9(s) 需要Frame: 80
    2023-05--14 10:41:24 [ALLOCATE]为进程1003分配, 大小: 80  起始frame: 300 本次分配耗时 : 44(ms)
    2023-05--14 10:41:26 [ADD]添加进程: 1004 驻留时间: 8(s) 需要Frame: 180
    2023-05--14 10:41:26 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:27 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:27 [GC]回收进程 1001 ,大小: 160
    2023-05--14 10:41:28 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:28 [ADD]添加进程: 1005 驻留时间: 9(s) 需要Frame: 50
    2023-05--14 10:41:28 [ALLOCATE]为进程1005分配, 大小: 50  起始frame: 0 本次分配耗时 : 12(ms)
    2023-05--14 10:41:29 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:30 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:30 [ADD]添加进程: 1006 驻留时间: 10(s) 需要Frame: 100
    2023-05--14 10:41:30 [ALLOCATE]为进程1006分配, 大小: 100  起始frame: 50 本次分配耗时 : 24(ms)
    2023-05--14 10:41:31 [GC]回收进程 1002 ,大小: 140
    2023-05--14 10:41:31 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:32 [ADD]添加进程: 1007 驻留时间: 1(s) 需要Frame: 200
    2023-05--14 10:41:32 [FAILED]添加进程到阻塞队列: 1004
    2023-05--14 10:41:32 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:33 [GC]回收进程 1003 ,大小: 80
    2023-05--14 10:41:33 [ALLOCATE]为进程1004分配, 大小: 180  起始frame: 150 本次分配耗时 : 62(ms)
    2023-05--14 10:41:34 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:34 [ADD]添加进程: 1008 驻留时间: 4(s) 需要Frame: 80
    2023-05--14 10:41:34 [ALLOCATE]为进程1008分配, 大小: 80  起始frame: 330 本次分配耗时 : 47(ms)
    2023-05--14 10:41:35 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:36 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:36 [ADD]添加进程: 1009 驻留时间: 3(s) 需要Frame: 90
    2023-05--14 10:41:36 [FAILED]添加进程到阻塞队列: 1009
    2023-05--14 10:41:37 [GC]回收进程 1005 ,大小: 50
    2023-05--14 10:41:37 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:38 [GC]回收进程 1008 ,大小: 80
    2023-05--14 10:41:38 [ALLOCATE]为进程1009分配, 大小: 90  起始frame: 330 本次分配耗时 : 89(ms)
    2023-05--14 10:41:38 [ADD]添加进程: 1010 驻留时间: 1(s) 需要Frame: 40
    2023-05--14 10:41:38 [ALLOCATE]为进程1010分配, 大小: 40  起始frame: 0 本次分配耗时 : 9(ms)
    2023-05--14 10:41:39 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:39 [GC]回收进程 1010 ,大小: 40
    2023-05--14 10:41:40 [GC]回收进程 1006 ,大小: 100
    2023-05--14 10:41:40 [FAILED]添加进程到阻塞队列: 1007
    2023-05--14 10:41:41 [GC]回收进程 1004 ,大小: 180
    2023-05--14 10:41:41 [GC]回收进程 1009 ,大小: 90
    2023-05--14 10:41:41 [ALLOCATE]为进程1007分配, 大小: 200  起始frame: 0 本次分配耗时 : 159(ms)
    [测试结束] 总耗时: 352(ms)
    2023-05--14 10:41:42 [GC]回收进程 1007 ,大小: 200
     */
}
