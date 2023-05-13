package com.os.dynamicmatching.model;

import java.util.Date;

/**
 * @author dhx_
 * @className 进程
 * @date : 2023-5-13 15:13:16
 **/
public class Process {

    /**
     * 进程ID
     */
    int PID;

    /**
     * 进程状态
     */
    int status;

    /**
     * 进程优先级
     */
    int order;

    /**
     * 执行时间
     */
    long runTime;

    /**
     * 到达时间(时刻)
     */
    Date arriveTime;

    /**
     * 结束时间(时刻)
     */
    Date finishTime;

    /**
     * 周转时间 : 周转时间=结束时间-到达时间
     */
    long cycleTime;

    /**
     * 平均周转时间 : 平均周转时间=周转时间/运行时间
     */
    long aveCycleTime;

    /**
     * 需要的 帧 的数量
     */
    int frameSize;

    /**
     * HRRN 算法的优先权
     */
    double preemption;

    long timeConsume;

    public long getTimeConsume() {
        return timeConsume;
    }

    public void setTimeConsume(long timeConsume) {
        this.timeConsume = timeConsume;
    }

    public double getPreemption() {
        return preemption;
    }

    public void setPreemption(double preemption) {
        this.preemption = preemption;
    }

    public Process(int PID , int runTime,int frameSize){
        this.PID=PID;
        this.runTime=runTime; // 设置进程需要的执行时间
        this.frameSize=frameSize;
        order=DEFAULT_ORDER;
        status=READY; // 此处模拟进程分配 , 默认初始进程状态为READY
        this.arriveTime=new Date();
    }

    /**
     * 进程就绪
     */
    public static final int READY=1;

    /**
     * 进程阻塞
     */
    public static final int BLOCK=2;

    /**
     * 执行
     */
    public static final int RUNNING=3;

    /**
     * 默认进程优先级
     */
    public static final int DEFAULT_ORDER=0;


    public int getPID() {
        return PID;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public long getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(long cycleTime) {
        this.cycleTime = cycleTime;
    }

    public long getAveCycleTime() {
        return aveCycleTime;
    }

    public void setAveCycleTime(long aveCycleTime) {
        this.aveCycleTime = aveCycleTime;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }
}
