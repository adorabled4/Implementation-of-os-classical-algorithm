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
    public static int cnt=0;

    public static void main(String[] args) {
        // 使用thread.sleep 模拟进程的执行
        FCFS fcfs = new FCFS(3);
        new Thread(()->{
            Process processA= new Process(Integer.parseInt(BASE_PID)+cnt++,3);
            Process processB= new Process(Integer.parseInt(BASE_PID)+cnt++,4);
            Process processC= new Process(Integer.parseInt(BASE_PID)+cnt++,5);
            try {
                fcfs.addProcess(processA);
                Thread.sleep(1000); // 设置间隔时间
                fcfs.addProcess(processB);
                Thread.sleep(1000);
                fcfs.addProcess(processC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        fcfs.start();
    }

}

