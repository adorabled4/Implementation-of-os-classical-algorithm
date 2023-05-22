package com.dhx.sample;

import com.dhx.algorithms.FCFS;
import com.dhx.model.Process;


/**
 * @author dhx_
 * @className FCFSSample
 * @date : 2023/03/27/ 22:11
 **/
public class FCFSSample {
    public static final String BASE_PID="1000";
    static FCFS fcfs ;


    public static void main(String[] args) {
        // 使用thread.sleep 模拟进程的执行
        fcfs = new FCFS(5);
        Thread addThread = new Thread(() -> {
            addProcess2Queue(6, 5, 3);
        });
        Thread runThread = new Thread(() -> {
            fcfs.start();
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
            fcfs.addProcess(process);
            try {
                Thread.sleep(1000 * (int) ((Math.random() * 10) % maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

