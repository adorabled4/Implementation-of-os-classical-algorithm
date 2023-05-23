package com.dhx.algorithms;

import com.dhx.model.Constant;
import com.dhx.model.Process;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author adorabled4
 * @className MLFQ  多级反馈队列调度算法（Multi-Level Feedback Queue）
 * @date : 2023/05/21/ 10:29
 **/
public class MLFQ {

    /**
     * 累计的程序数量
     */
    int totalProcessNum;

    /**
     * 已经执行的程序数量
     */
    int completedNum;
    /**
     * 就绪队列
     */
    Process running;

    /**
     * 多级队列
     */
    private List<LinkedList<Process>> multiQueue;


    /**
     * 每个队列的时间片大小
     */
    private int[] quantum;

    public MLFQ(int numQueues, int[] quantum) {
        this.multiQueue = new ArrayList<>();
        for (int i = 0; i < numQueues; i++) {
            multiQueue.add(new LinkedList<>());
        }
        this.quantum = quantum;
    }

    /**
     * 添加进程
     *
     * @param p 进程
     */
    public void addProcess(Process p) {
        System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date()) +
                "\33[35;1m[ARRIVE]进程到达\33[0m, 进程ID :" + p.getPID() + " 预计用时: " + p.getRunTime() + "(s)");
        p.setStatus(Process.READY);
        p.setArriveTime(new Date()); // 设置到达时间
        // 如果当前的就绪队列已满, 那么把程序添加到阻塞队列(实际上的作用相当于挂载:suspend)
        multiQueue.get(0).addLast(p);
        totalProcessNum++;
    }

    /**
     * 执行进程
     */
    public void executeProcess() {
        while (true) {
            int queueIndex = getNextQueueLevel();
            if(queueIndex==-1){
                break;// 所有级的队列都为空 , 结束执行
            }
            LinkedList<Process> currentQueue= multiQueue.get(queueIndex);
            while(currentQueue.size()!=0){
                // 获取需要执行的进程
                this.running=currentQueue.poll();
                running.setStatus(Process.RUNNING);
                long runTime = running.getRunTime();
                try {
                    if (runTime > quantum[queueIndex]) {
                        Thread.sleep(quantum[queueIndex] * 1000);
                        runTime=runTime - quantum[queueIndex];
                        running.setRunTime(runTime);
                        // 将进程加入下一级队列
                        if(queueIndex+1>=multiQueue.size()){
                            // 动态扩容multiQueue , 需要更新时间片数组
                            multiQueue.add(new LinkedList<>());
                            int[] newQuantum = Arrays.copyOf(quantum, multiQueue.size() + 1);
                            newQuantum[multiQueue.size()-1]=newQuantum[multiQueue.size()-2]+1;
                            quantum=newQuantum;
                        }
                        multiQueue.get(queueIndex + 1).offer(running);
                        System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date()) +
                                "\u001B[32m\u001B[1m[LACK]\u001B[0m时间片耗尽, 进程ID :" + running.getPID() +
                                "\t耗时: " + quantum[queueIndex] + "(s)" +
                                "添加进程到第 " + (queueIndex + 2) + " 级队列");
                    } else {
                        Thread.sleep(running.getRunTime() * 1000);
                        System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date()) +
                                "\33[92;1m[FINISH]\33[0m执行完毕  进程ID :" + running.getPID() +
                                "\t耗时: " + running.getRunTime() + "(s)");
                        completedNum++;
                    }
                } catch (Exception e) {
                    System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss") + " 执行进程出现异常"+queueIndex + running);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取下一个不为空的队列的级别
     * @return
     */
    public int getNextQueueLevel(){
        int queueLevel= 0;
        while(multiQueue.get(queueLevel).size()==0){
            queueLevel++;
            if(queueLevel>=multiQueue.size()){
                return -1;
            }
        }
        return queueLevel;
    }

    /**
     * 开始执行
     */
    public void start() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executeProcess();
        }
    }
}
