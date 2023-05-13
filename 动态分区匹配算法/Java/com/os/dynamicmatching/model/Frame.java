package com.os.dynamicmatching.model;


/**
 * @author adorabled4
 * @className Frame 模拟帧
 * @date : 2023/05/13/ 15:33
 **/
public class Frame {

    /**
     * 当前状态
     */
    private int status;

    /**
     * 帧号
     */
    private int frameId;

    /**
     * 占用内存的进程ID
     */
    private int processId;

    public Frame(int frameId,int status){
        this.frameId=frameId;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFrameId() {
        return frameId;
    }

    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }
}
