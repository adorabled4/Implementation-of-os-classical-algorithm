package com.dhx.sample;

import com.dhx.algorithms.Priority;
import com.dhx.algorithms.SJF;
import com.dhx.model.Process;

/**
 * @author dhx_
 * @className PrioritySample
 * @date : 2023/04/09/ 13:49
 **/
public class PrioritySample {
    public static final String BASE_PID="1000";
    static Priority priority;

    public static void main(String[] args) {
        priority = new Priority(5);
        new Thread(()->{
            addProcess2Queue(7,5,5,10);
        }).start();
        priority.start();
    }
    /**
     * 下面是一段测试的结果 , 可以看到进程的执行顺序通过优先级由高到低执行
     * 04-09 01:53:48.048[arrive]进程到达, 进程ID :1000 预计用时: 2(s)
     * 04-09 01:53:48.048[running]执行进程  进程ID :1000	耗时: 2(s) , 进程优先级为: 9
     * 04-09 01:53:52.052[arrive]进程到达, 进程ID :1001 预计用时: 5(s)
     * 04-09 01:53:52.052[running]执行进程  进程ID :1001	耗时: 5(s) , 进程优先级为: 5
     * 04-09 01:53:54.054[arrive]进程到达, 进程ID :1002 预计用时: 3(s)
     * 04-09 01:53:54.054[arrive]进程到达, 进程ID :1003 预计用时: 3(s)
     * 04-09 01:53:55.055[arrive]进程到达, 进程ID :1004 预计用时: 2(s)
     * 04-09 01:53:57.057[running]执行进程  进程ID :1003	耗时: 3(s) , 进程优先级为: 8
     * 04-09 01:53:58.058[arrive]进程到达, 进程ID :1005 预计用时: 2(s)
     * 04-09 01:54:00.000[arrive]进程到达, 进程ID :1006 预计用时: 3(s)
     * 04-09 01:54:00.000[running]执行进程  进程ID :1006	耗时: 3(s) , 进程优先级为: 10
     * 04-09 01:54:03.003[running]执行进程  进程ID :1005	耗时: 2(s) , 进程优先级为: 6
     * 04-09 01:54:05.005[running]执行进程  进程ID :1004	耗时: 2(s) , 进程优先级为: 4
     * 04-09 01:54:07.007[running]执行进程  进程ID :1002	耗时: 3(s) , 进程优先级为: 1
     */

    /**
     * 添加进程
     * @param size 进程的数量
     * @param maxRunTime 生成的进程的最大执行时间
     * @param maxSleepTime 生成进程的过程中的最大间隔时间
     */
    private static void addProcess2Queue(int size,int maxRunTime ,int maxSleepTime,int maxOrder){
        for (int i = 0; i < size; i++) {
            Process process = new Process(Integer.parseInt(BASE_PID) + i, (int) ((Math.random()*10)%maxRunTime)+1);
            // 设置进程的优先级为随机数
            process.setOrder((int) ((Math.random()*10)%maxOrder)+1);
            priority.addProcess(process);
            try {
                Thread.sleep(1000 * (int) ((Math.random()*10)%maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
