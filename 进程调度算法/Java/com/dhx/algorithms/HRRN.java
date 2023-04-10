package com.dhx.algorithms;

import com.dhx.model.Process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author adorabled4
 * @className HRRN : 高响应比优先调度算法
 * @Description  响应比Rp = （等待时间+要求服务时间）/ 要求服务时间 = 1 +（等待时间 / 要求服务时间）
 * @date : 2023/04/09/ 13:56
 **/
public class HRRN {

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
        System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss.sss").format(new Date())+
                "\33[35;1m[arrive]进程到达\33[0m, 进程ID :"+ p.getPID() +" 预计用时: "+p.getRunTime() +"(s)");
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
            if(readyQueue.size()!=0){
                // 进程优先权展示
                System.out.println("\t进程ID\t优先权");
                readyQueue.forEach(item->{
                    System.out.print("\t");
                    System.out.println(item.getPID() + "\t"+item.getPreemption());
                });
            }
            running=readyQueue.poll();
            try{
                System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss.sss").format(new Date())+
                        "\33[92;1m[running]执行进程\33[0m  进程ID :"+ running.getPID()+
                        "\t耗时: "+running.getRunTime()+"(s)"+
                        "\t进程优先权: "+running.getPreemption());
                Thread.sleep(running.getRunTime()*1000);
            }catch (InterruptedException e) {
                System.out.println(new SimpleDateFormat("MM-dd hh:mm:ss.sss") + "执行进程出现异常");
            }
            running=null;
            // 每次执行完一个进程刷新其他进程的优先权, 重新进行排列
            sortProcessQueue();
        }
        completedNum++;
    }

    /**
     * 根据进程的优先权进行重新排序
     */
    private void sortProcessQueue(){
        fromBlock2Ready();
        int size = readyQueue.size();
        while(size-->0){
            Process process = readyQueue.poll();
            calculatePreemption(process);
            readyQueue.add(process);
        }
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

    private void calculatePreemption(Process p){
        Date arriveTime = p.getArriveTime();
        long runTime = p.getRunTime(); // 需要的执行事件
        long waitTime = System.currentTimeMillis()- arriveTime.getTime();
        double preemption = (waitTime+runTime)/1000.0/runTime; // runTime的单位是秒 , 其他两个的单位是
        p.setPreemption(preemption);
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
//            System.out.println("start0------------");
            if(readyQueue.size()>0 || blockQueue.size()>0){
                executeProcess();
            }
        }
    }

    public HRRN(int capacity){
        this.cap =capacity;
        // 按照优先权从大到小排序
        readyQueue=new PriorityQueue<>((o1,o2)-> (int) (o2.getPreemption()-o1.getPreemption()));
        blockQueue=new LinkedList<>();
    }

}
