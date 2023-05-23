package algorithms;


import java.util.Arrays;

/**
 * @author Discard-001
 * @className CSCAN
 * @date : 2023/05/13/ 17:36
 **/
public class CSCAN {
    private int n; // 磁盘请求数目
    private int[] request; // 磁盘请求序列
    private int head; // 磁头初始位置

    public CSCAN(int n, int[] request, int head) {
        this.n = n;
        this.request = request;
        this.head = head;
        Arrays.sort(request); // 对请求序列进行升序排序
    }

    public int schedule() {
        int distance = 0; // 磁头移动距离
        int seekTime = 0; // 磁头寻道时间
        System.out.println("C-SCAN算法调度过程如下：");
        int first = -1;
        for (int i = 0; i < n; i++) {
            if (head <= request[i + 1]) {
                first = i;
                break;
            }
        }
        for (int i = first + 1; i < n; i++) {
            distance = Math.abs(request[i] - head); // 计算磁头移动距离
            seekTime += distance; // 累加磁头寻道距离
            System.out.println("从" + head + "号磁道移动到" + request[i] + "号磁道，移动距离为" + distance);
            head = request[i]; // 更新磁头位置
        }
        for (int i = 0; i <= first; i++) {
            distance = Math.abs(request[i] - head); // 计算磁头移动距离
            seekTime += distance; // 累加磁头寻道距离
            System.out.println("从" + head + "号磁道移动到" + request[i] + "号磁道，移动距离为" + distance);
            head = request[i]; // 更新磁头位置
        }
        System.out.println("CSCAN算法调度结束");
        System.out.println("总移动距离为：" + seekTime);
        System.out.println("平均寻道长度为：" + (double)seekTime / n);
        return seekTime;
    }
}

