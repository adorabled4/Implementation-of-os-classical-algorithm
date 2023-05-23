package com.os.dynamicmatching.algorithms;

import com.os.dynamicmatching.model.DMConstant;
import com.os.dynamicmatching.model.Frame;
import com.os.dynamicmatching.model.Process;
import com.os.dynamicmatching.util.RandomTestUtil;
import com.os.dynamicmatching.util.TimeUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author adorabled4
 * @className FF
 * @date : 2023/05/13/ 15:37
 **/
public class FF extends Thread implements Runnable {

    /**
     * 帧
     */
    List<Frame> mem;
    /**
     * 帧容量
     */
    int cap;

    /**
     * 阻塞进程
     */
    LinkedList<Process> blockingQueue;

    /**
     * 执行标志 : 一定要加上volatile , 否则不会停止 !
     */
    volatile boolean runFlag = true;

    public void setRunFlag(boolean flag) {
        runFlag = flag;
    }

    public FF() {
        mem = new ArrayList<>(DMConstant.DEFAULT_FRAME_SIZE);// 设置内存的大小
        blockingQueue = new LinkedList<>();
        for (int i = 0; i < DMConstant.DEFAULT_FRAME_SIZE; i++) {
            mem.add(new Frame(i, DMConstant.FREE));
        }
    }

    public FF(int cap) {
        mem = new ArrayList<>(cap);// 设置内存的大小
        this.cap = cap;
        blockingQueue = new LinkedList<>();
        for (int i = 0; i < cap; i++) {
            mem.add(new Frame(i, DMConstant.FREE));
        }
    }

    /**
     * 分配内存
     *
     * @param process
     */
    public synchronized long allocate(Process process) {
        // 从内存的起始点开始遍历，找到第一个空闲的分区
        long start = System.nanoTime();
        for (int i = 0; i < mem.size(); i++) {
            if (mem.get(i).getStatus() == DMConstant.FREE) {
                // 超出限制
                if (i + process.getFrameSize() >= cap) {
                    process.setStatus(Process.BLOCK);
                    blockingQueue.add(process);
                    System.out.println(TimeUtil.getCurrentTime() + "\u001B[32m\u001B[1m[FAILED]\u001B[0m添加进程到阻塞队列: " + process.getPID());
                    return -1;
                } else {
                    if (mem.get(i + process.getFrameSize() - 1).getStatus() == DMConstant.FREE) {
                        boolean isAllFree = true;
                        // 查看是否有连续空闲的分区
                        for (int j = i + 1; j < i + process.getFrameSize(); j++) {
                            if (mem.get(j).getStatus() == DMConstant.USED) {
                                isAllFree = false;
                                break;
                            }
                        }
                        // 进行分配
                        if (isAllFree) {
                            for (int j = i + 1; j < i + process.getFrameSize(); j++) {
                                mem.get(j).setStatus(DMConstant.USED);
                            }
                            process.setStatus(Process.READY);
                            // 分配成功, 打印日志
                            long timeConsume = (System.nanoTime() - start) / 1000;
                            process.setTimeConsume(timeConsume);
                            System.out.println(TimeUtil.getCurrentTime() + "\u001B[33m\u001B[1m[ALLOCATE]\u001B[0m为进程" + process.getPID() + "分配, 大小: " + process.getFrameSize() + "  起始frame: " + i + " 本次分配耗时 : " + process.getTimeConsume() + "(ms)");
                            int finalI = i;
                            // 执行进程之后，进行回收
                            RandomTestUtil.runAndMemCollect(process, finalI,mem);
                            return process.getTimeConsume();
                        }
                    }
                }
            }
        }
        process.setStatus(Process.BLOCK);
        blockingQueue.add(process);
        System.out.println(TimeUtil.getCurrentTime() + "\u001B[32m\u001B[1m[FAILED]\u001B[0m添加进程到阻塞队列: " + process.getPID());
        return -1;
    }



    @Override
    public void run() {
        while (runFlag) {
            while (blockingQueue.size() > 0) {
                // 先睡眠 , 然后再进行添加, 防止某些时刻帧不足 , 占用极高的进程持续的访问锁, 浪费资源
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Process poll = blockingQueue.poll();
                allocate(poll);
            }
        }
    }

}
