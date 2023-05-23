package com.os.dynamicmatching.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adorabled4
 * @className FramePartitionNode Frame分区, 基于Frame类的二次封装 , 用于实现Quick Fit 算法
 * @date : 2023/05/16/ 13:33
 **/
public class FramePartitionNode {


    /**
     * 分区的大小
     */
    private int size;


    /**
     * 当前分区的状态
     */
    private int status;

    /**
     * 分区唯一标识
     */
    private int id;

    /**
     * Frame数组, 实际存储
     */
    private List<Frame> mem;


    public FramePartitionNode(int cap,int id){
        this.size=cap;
        mem=new ArrayList<>(cap);
        for (int i = 0; i < cap; i++) {
            Frame frame = new Frame(id + i, DMConstant.FREE);
            mem.add(frame);
        }
        this.id=id;
        this.status=DMConstant.FREE;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Frame> getMem() {
        return mem;
    }

    public void setMem(List<Frame> mem) {
        this.mem = mem;
    }
}
