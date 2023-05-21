package com.dhx.sample;

import com.dhx.algorithms.HRRN;
import com.dhx.algorithms.Priority;
import com.dhx.model.Process;

/**
 * @author dhx_
 * @className HRRNSample 通过灵活的调整生成Proces的参数,可以更加直观的了解到每个算法的特性
 * @date : 2023/04/09/ 13:49
 **/
public class HRRNSample {
    public static final String BASE_PID="1000";
    static HRRN hrrn;

    public static void main(String[] args) {
        hrrn = new HRRN(5);
        new Thread(()->{
            addProcess2Queue(7,5,2);
        }).start();
        hrrn.start();
    }

    /*
    下面是一段测试的结果 , 可以看到进程的执行顺序通过判断进程的优先权来进行
    04-10 09:07:06.006[ARRIVE]进程到达, 进程ID :1000 预计用时: 5(s)
        进程ID	优先权
        1000	0.0
    04-10 09:07:06.006[RUNNING]执行进程  进程ID :1000	耗时: 5(s)	进程优先权: 0.0
    04-10 09:07:08.008[ARRIVE]进程到达, 进程ID :1001 预计用时: 2(s)
    04-10 09:07:10.010[ARRIVE]进程到达, 进程ID :1002 预计用时: 5(s)
        进程ID	优先权
        1001	1.543
        1002	0.0
    04-10 09:07:11.011[RUNNING]执行进程  进程ID :1001	耗时: 2(s)	进程优先权: 1.543
    04-10 09:07:12.012[ARRIVE]进程到达, 进程ID :1003 预计用时: 2(s)
    04-10 09:07:13.013[ARRIVE]进程到达, 进程ID :1004 预计用时: 2(s)
        进程ID	优先权
        1004	0.041
        1003	0.0
        1002	0.6208
    04-10 09:07:13.013[RUNNING]执行进程  进程ID :1004	耗时: 2(s)	进程优先权: 0.041
    04-10 09:07:14.014[ARRIVE]进程到达, 进程ID :1005 预计用时: 3(s)
        进程ID	优先权
        1002	1.0222
        1003	0.0
        1005	0.0
    04-10 09:07:15.015[RUNNING]执行进程  进程ID :1002	耗时: 5(s)	进程优先权: 1.0222
    04-10 09:07:16.016[ARRIVE]进程到达, 进程ID :1006 预计用时: 5(s)
        进程ID	优先权
        1005	2.0276666666666667
        1003	0.0
        1006	0.0
    04-10 09:07:20.020[RUNNING]执行进程  进程ID :1005	耗时: 3(s)	进程优先权: 2.0276666666666667
        进程ID	优先权
        1006	1.4146
        1003	0.0
    04-10 09:07:23.023[RUNNING]执行进程  进程ID :1006	耗时: 5(s)	进程优先权: 1.4146
        进程ID	优先权
        1003	8.061
    04-10 09:07:28.028[RUNNING]执行进程  进程ID :1003	耗时: 2(s)	进程优先权: 8.061
    */

    /**
     * 添加进程
     * @param size 进程的数量
     * @param maxRunTime 生成的进程的最大执行时间
     * @param maxSleepTime 生成进程的过程中的最大间隔时间
     */
    private static void addProcess2Queue(int size,int maxRunTime ,int maxSleepTime){
        for (int i = 0; i < size; i++) {
            Process process = new Process(Integer.parseInt(BASE_PID) + i, (int) ((Math.random()*10)%maxRunTime)+1);
            hrrn.addProcess(process);
            long sleepTime= (int) ((Math.random()*10)%maxSleepTime) +1;
            try {
                Thread.sleep(1000 * sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
