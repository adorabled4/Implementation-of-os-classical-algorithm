//
// 最短寻道时间优先（SSTF）调度算法
// Created by 侯金科 on 2023/5/12.
//
/*
 * SSTF算法选择调度处理的磁道是与当前磁头所在磁道距离最近的磁道，
 * 以使每次的寻找时间最短。当然，总是选择最小寻找时间并不能保证平均寻找时间最小，
 * 但是能提供比FCFS算法更好的性能。这种算法会产生“饥饿”现象。
 */
#include <iostream>
#include <cmath>
#include <climits>
using namespace std;

class SSTF {
private:
    int n; // 磁盘请求数目
    int* request; // 磁盘请求序列
    int head; // 磁头初始位置
    bool* visited; // 标记请求是否已被处理
public:
    SSTF(int n, int* request, int head) {
        this->n = n;
        this->request = request;
        this->head = head;
        this->visited = new bool[n]; // 创建动态布尔数组
        for (int i = 0; i < n; i++) {
            visited[i] = false; // 初始化为未访问
        }
    }

    ~SSTF() {
        delete[] visited; // 析构函数中释放动态数组内存
    }

    void schedule() {
        int distance = 0; // 磁头移动距离
        int seekTime = 0; // 磁头寻道总距离
        cout << "SSTF算法调度过程如下：" << endl;
        for (int i = 0; i < n; i++) {
            int minDistance = INT_MAX; // 最小寻道距离，初始为最大整数
            int minIndex = -1; // 最小寻道距离对应的请求序号，初始为-1
            for (int j = 0; j < n; j++) {
                if (!visited[j]) { // 如果请求未被处理
                    distance = abs(request[j] - head); // 计算磁头移动距离
                    if (distance < minDistance) { // 如果距离小于当前最小距离
                        minDistance = distance; // 更新最小距离
                        minIndex = j; // 更新最小距离对应的序号
                    }
                }
            }
            seekTime += minDistance; // 累加磁头寻道距离
            cout << "从" << head << "号磁道移动到" << request[minIndex] << "号磁道，移动距离为" << minDistance << endl;
            head = request[minIndex]; // 更新磁头位置
            visited[minIndex] = true; // 标记请求已被处理
        }
        cout << "SSTF算法调度结束" << endl;
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

    SSTF sstf(n, request, head); // 创建SSTF对象
    sstf.schedule(); // 调用schedule方法进行调度

    delete[] request; // 释放动态数组内存
    return 0;
}
