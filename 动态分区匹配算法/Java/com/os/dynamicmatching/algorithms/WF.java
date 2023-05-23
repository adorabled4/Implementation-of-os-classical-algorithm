package com.os.dynamicmatching.algorithms;

import com.os.dynamicmatching.model.DMConstant;
import com.os.dynamicmatching.model.Frame;
import com.os.dynamicmatching.model.Process;
import com.os.dynamicmatching.util.AlgorithmUtil;
import com.os.dynamicmatching.util.TimeUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author adorabled4
 * @className WF : 从所有空闲分区中找到大小最接近请求大小的分区，将其分配给程序使用。
 * @date : 2023-5-16 13:13:00
 **/
public class WF extends Thread implements Runnable {

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

    public WF() {
        mem = new ArrayList<>(DMConstant.DEFAULT_FRAME_SIZE);// 设置内存的大小
        blockingQueue = new LinkedList<>();
        for (int i = 0; i < DMConstant.DEFAULT_FRAME_SIZE; i++) {
            mem.add(new Frame(i, DMConstant.FREE));
        }
    }

    public WF(int cap) {
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
        long startTime = System.nanoTime();
        List<Map<Integer, int[]>> partitions = AlgorithmUtil.searchPartition(this.mem);
        if (partitions.size() == 0) {
            // 当前没有空闲分区
            addBlockingQueue(process);
            return -1;
        }
        System.out.println("\u001B[34m\u001B[1m" + TimeUtil.getCurrentTime() + "当前空闲分区情况:\u001B[0m");
        // Print each partition in the list
        for (int i = 0; i < partitions.size(); i++) {
            System.out.print("\t\u001B[34m\u001B[1m[Partition]\u001B[0m #: " + (i + 1) + ":");
            Map<Integer, int[]> partition = partitions.get(i);
            for (Map.Entry<Integer, int[]> entry : partition.entrySet()) {
                int size = entry.getKey();
                int[] range = entry.getValue();
                System.out.println("Size: " + size + ", Range: [" + range[0] + ", " + range[1] + "]");
            }
        }
        // 分配最大的的分区给进程
        if (partitions.get(0).keySet().iterator().next() >= process.getFrameSize()) {
            //刚好一块可以满足 (例如最出初始的情况)
            int startFrame = partitions.get(0).values().iterator().next()[0];
            AlgorithmUtil.allocateMem(startFrame, mem, process, startTime);
            return process.getTimeConsume();
        }else{
            addBlockingQueue(process);
            return -1;
        }
    }

    /**
     * 添加进程到阻塞队列 ,(无可用内存分配)
     *
     * @param process
     */
    private void addBlockingQueue(Process process) {
        process.setStatus(Process.BLOCK);
        blockingQueue.add(process);
        System.out.println(TimeUtil.getCurrentTime() + "\u001B[32m\u001B[1m[FAILED]\u001B[0m添加进程到阻塞队列: " + process.getPID());
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
