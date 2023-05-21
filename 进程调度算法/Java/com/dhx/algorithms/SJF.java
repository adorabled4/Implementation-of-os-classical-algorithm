package com.dhx.algorithms;

import com.dhx.model.Process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author dhx_
 * @className SJF : 短进程优先 , 底层采用 堆 存储, 新的进程入队的时候通过预计的执行时间来排序
 * @date : 2023/04/07/ 13:46
 **/
public class SJF {

    /**
     * 就绪队列最大容量
     */
    int cap;

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
     * 就绪队列 : 每次从就绪队列中找到一个进程来执行
     */
    PriorityQueue<Process> readyQueue;

    /**
     * 阻塞队列
     */
    LinkedList<Process> blockQueue;

    /**
     * 添加进程
     * @param p 进程
     */
    public void addProcess(Process p){
        System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date())+
                "\33[35;1m[ARRIVE]进程到达\33[0m, 进程ID :"+ p.getPID() +" 预计用时: "+p.getRunTime() +"(s)");
        p.setStatus(Process.READY);
        p.setArriveTime(new Date()); // 设置到达时间
        // 如果当前的就绪队列已满, 那么把程序添加到阻塞队列(实际上的作用相当于挂载:suspend)
        if(readyQueue.size()>=cap){
            p.setStatus(Process.BLOCK);
            blockQueue.add(p);
            return ;
        }
        readyQueue.add(p);
        totalProcessNum++;
    }

    /**
     * 执行进程
     */
    private void executeProcess(){
        while(running==null && (readyQueue.size()>0 || blockQueue.size()>0)){
            if(readyQueue.size()<1){
                fromBlock2Ready();
            }
            running=readyQueue.poll();
            try{
                System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date())+
                        "\33[92;1m[RUNNING]执行进程\33[0m  进程ID :"+ running.getPID()+
                        "\t耗时: "+running.getRunTime()+"(s)");
                Thread.sleep(running.getRunTime()*1000);
            }catch (InterruptedException e) {
                System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss") + "执行进程出现异常");
            }
            running=null;
        }
        completedNum++;
    }

    /**
     * 把程序从阻塞队列移动到等待队列
     */
    private void fromBlock2Ready(){
        if(blockQueue.size() == 0){
            return ;
        }
        while(blockQueue.size()>0 && readyQueue.size()<cap){
            Process process= readyQueue.poll();
            if(process!=null){
                process.setStatus(Process.READY);
                readyQueue.add(process);
            }
        }
    }

    /**
     * 开始执行
     */
    public void start(){
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(readyQueue.size()>0 || blockQueue.size()>0){
                executeProcess();
            }
        }
    }

    public SJF(int capacity){
        this.cap =capacity;
        readyQueue=new PriorityQueue<>((o1,o2)-> (int) (o1.getRunTime()-o2.getRunTime()));
        blockQueue=new LinkedList<>();
    }
}
