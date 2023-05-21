package com.dhx.sample;

import com.dhx.algorithms.FCFS;
import com.dhx.algorithms.MLFQ;
import com.dhx.algorithms.SJF;
import com.dhx.model.Process;


/**
 * @author dhx_
 * @className FCFSSample
 * @date : 2023-5-21 10:58:50
 **/
public class MLFQSample {
    public static final String BASE_PID = "1000";
    public static MLFQ mlfq;

    public static void main(String[] args) throws InterruptedException {
        int[]quantum = new int[]{1, 2, 3, 4, 5};
        int queueSize = 5;
        mlfq = new MLFQ(queueSize,quantum);
        Thread addThread = new Thread(() -> {
            addProcess2Queue(6, 5, 3);
        });
        Thread runThread = new Thread(() -> {
            mlfq.start();
        });
        addThread.start();
        runThread.start();
    }

    /**
     * 添加进程
     *
     * @param size         进程的数量
     * @param maxRunTime   生成的进程的最大执行时间
     * @param maxSleepTime 生成进程的过程中的最大间隔时间
     */
    private static void addProcess2Queue(int size, int maxRunTime, int maxSleepTime) {
        for (int i = 0; i < size; i++) {
            Process process = new Process(Integer.parseInt(BASE_PID) + i + 1, (int) ((Math.random() * 10) % maxRunTime) + 1);
            mlfq.addProcess(process);
            try {
                Thread.sleep(1000 * (int) ((Math.random() * 10) % maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
    下面是一组测试结构 , 如果想要突出多级队列的显示,  可以增大 maxRunTime 参数或者 减小时间片数组中元素的大小
        05-21 11:23:57[ARRIVE]进程到达, 进程ID :1001 预计用时: 3(s)
        05-21 11:23:58[ARRIVE]进程到达, 进程ID :1002 预计用时: 4(s)
        05-21 11:23:58[ARRIVE]进程到达, 进程ID :1003 预计用时: 1(s)
        05-21 11:23:58[ARRIVE]进程到达, 进程ID :1004 预计用时: 1(s)
        05-21 11:23:59[ARRIVE]进程到达, 进程ID :1005 预计用时: 5(s)
        05-21 11:23:59[ARRIVE]进程到达, 进程ID :1006 预计用时: 1(s)
        05-21 11:24:00[LACK], 进程ID :1001	耗时: 1(s)添加进程到第 2 级队列
        05-21 11:24:04[LACK], 进程ID :1002	耗时: 1(s)添加进程到第 2 级队列
        05-21 11:24:05[FINISH]执行完毕  进程ID :1003	耗时: 1(s)
        05-21 11:24:06[FINISH]执行完毕  进程ID :1004	耗时: 1(s)
        05-21 11:24:11[LACK], 进程ID :1005	耗时: 1(s)添加进程到第 2 级队列
        05-21 11:24:12[FINISH]执行完毕  进程ID :1006	耗时: 1(s)
        05-21 11:24:14[FINISH]执行完毕  进程ID :1001	耗时: 2(s)
        05-21 11:24:17[LACK], 进程ID :1002	耗时: 2(s)添加进程到第 3 级队列
        05-21 11:24:21[LACK], 进程ID :1005	耗时: 2(s)添加进程到第 3 级队列
        05-21 11:24:22[FINISH]执行完毕  进程ID :1002	耗时: 1(s)
        05-21 11:24:24[FINISH]执行完毕  进程ID :1005	耗时: 2(s)
     */
}

