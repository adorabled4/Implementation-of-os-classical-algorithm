package sample;

import algorithms.CSCAN;
import algorithms.FCFS;
import algorithms.SCAN;
import algorithms.SSTF;
import util.TestUtil;

import java.util.Scanner;

/**
 * @author Discard-001
 * @className Example
 * @date : 2023/05/13/ 17:55
 **/
public class Example {
    public static void main(String[] args) {
        compareTest();
    }

    /**
     * 通用测试: 单独测试某个算法的表现情况
     */
    static void commonTest() {
        int size = 20; // 磁盘序列的大小
        int head = 0; // 磁头的初始位置;
        int min = 10; // 磁道的最小值
        int max = 200;// 磁道的最大值
        // 通过通用测试类进行测试
        // 修改第一个方法来选定需要测试的算法
        TestUtil.algorithmTest(FCFS.class, size, min, max, head);
        TestUtil.algorithmTest(CSCAN.class, size, min, max, head);
        TestUtil.algorithmTest(SCAN.class, size, min, max, head);
        TestUtil.algorithmTest(SSTF.class, size, min, max, head);
    }

    /**
     * 对比测试: 测试相同数据下各个算法的表现情况
     */
    static void compareTest() {
        int size = 20; // 磁盘序列的大小
        int head = 0; // 磁头的初始位置;
        int min = 10; // 磁道的最小值
        int max = 200;// 磁道的最大值
        // 对比测试
        Class<?>[] classes = new Class[]{FCFS.class,CSCAN.class,SCAN.class,SSTF.class};
        TestUtil.algorithmTest(classes, size, min, max, head);
    }
}
