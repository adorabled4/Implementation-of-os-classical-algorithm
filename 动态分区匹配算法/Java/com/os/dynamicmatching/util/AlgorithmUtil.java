package com.os.dynamicmatching.util;

import com.os.dynamicmatching.model.DMConstant;
import com.os.dynamicmatching.model.Frame;
import com.os.dynamicmatching.model.FramePartitionNode;
import com.os.dynamicmatching.model.Process;

import java.util.*;

/**
 * @author adorabled4
 * @className AlgorithmUtil
 * @date : 2023/05/15/ 12:09
 **/
public class AlgorithmUtil {


    /**
     * 查找当前内存分区情况 , 返回连续的空闲的列表
     *
     * @param mem
     * @return 返回map列表 , 映射关系为 : frameSize : int[] , 数组中存储起始下标
     */
    public static List<Map<Integer, int[]>> searchPartition(List<Frame> mem) {
        ArrayList<Map<Integer, int[]>> results = new ArrayList<>();
        for (int i = 0; i < mem.size(); i++) {
            if (mem.get(i).getStatus() == DMConstant.FREE) {
                HashMap<Integer, int[]> map = new HashMap<>();
                int j = i;
                while (j < mem.size() && mem.get(j).getStatus() == DMConstant.FREE) {
                    j++;
                }
                map.put(j - i , new int[]{i, j-1});
                results.add(map);
                i = j+1;
            }
        }
        results.sort((o1, o2) -> o2.keySet().iterator().next() - o2.keySet().iterator().next());
        return results;
    }

    /**
     * 分配标记内存 并 执行
     * @param startFrame
     * @param mem
     * @param process
     * @param startTime
     */
    public static void allocateMem(int startFrame, List<Frame> mem, Process process, long startTime) {
        // 标记内存为已使用
        for (int j = startFrame; j < startFrame + process.getFrameSize(); j++) {
            mem.get(j).setStatus(DMConstant.USED);
        }
        process.setStatus(Process.READY);
        long timeConsume = (System.nanoTime() - startTime) / 1000;
        process.setTimeConsume(timeConsume);
        System.out.println(TimeUtil.getCurrentTime() + "\u001B[33m\u001B[1m[ALLOCATE]\u001B[0m为进程" + process.getPID() + "分配, 大小: " + process.getFrameSize() + "  起始frame: " + startFrame + " 本次分配耗时 : " + process.getTimeConsume() + "(ns)");
        // 执行进程
        RandomTestUtil.runAndMemCollect(process, startFrame, mem);
    }

    /**
     * 分配标记内存 并 执行
     * @param startFrame
     * @param node
     * @param process
     * @param startTime
     */
    public static void allocateMem(int startFrame, FramePartitionNode node, Process process, long startTime) {
        List<Frame> mem = node.getMem();
        // 标记内存为已使用
        for (int j = startFrame; j < startFrame + process.getFrameSize(); j++) {
            mem.get(j).setStatus(DMConstant.USED);
        }
        process.setStatus(Process.READY);
        long timeConsume = (System.nanoTime() - startTime) / 1000;
        process.setTimeConsume(timeConsume);
        System.out.println(TimeUtil.getCurrentTime() + "\u001B[33m\u001B[1m[ALLOCATE]\u001B[0m为进程" + process.getPID() + "分配, 大小: " + process.getFrameSize() + "  起始frame: " + startFrame + " 本次分配耗时 : " + process.getTimeConsume() + "(ns)");
        // 执行进程
        RandomTestUtil.runAndMemCollect(process, startFrame, node);
    }
}
