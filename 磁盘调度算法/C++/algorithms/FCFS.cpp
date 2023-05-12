//
// 先来先服务（FCFS）调度算法
// Created by 侯金科 on 2023/5/12.
//
/*
 * FCFS算法是先来先服务算法，它根据进程请求访问磁盘的先后顺序进行调度，
 * 不考虑磁头的移动方向和距离。它的优点是公平、简单，每个进程请求都能依次得到处理，
 * 不会出现饥饿现象。它的缺点是平均寻道时间较长，适用于磁盘I/O进程数目较少的场合。
 */
#include <iostream>
#include <cmath>
using namespace std;

class FCFS {
private:
    int n; // 磁盘请求数目
    int* request; // 磁盘请求序列
    int head; // 磁头初始位置
public:
    FCFS(int n, int* request, int head) {
        this->n = n;
        this->request = request;
        this->head = head;
    }

    void schedule() {
        int distance = 0; // 磁头移动距离
        int seekTime = 0; // 磁头寻道总距离
        cout << "FCFS算法调度过程如下：" << endl;
        for (int i = 0; i < n; i++) {
            distance = abs(request[i] - head); // 计算磁头移动距离
            seekTime += distance; // 累加磁头寻道距离
            cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
            head = request[i]; // 更新磁头位置
        }
        cout << "FCFS算法调度结束" << endl;
        cout << "总移动距离为：" << seekTime << endl;
        cout << "平均寻道长度为：" << (double)seekTime / n << endl;
    }
};

int main() {
    //解决CLion控制台乱码问题
    system("chcp 65001");
    cout << "请输入磁盘请求数目：" << endl;
    int n; // 磁盘请求数目
    cin >> n;
    int* request = new int[n]; // 磁盘请求序列
    cout << "请输入磁盘请求序列：" << endl;
    for (int i = 0; i < n; i++) {
        cin >> request[i]; // 输入磁盘请求序列
    }
    cout << "请输入磁头初始位置：" << endl;
    int head; // 磁头初始位置
    cin >> head;

    FCFS fcfs(n, request, head); // 创建FCFS对象
    fcfs.schedule(); // 调用schedule方法进行调度

    delete[] request; // 释放动态数组内存
    return 0;
}
