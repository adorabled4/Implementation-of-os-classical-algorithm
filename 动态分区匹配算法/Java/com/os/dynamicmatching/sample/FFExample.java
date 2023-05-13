package com.os.dynamicmatching.sample;

import com.os.dynamicmatching.algorithms.FF;
import com.os.dynamicmatching.model.Process;
import com.os.dynamicmatching.util.TimeUtil;

/**
 * @author adorabled4
 * @className FFExample
 * @date : 2023/05/13/ 15:45
 **/
public class FFExample {

    public static final Integer BASE_PID=1000;

    public static void main(String[] args) {
        testDM(500,10,10);
    }
    /*
    下面是一组测试结果
        2023-05--13 05:11:12 [ADD]添加进程: 1001 预计耗时: 6 需要Frame: 200
        2023-05--13 05:11:12 [ALLOCATE]为进程1001分配, 大小: 200  起始frame: 0
        2023-05--13 05:11:14 [ADD]添加进程: 1002 预计耗时: 4 需要Frame: 60
        2023-05--13 05:11:14 [ALLOCATE]为进程1002分配, 大小: 60  起始frame: 200
        2023-05--13 05:11:16 [ADD]添加进程: 1003 预计耗时: 9 需要Frame: 150
        2023-05--13 05:11:16 [ALLOCATE]为进程1003分配, 大小: 150  起始frame: 260
        2023-05--13 05:11:18 [GC]回收进程 1002 ,大小: 60
        2023-05--13 05:11:18 [GC]回收进程 1001 ,大小: 200
        2023-05--13 05:11:18 [ADD]添加进程: 1004 预计耗时: 6 需要Frame: 100
        2023-05--13 05:11:18 [ALLOCATE]为进程1004分配, 大小: 100  起始frame: 0
        2023-05--13 05:11:20 [ADD]添加进程: 1005 预计耗时: 5 需要Frame: 10
        2023-05--13 05:11:20 [ALLOCATE]为进程1005分配, 大小: 10  起始frame: 100
        2023-05--13 05:11:22 [ADD]添加进程: 1006 预计耗时: 6 需要Frame: 180
        2023-05--13 05:11:22 [FAILED]添加进程到阻塞队列: 1006
        2023-05--13 05:11:23 [FAILED]添加进程到阻塞队列: 1006
        2023-05--13 05:11:24 [GC]回收进程 1004 ,大小: 100
        2023-05--13 05:11:24 [ADD]添加进程: 1007 预计耗时: 4 需要Frame: 170
        2023-05--13 05:11:24 [FAILED]添加进程到阻塞队列: 1006
        2023-05--13 05:11:24 [FAILED]添加进程到阻塞队列: 1007
        2023-05--13 05:11:25 [GC]回收进程 1003 ,大小: 150
        2023-05--13 05:11:25 [GC]回收进程 1005 ,大小: 10
        2023-05--13 05:11:25 [ALLOCATE]为进程1006分配, 大小: 180  起始frame: 0
        2023-05--13 05:11:26 [ADD]添加进程: 1008 预计耗时: 4 需要Frame: 60
        2023-05--13 05:11:26 [ALLOCATE]为进程1008分配, 大小: 60  起始frame: 180
        2023-05--13 05:11:26 [ALLOCATE]为进程1007分配, 大小: 170  起始frame: 240
        2023-05--13 05:11:28 [ADD]添加进程: 1009 预计耗时: 5 需要Frame: 10
        2023-05--13 05:11:28 [ALLOCATE]为进程1009分配, 大小: 10  起始frame: 410
        2023-05--13 05:11:30 [GC]回收进程 1008 ,大小: 60
        2023-05--13 05:11:30 [GC]回收进程 1007 ,大小: 170
        2023-05--13 05:11:30 [ADD]添加进程: 1010 预计耗时: 9 需要Frame: 80
        2023-05--13 05:11:30 [ALLOCATE]为进程1010分配, 大小: 80  起始frame: 180
        2023-05--13 05:11:31 [GC]回收进程 1006 ,大小: 180
        2023-05--13 05:11:33 [GC]回收进程 1009 ,大小: 10
        2023-05--13 05:11:39 [GC]回收进程 1010 ,大小: 80
     */

    /**
     * 测试 动态分区匹配算法
     * @param cap frame容量
     * @param processSize 进程的个数
     * @param maxProcessRunTime 进程的最大运行时间
     */
    static void testDM(int cap,int processSize,int maxProcessRunTime){
        FF mem = new FF(cap);
        mem.start();
        new Thread(()->{
            for (int i = 0; i < processSize; i++) {
                int runTime = (int)(Math.random()*100)%maxProcessRunTime +1 ;
                // 随机模拟frameSize , 范围为 10~200
                int frameSize = (int) (Math.random() * 20+ 1 )* 10;
                Process process = new Process(BASE_PID + i + 1, runTime,frameSize);
                System.out.println(TimeUtil.getCurrentTime()+"\u001B[31m\u001B[1m[ADD]\u001B[0m添加进程: "+process.getPID()+ " 预计耗时: "+ process.getRunTime()+ " 需要Frame: "+ process.getFrameSize());
                mem.allocate(process);
                // 每间隔2s分配一个进程
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            mem.setRunFlag(false);
        }).start();
    }
}
