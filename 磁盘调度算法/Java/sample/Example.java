package sample;

import algorithms.CSCAN;
import algorithms.FCFS;
import algorithms.SCAN;
import algorithms.SSTF;

import java.util.Scanner;

/**
 * @author Discard-001
 * @className Example
 * @date : 2023/05/13/ 17:55
 **/
public class Example {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入磁盘请求数目：");
        int n = sc.nextInt(); // 磁盘请求数目
        int[] request = new int[n]; // 磁盘请求序列
        System.out.println("请输入磁盘请求序列：");
        for (int i = 0; i < n; i++) {
            request[i] = sc.nextInt(); // 输入磁盘请求序列
        }
        System.out.println("请输入磁头初始位置：");
        int head = sc.nextInt(); // 磁头初始位置


        //根据需要放开其中两行
//        CSCAN cScan = new CSCAN(n, request, head); // 创建C_SCAN对象
//        cScan.schedule(); // 调用schedule方法进行调度
//
//        SCAN Scan = new SCAN(n, request, head); // 创建SCAN对象
//        Scan.schedule(); // 调用schedule方法进行调度
//
//        SSTF sstf = new SSTF(n, request, head); // 创建SSTF对象
//        sstf.schedule(); // 调用schedule方法进行调度

        FCFS fcfs = new FCFS(n, request, head); // 创建FCFS对象
        fcfs.schedule(); // 调用schedule方法进行调度

    }
}
