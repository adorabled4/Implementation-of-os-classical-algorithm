package com.os.dynamicmatching.util;

import com.os.dynamicmatching.model.Process;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author adorabled4
 * @className RandomTestUtil 建立通用测试类 , 保证每种算法的测试数据相同 , 以对比效率
 * @date : 2023/05/13/ 19:23
 **/
public class RandomTestUtil {

    public static final Integer BASE_PID = 1000;

    public static final Integer DEFAULT_PROCESS_SIZE = 1000;
    public static final Integer DEFAULT_MAX_PROCESS_RUNTIME = 10;

    public static final List<Process> processes;

    /**
     * 设置线程池 进行内存回收
     */
    public static ThreadPoolExecutor threadPool;
    static {
        processes = new ArrayList<>();
        // 初始化1000 个模拟进程
        for (int i = 0; i < DEFAULT_PROCESS_SIZE; i++) {
            int runTime = (int) (Math.random() * 100) % DEFAULT_MAX_PROCESS_RUNTIME + 1;
            // 随机模拟frameSize , 范围为 10~200
            int frameSize = (int) (Math.random() * 20 + 1) * 10;
            Process process = new Process(BASE_PID + i + 1, runTime, frameSize);
            processes.add(process);
        }
        threadPool=new ThreadPoolExecutor(10,20,20, TimeUnit.SECONDS,
                new LinkedBlockingDeque());
    }




    /**
     * 测试 动态分区匹配算法
     * @param cap               frame容量
     * @param processSize       进程的个数
     * @param mem 测试类型
     * @return 返回 耗时数组
     */
    public static List<Long> testDynamicMatching(int cap, int processSize, Class mem ) {
        try {
            Constructor constructor = mem.getConstructor(int.class);
            Object object = constructor.newInstance(cap);
            List<Long> timeCounts = new ArrayList<>();
            Method run = mem.getDeclaredMethod("run");
            Method allocate = mem.getDeclaredMethod("allocate", Process.class);
            run.setAccessible(true);
            Thread startMem = new Thread((Runnable) object);
            Thread testAllocate = new Thread(() -> {
                try {
                    for (int i = 0; i < processSize; i++) {
                        Process process = processes.get(i);
                        // 随机模拟frameSize , 范围为 10~200
                        System.out.println(TimeUtil.getCurrentTime() + "\u001B[31m\u001B[1m[ADD]\u001B[0m添加进程: " + process.getPID() + " 预计耗时: " + process.getRunTime() + " 需要Frame: " + process.getFrameSize());
                        Object timeConsume = allocate.invoke(object, process);
                        long consume = (long) timeConsume;
                        if(consume!=-1){
                            timeCounts.add(consume);
                        }
                        // 每间隔2s分配一个进程
                        Thread.sleep(2 * 1000);
                    }
                    Method runFlag = mem.getDeclaredMethod("setRunFlag", boolean.class);
                    runFlag.invoke(object, false);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            startMem.start();
            testAllocate.start();
            // 通过join使得 等到两个线程执行完毕之后才返回 : 调用 join() 方法会阻塞当前线程，直到该方法所属的线程对象执行完毕。
            startMem.join();
            testAllocate.join();
            return timeCounts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
