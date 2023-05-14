//
// Created by 12494 on 2023/5/14.
//
#include "../model/Mem.h"
const int N = 100;
int pages[N];// 模拟访问的页面序列
int n=0;//访问次数
// 计算缺页次数和缺页率
int pageFaults = 0;
double pageFaultRate = 0.0;
// 定义一个队列，用来存储当前内存中的页面
queue<int> memory;
static Mem Memobject;
// 假设内存中有 3 个页面的空间
void init(){
    cout<<"请输入内存中的页面空间大小";
    cin>>Memobject.pageNum;
    //模拟访问的页面序列
    cout<<"请输入访问的页面数";
    cin>>n;
    cout<<"请依次输入访问的页面序列";
    for(int i=0;i<n;i++){
        cin>>pages[i];
    }
}
// 定义一个函数，判断给定的页面是否在内存中
bool isInMemory(int page) {
    // 遍历队列中的元素，如果找到与页面相同的元素，返回 true
    queue<int> temp = memory; // 复制一份队列，避免修改原队列
    while (!temp.empty()) {
        if (temp.front() == page) {
            return true;
        }
        temp.pop();
    }
    // 如果遍历完队列没有找到相同的元素，返回 false
    return false;
}
// 定义一个函数，打印当前内存中的页面
void printMemory() {
    // 遍历队列中的元素，打印出来
    queue<int> temp = memory; // 复制一份队列，避免修改原队列
    cout << "Memory: ";
    while (!temp.empty()) {
        cout << temp.front() << " ";
        temp.pop();
    }
    cout << endl;
}

// 计算并输出缺页率
void print(){
    cout<<"缺页数为："<<pageFaults<<endl;
    pageFaultRate = (double)pageFaults / n*100;
    cout << fixed << setprecision(2);
    cout << "缺页率为 = " << pageFaultRate <<"%"<< endl;
}
void run(){
    // 模拟访问每个页面
    for (int i = 0; i < n; i++) {
        int page = pages[i]; // 当前要访问的页面
        cout << "当前要访问的页面: " << page << endl;
        if (isInMemory(page)) { // 如果页面已经在内存中，不需要替换
            cout << "击中页面!" << endl;
        } else { // 如果页面不在内存中，需要进行替换
            cout << "缺页!" << endl;
            pageFaults++; // 缺页次数加一
            if (memory.size() == Memobject.pageNum) { // 如果内存已满，需要淘汰最早进入的页面
                int oldPage = memory.front(); // 获取队首元素，即最早进入的页面
                memory.pop(); // 弹出队首元素
                cout << "把页面： " << oldPage << " 替换成： " << page << endl;
            }
            else { // 如果内存未满，直接加入新页面即可
                cout << "添加页面：" << page << endl;
            }
            memory.push(page); // 将新页面加入队尾
        }
        printMemory(); // 打印当前内存中的页面
        cout << "---------------------" << endl;
    }

}
