package com.os.dynamicmatching.algorithms;

import com.os.dynamicmatching.model.DMConstant;
import com.os.dynamicmatching.model.FramePartitionNode;
import com.os.dynamicmatching.model.Process;
import com.os.dynamicmatching.util.AlgorithmUtil;
import com.os.dynamicmatching.util.TimeUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author adorabled4
 * @className QF : 将内存划分为多个固定大小的分区，每个分区维护一个空闲分区链表，根据请求大小找到对应大小的分区链表，从该链表中找到一个空闲分区分配给程序使用。
 * @date : 2023-5-15 12:27:40
 **/
public class QF extends Thread implements Runnable {


    // 使用 List 存储每个分区链表
    // 也就是 capFrame : List<Frame>
    LinkedList<LinkedList<FramePartitionNode>> newMem;

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

    /**
     * QF 算法测试的最小size 为 1024
     */
    public static final int MIN_SIZE = 1 << 10;

    public void setRunFlag(boolean flag) {
        runFlag = flag;
    }

    public QF() {
        // 总共的cap , 通过cap划分出小的分片链表
        this.cap = MIN_SIZE;
        blockingQueue = new LinkedList<>();
        // 初始化
        newMem = new LinkedList<>();
        // 构建 分区链表
        constructPartitionNodes(cap);
    }

    public QF(int cap) {
        // 总共的cap , 通过cap划分出小的分片链表
        this.cap = cap;
        blockingQueue = new LinkedList<>();
        if (cap <= MIN_SIZE) {
            cap = MIN_SIZE;
        }
        // 初始化
        newMem = new LinkedList<>();
        // 构建 分区链表
        constructPartitionNodes(cap);
    }

    private void constructPartitionNodes(int cap) {
        int firstSize = cap / 4; // 2
        int secondSize = cap / 8; // 3
        int thirdSize = cap / 16; // 2
        int[] sizes = new int[]{firstSize, secondSize, thirdSize};
        int id = 0;
        LinkedList<FramePartitionNode> list = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            FramePartitionNode node = new FramePartitionNode(sizes[0], ++id);
            list.add(node);
        }
        newMem.add(new LinkedList<>(list));
        list.clear();
        for (int i = 0; i < 3; i++) {
            FramePartitionNode node = new FramePartitionNode(sizes[1], ++id);
            list.add(node);
        }
        newMem.add(new LinkedList<>(list));
        list.clear();
        for (int i = 0; i < 2; i++) {
            FramePartitionNode node = new FramePartitionNode(sizes[2], ++id);
            list.add(node);
        }
        newMem.add(new LinkedList<>(list));
        list.clear();
    }

    /**
     * 分配内存
     *
     * @param process
     */
    public synchronized long allocate(Process process) {
        // 从内存的起始点开始遍历，找到第一个空闲的分区
        long startTime = System.nanoTime();
        List<FramePartitionNode> peek = newMem.peek();
        if (peek == null) {
            System.out.println("分区为空!!");
            return -1;
        }
        if (peek.size() == 0 || peek.get(0).getSize() < process.getFrameSize() || peek.get(0).getStatus() == DMConstant.USED) {
            // 当前没有空闲分区 ||  当前的分区都不能满足
            addBlockingQueue(process);
            return -1;
        }
        List<Integer> frameSizes = newMem.stream().map(item -> item.get(0).getSize()).collect(Collectors.toList());
        for (int i = 0; i < frameSizes.size(); i++) {
            int curSize = frameSizes.get(i);
            // 当前的分区刚好适合 || 当前的分区适合并且当前的分区是最后一个分区
            if (i<frameSizes.size()-1 &&  curSize >= process.getFrameSize() && frameSizes.get(i+1) < process.getFrameSize()
                    || curSize >= process.getFrameSize() && i == frameSizes.size() - 1) {
                // 分配当前的分区
                LinkedList<FramePartitionNode> framePartitionNodes = newMem.get(i);
                // 获取首个
                FramePartitionNode node = framePartitionNodes.getFirst();
                // 如果当前的合适的首个已经被使用了
                if (node.getSize() == DMConstant.USED) {
                    int p = i - 1;
                    while (p >= 0 && newMem.get(p).get(0).getSize() == 0 || newMem.get(p).get(0).getStatus() == DMConstant.USED) {
                        p--;
                    }
                    if (p < 0) {
                        // 当前没有空闲分区
                        addBlockingQueue(process);
                        return -1;
                    } else {
                        // 找到了较大的空闲分区
                        LinkedList<FramePartitionNode> biggerFrameList = newMem.get(p);
                        FramePartitionNode partitionNode = biggerFrameList.get(0);
                        biggerFrameList.removeFirst();
                        biggerFrameList.addLast(partitionNode);
                        // 分配内存并执行
                        AlgorithmUtil.allocateMem(0, partitionNode, process, startTime);
                        return process.getTimeConsume();
                    }
                }
                framePartitionNodes.removeFirst();
                framePartitionNodes.addLast(node);
                // 分配内存并执行
                AlgorithmUtil.allocateMem(0, node, process, startTime);
                return process.getTimeConsume();
            }
        }
        addBlockingQueue(process);
        return -1;
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
