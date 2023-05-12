//
// 循环扫描（C-SCAN）调度算法
// Created by 侯金科 on 2023/5/12.
//

/*
 *C-SCAN算法是循环扫描算法，它是SCAN算法的改进，
 * 它在磁头移动到磁盘一端并反转方向时，不处理相反方向的请求，而是直接跳到磁盘另一端的最外侧磁道，
 * 然后再按同一方向处理请求。它的优点是避免了磁头反转方向时的寻道时间，使请求的响应时间更加均匀。
 * 它的缺点是可能导致磁盘两端的请求等待时间较长。
 *
 */

#include <iostream>
#include <cmath>
#include <algorithm>
using namespace std;

class C_SCAN {
private:
    int n; // 磁盘请求数目
    int* request; // 磁盘请求序列
    int head; // 磁头初始位置
public:
    C_SCAN(int n, int* request, int head) {
        this->n = n;
        this->request = request;
        this->head = head;
        sort(request, request + n); // 对请求序列进行升序排序
    }

    void schedule() {
        int distance = 0; // 磁头移动距离
        int seekTime = 0; // 磁头寻道时间
        cout << "C-SCAN算法调度过程如下：" << endl;
        int first = -1;
        for(int i=0;i<n;i++){
            if(head<=request[i+1]){
                first = i;
                break;
            }
        }
        for (int i = first+1; i < n; i++) {
                distance = abs(request[i] - head); // 计算磁头移动距离
                seekTime += distance; // 累加磁头寻道距离
                cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
                head = request[i]; // 更新磁头位置
        }
        for (int i = 0; i <= first; i++) {
                distance = abs(request[i] - head); // 计算磁头移动距离
                seekTime += distance; // 累加磁头寻道距离
                cout << "从" << head << "号磁道移动到" << request[i] << "号磁道，移动距离为" << distance << endl;
                head = request[i]; // 更新磁头位置
        }
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

    C_SCAN cScan(n, request, head); // 创建SCAN对象
    cScan.schedule(); // 调用schedule方法进行调度

    delete[] request; // 释放动态数组内存
    return 0;
}
