package algorithms;

/**
 * @author Discard-001
 * @className FCFS
 * @date : 2023/05/13/ 17:35
 **/

/**
 * FCFS算法是先来先服务算法，它根据进程请求访问磁盘的先后顺序进行调度，
 * 不考虑磁头的移动方向和距离。它的优点是公平、简单，每个进程请求都能依次得到处理，
 * 不会出现饥饿现象。它的缺点是平均寻道时间较长，适用于磁盘I/O进程数目较少的场合。
 */
import java.util.Scanner;

public class FCFS {
    private int n; // 磁盘请求数目
    private int[] request; // 磁盘请求序列
    private int head; // 磁头初始位置

    public FCFS(int n, int[] request, int head) {
        this.n = n;
        this.request = request;
        this.head = head;
    }

    public void schedule() {
        int distance = 0; // 磁头移动距离
        int seekTime = 0; // 磁头寻道总距离
        System.out.println("FCFS算法调度过程如下：");
        for (int i = 0; i < n; i++) {
            distance = Math.abs(request[i] - head); // 计算磁头移动距离
            seekTime += distance; // 累加磁头寻道距离
            System.out.println("从" + head + "号磁道移动到" + request[i] + "号磁道，移动距离为" + distance);
            head = request[i]; // 更新磁头位置
        }
        System.out.println("FCFS算法调度结束");
        System.out.println("总移动距离为：" + seekTime);
        System.out.println("平均寻道长度为：" + (double)seekTime / n);
    }
}
