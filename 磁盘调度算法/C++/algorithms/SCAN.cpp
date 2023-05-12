//
// 扫描(SCAN)调度算法
// Created by 侯金科 on 2023/5/12.
//

/*
 * SCAN算法是最佳寻道时间优先算法，也叫电梯调度算法，
 * 它根据磁头的移动方向，先处理所有同向的请求，然后再处理相反方向的请求。
 * 它的优点是避免了饥饿现象，提高了磁盘访问效率。它的缺点是当磁头移到磁盘一端并反转方向时，
 * 紧靠磁头前方的请求相对较少，而磁盘另一端的请求密度却是最多。
 */

#include <iostream>
#include <cmath>
#include <algorithm>
using namespace std;

class SCAN {
private:
    int n; // 磁盘请求数目
    int* request; // 磁盘请求序列
    int head; // 磁头初始位置
    int direction; // 磁头移动方向，1为增加方向，-1为减少方向
public:
    SCAN(int n, int* request, int head) {
        this->n = n;
        this->request = request;
        this->head = head;
        sort(request, request + n); // 对请求序列进行升序排序
        //判断走向，可以用二分
        for(int i=0;i<n;i++){
            if(head<=request[i+1]){
                if(request[i+1]-head <= head - request[i])
                    this->direction = 1;
                else this->direction = -1;
                break;
            }
        }
    }

    void schedule() {
        int distance = 0; // 磁头移动距离
        int seekTime = 0; // 磁头寻道总距离
        cout << "SCAN算法调度过程如下：" << endl;
        if (direction == 1) { // 如果磁头向增加方向移动
            for (int i = 0; i < n; i++) {
                if (request[i] >= head) { // 如果请求在当前磁头位置或之后
                    distance = abs(request[i] - head); // 计算磁头移动距离
                    seekTime += distance; // 累加磁头寻道距离
                    cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
                    head = request[i]; // 更新磁头位置
                }
            }
            for (int i = n - 1; i >= 0; i--) {
                if (request[i] < head) { // 如果请求在当前磁头位置之前
                    distance = abs(request[i] - head); // 计算磁头移动距离
                    seekTime += distance; // 累加磁头寻道距离
                    cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
                    head = request[i]; // 更新磁头位置
                }
            }
        } else if (direction == -1) { // 如果磁头向减少方向移动
            for (int i = n - 1; i >= 0; i--) {
                if (request[i] <= head) { // 如果请求在当前磁头位置或之前
                    distance = abs(request[i] - head); // 计算磁头移动距离
                    seekTime += distance; // 累加磁头寻道距离
                    cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
                    head = request[i]; // 更新磁头位置
                }
            }
            for (int i = 0; i < n; i++) {
                if (request[i] > head) { // 如果请求在当前磁头位置之后
                    distance = abs(request[i] - head); // 计算磁头移动距离
                    seekTime += distance; // 累加磁头寻道距离
                    cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
                    head = request[i]; // 更新磁头位置
                }
            }
        }
        cout << "SCAN算法调度结束" << endl;
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

    SCAN scan(n, request, head); // 创建SCAN对象
    scan.schedule(); // 调用schedule方法进行调度

    delete[] request; // 释放动态数组内存
    return 0;
}
