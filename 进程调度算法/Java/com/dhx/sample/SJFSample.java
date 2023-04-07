package com.dhx.sample;

import com.dhx.algorithms.FCFS;
import com.dhx.algorithms.SJF;
import com.dhx.model.Process;

import java.util.ArrayList;
import java.util.List;


/**
 * @author dhx_
 * @className FCFSSample
 * @date : 2023/03/27/ 22:11
 **/
public class SJFSample {
    public static final String BASE_PID="1000";
    public static int cnt=0;
    static SJF sjf;

    public static void main(String[] args) {
        sjf = new SJF(5);
        new Thread(()->{
            addProcess2Queue(6,5,5);
        }).start();
        sjf.start();
    }

    /**
     * 添加进程
     * @param size 进程的数量
     * @param maxRunTime 生成的进程的最大执行时间
     * @param maxSleepTime 生成进程的过程中的最大间隔时间
     */
    private static void addProcess2Queue(int size,int maxRunTime ,int maxSleepTime){
        for (int i = 0; i < size; i++) {
            Process process = new Process(Integer.parseInt(BASE_PID) + i, (int) ((Math.random()*10)%maxRunTime)+1);
            sjf.addProcess(process);
            try {
                Thread.sleep(1000 * (int) ((Math.random()*10)%maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 旧的示例 , 可以直接写死进程的执行时间以及到达时间等 , **随机的实现可能体现不出来算法的特性**
     */
    private static void oldSample(){
        // 使用thread.sleep 模拟进程的执行
        // 可以看到 虽然 B C进程都在等待队列里面, 并且B是先到达的进程, 但是由于B的执行时间长于C,因此先执行C进程
        new Thread(()->{
            Process processA= new Process(Integer.parseInt(BASE_PID)+cnt++,3);
            Process processB= new Process(Integer.parseInt(BASE_PID)+cnt++,2);
            Process processC= new Process(Integer.parseInt(BASE_PID)+cnt++,1);
            try {
                sjf.addProcess(processA);
                Thread.sleep(1000); // 设置间隔时间
                sjf.addProcess(processB);
                Thread.sleep(1000);
                sjf.addProcess(processC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        sjf.start();
    }

}

